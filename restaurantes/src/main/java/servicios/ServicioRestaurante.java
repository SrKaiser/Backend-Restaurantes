package servicios;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.Valoracion;
import repositorios.FactoriaRepositorios;
import repositorios.IRepositorioRestaurante;

public class ServicioRestaurante implements IServicioRestaurante {
	
	private IRepositorioRestaurante repositorioRestaurante;
	private ServicioOpinionesRetrofit servicioOpiniones;

    public ServicioRestaurante() {
    	this.repositorioRestaurante  = FactoriaRepositorios.getRepositorio(Restaurante.class);
    	this.servicioOpiniones = new ServicioOpinionesRetrofit();
    }
    
    @Override
    public String altaRestaurante(String nombre, double latitud, double longitud) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String gestorId = authentication.getName();

        String opinionId = servicioOpiniones.registrarRecurso(nombre);
        
        return repositorioRestaurante.create(nombre, latitud, longitud, gestorId, opinionId);
    }
    
    @Override
    public boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud) {
        return repositorioRestaurante.update(idRestaurante, nombre, latitud, longitud);
    }
    
    @Override
	public List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante) {
		return repositorioRestaurante.findSitiosTuristicosProximos(idRestaurante);
	}
    
    @Override
    public boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
        return repositorioRestaurante.setSitiosTuristicosDestacados(idRestaurante, sitiosTuristicos);
    }
    
    @Override
    public boolean añadirPlato(String idRestaurante, Plato plato) {
       return repositorioRestaurante.addPlato(idRestaurante, plato);
    }
    
    @Override
    public boolean borrarPlato(String idRestaurante, String nombrePlato) {
        return repositorioRestaurante.removePlato(idRestaurante, nombrePlato);
    }
    
    @Override
    public boolean actualizarPlato(String idRestaurante, Plato plato) {
        return repositorioRestaurante.updatePlato(idRestaurante, plato);   
    }
    
    @Override
    public Restaurante recuperarRestaurante(String idRestaurante) {
        return repositorioRestaurante.findById(idRestaurante);
    }
    
    @Override
    public boolean borrarRestaurante(String idRestaurante) {
        return repositorioRestaurante.delete(idRestaurante);
    }
    
    @Override
    public List<ResumenRestaurante> recuperarTodosRestaurantes() {
        return repositorioRestaurante.findAll();
    }
    
    @Override
    public List<Valoracion> obtenerValoracionesRestaurante(String idRestaurante){
    	Restaurante restaurante = recuperarRestaurante(idRestaurante);
        if (restaurante != null) {
            return servicioOpiniones.obtenerValoraciones(restaurante.getOpinionId());
        }
        return null;

    }

	

}
