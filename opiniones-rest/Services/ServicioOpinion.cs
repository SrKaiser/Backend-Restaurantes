
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
                return true;
            }
            return false;
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

