package servicios;

import java.util.List;

import modelos.Opinion;
import modelos.Valoracion;
import servicio.ServicioOpinion;

public class ServicioOpinionesRetrofit implements IServicioOpiniones {
    private ServicioOpinion servicioOpinion;
    
    public ServicioOpinionesRetrofit () {
    	this.servicioOpinion = new ServicioOpinion();
    }

    @Override
    public String registrarRecurso(String nombreRecurso) {
        return servicioOpinion.registrarRecurso(nombreRecurso);
    }
    
    @Override
    public List<Valoracion> obtenerValoraciones(String idOpinion) {
        Opinion opinion = servicioOpinion.obtenerOpinion(idOpinion);
        if (opinion != null) {
            return opinion.getValoraciones();
        }
        return null;
    }
}
