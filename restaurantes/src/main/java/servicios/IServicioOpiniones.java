package servicios;

import java.util.List;

import modelos.Valoracion;

public interface IServicioOpiniones {
    String registrarRecurso(String nombreRecurso);
    List<Valoracion> obtenerValoraciones(String idOpinion);
}

