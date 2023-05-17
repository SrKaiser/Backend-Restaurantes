
using System;
using System.Collections.Generic;
using Opinion.Modelo;
using Repositorio;
using Opinion.Repositorio;

namespace Opinion.Servicio
{
    public interface IServicioOpinion
    {
        string RegistrarRecurso(string nombreRecurso);

        bool AñadirValoracion(string idOpinion, Valoracion valoracion);

        OpinionModelo ObtenerOpinion(string idOpinion);

        bool EliminarOpinion(string idOpinion);

        List<OpinionModelo> ObtenerOpiniones();
    }

    public class ServicioOpinion : IServicioOpinion
    {
        private IRepositorioOpinion _repositorioOpinion;

        public ServicioOpinion( IRepositorioOpinion _repositorioOpinion)
        {
            this._repositorioOpinion =_repositorioOpinion;
        }

        public string RegistrarRecurso(string nombreRecurso)
        {
            OpinionModelo opinion = new OpinionModelo(nombreRecurso);
            return _repositorioOpinion.Create(opinion);
        }

        public bool AñadirValoracion(string idOpinion, Valoracion valoracion)
        {
            OpinionModelo opinion = _repositorioOpinion.FindById(idOpinion);
            if (opinion != null)
            {
                opinion.Valoraciones.RemoveAll(v => v.CorreoElectronico.Equals(valoracion.CorreoElectronico));
                opinion.AddValoracion(valoracion);
                _repositorioOpinion.AddValoracion(idOpinion, valoracion);

                // Notificar evento reserva creada
                        
                // 1. Crear el evento
            
                JObject evento = new JObject();
                evento["idOpinion"] = opinion.Id;
                evento["valoracion"] = valoracion.Valor;
                evento["numeroValoraciones"] = opinion.NumeroValoraciones;
                evento["calificacionMedia"] = opinion.CalificacionMedia;
                
                // 2. Notificarlo
                NotificarEvento(evento)

                return true;
            }
            
            return false;
        }

        
        protected void NotificarEvento(JObject evento)
        {
            try
            {
                var factory = new ConnectionFactory()
                {
                    Uri = new Uri("amqps://edzrfeij:KHQQWPWgL4xfzLdyGf8kazZ8XWrxNm6H@crow.rmq.cloudamqp.com/edzrfeij")
                };

                using (var connection = factory.CreateConnection())
                using (var channel = connection.CreateModel())
                {
                    /** Declaración del Exchange **/
                    // FIXME: la declaración del exchange debería hacerse una sola vez en el constructor
                    var exchangeName = "evento.nueva.valoracion";
                    bool durable = true;
                    channel.ExchangeDeclare(exchangeName, "fanout", durable);

                    /** Envío del mensaje **/
                    var json = JsonConvert.SerializeObject(evento);

                    var basicProperties = channel.CreateBasicProperties();
                    basicProperties.ContentType = "application/json";

                    channel.BasicPublish(exchangeName, routingKey: "", basicProperties, body: Encoding.UTF8.GetBytes(json));
                }
            }
            catch (Exception e)
            {
                throw new Exception(e.Message);
            }
        }

        public OpinionModelo ObtenerOpinion(string idOpinion)
        {
            return _repositorioOpinion.FindById(idOpinion);
        }

        public bool EliminarOpinion(string idOpinion)
        {
            return _repositorioOpinion.Delete(idOpinion);
        }

        public List<OpinionModelo> ObtenerOpiniones()
        {
            return _repositorioOpinion.FindAll();
        }
    }
}

