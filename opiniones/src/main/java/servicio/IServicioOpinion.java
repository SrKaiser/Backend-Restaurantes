package servicio;

import modelos.Opinion;
import modelos.Valoracion;

public interface IServicioOpinion {
	String registrarRecurso(String nombreRecurso);
    void añadirValoracion(String idOpinion, Valoracion valoracion);
    Opinion obtenerOpinion(String idOpinion);
    void eliminarOpinion(String idOpinion);
}
