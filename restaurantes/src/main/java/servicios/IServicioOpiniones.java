package servicios;

import excepciones.EntidadNoEncontrada;
import excepciones.RepositorioException;

public interface IServicioOpiniones {
    Object registrarRecurso(String nombre) throws RepositorioException, EntidadNoEncontrada;
    Object obtenerValoraciones(String idOpinion);
}

