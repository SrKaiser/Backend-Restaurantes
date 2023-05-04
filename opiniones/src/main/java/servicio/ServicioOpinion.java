package servicio;

import modelos.Opinion;
import modelos.Valoracion;
import repositorio.RepositorioOpinion;

public class ServicioOpinion implements IServicioOpinion{
	private final RepositorioOpinion repositorioOpinion;

    public ServicioOpinion(RepositorioOpinion repositorioOpinion) {
        this.repositorioOpinion = repositorioOpinion;
    }

    @Override
    public String registrarRecurso(String nombreRecurso) {
        Opinion opinion = new Opinion(nombreRecurso);
        return repositorioOpinion.create(opinion);
    }

    @Override
    public void añadirValoracion(String idOpinion, Valoracion valoracion) {
        Opinion opinion = repositorioOpinion.findById(idOpinion);
        if (opinion != null) {
            opinion.getValoraciones().removeIf(v -> v.getCorreoElectronico().equals(valoracion.getCorreoElectronico()));
            opinion.getValoraciones().add(valoracion);
            repositorioOpinion.update(opinion);
        }
    }

    @Override
    public Opinion obtenerOpinion(String idOpinion) {
        return repositorioOpinion.findById(idOpinion);
    }

    @Override
    public void eliminarOpinion(String idOpinion) {
    	repositorioOpinion.delete(idOpinion);
    }
}
