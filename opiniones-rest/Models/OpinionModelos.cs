using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;

namespace Opinion.Modelo
{
    public class OpinionModelo
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public string NombreRecurso { get; set; }
        public List<Valoracion> Valoraciones { get; set; }
    }

    public class Valoracion
    {
        public string CorreoElectronico { get; set; }
        public DateTime Fecha { get; set; }
        public int Calificacion { get; set; }
        public string Comentario { get; set; }
    }
}