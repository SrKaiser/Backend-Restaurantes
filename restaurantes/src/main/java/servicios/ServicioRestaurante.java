package servicios;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import excepciones.EntidadNoEncontrada;
import excepciones.RepositorioException;

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
//	private ServicioOpinionesRetrofit servicioOpiniones;

    public ServicioRestaurante() {
    	this.repositorioRestaurante  = FactoriaRepositorios.getRepositorio(Restaurante.class);
//    	this.servicioOpiniones = new ServicioOpinionesRetrofit();
    }
    
    @Override
    public String altaRestaurante(String nombre, double latitud, double longitud) throws RepositorioException {
    	if (nombre == null || nombre.trim().isEmpty()) {
			throw new RepositorioException("El nombre no puede ser null o vacío");
		}
		if (latitud < -90 || latitud > 90) {
			throw new RepositorioException("La latitud debe estar entre -90 y 90");
		}
		if (longitud < -180 || longitud > 180) {
			throw new RepositorioException("La longitud debe estar entre -180 y 180");
		}
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String gestorId = authentication.getName();

//        String opinionId = servicioOpiniones.registrarRecurso(nombre);
        
        return repositorioRestaurante.create(nombre, latitud, longitud, null);
    }
    
    @Override
    public boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud) throws RepositorioException, EntidadNoEncontrada {
    	if (nombre == null || nombre.trim().isEmpty()) {
			throw new RepositorioException("El nombre no puede ser null o vacío");
		}
		if (latitud < -90 || latitud > 90) {
			throw new RepositorioException("La latitud debe estar entre -90 y 90");
		}
		if (longitud < -180 || longitud > 180) {
			throw new RepositorioException("La longitud debe estar entre -180 y 180");
		}
    	return repositorioRestaurante.update(idRestaurante, nombre, latitud, longitud);
    }
    
    @Override
	public List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
		return repositorioRestaurante.findSitiosTuristicosProximos(idRestaurante);
	}
    
    @Override
    public boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) throws RepositorioException, EntidadNoEncontrada {
    	if (sitiosTuristicos == null || sitiosTuristicos.isEmpty()) {
	        throw new RepositorioException("La lista de sitios turísticos no puede ser nula o vacía");
	    }
	    for (SitioTuristico sitio : sitiosTuristicos) {
	        if (sitio.getTitulo() == null || sitio.getTitulo().trim().isEmpty()) {
	            throw new RepositorioException("El título del sitio turístico no puede ser null o vacío");
	        }
	        if (sitio.getResumen() == null || sitio.getResumen().trim().isEmpty()) {
	            throw new RepositorioException("El resumen del sitio turístico no puede ser null o vacío");
	        }
	    }
    	return repositorioRestaurante.setSitiosTuristicosDestacados(idRestaurante, sitiosTuristicos);
    }
    
    @Override
    public boolean añadirPlato(String idRestaurante, Plato plato) throws RepositorioException, EntidadNoEncontrada {
    	if (plato.getNombre() == null || plato.getNombre().trim().isEmpty()) {
	        throw new RepositorioException("El nombre del plato no puede ser null o vacío");
	    }
	    if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty()) {
	        throw new RepositorioException("La descripción del plato no puede ser null o vacía");
	    }
	    if (plato.getPrecio() <= 0) {
	        throw new RepositorioException("El precio del plato debe ser mayor que 0");
	    }

       return repositorioRestaurante.addPlato(idRestaurante, plato);
    }
    
    @Override
    public boolean borrarPlato(String idRestaurante, String nombrePlato) throws RepositorioException, EntidadNoEncontrada {
    	if (nombrePlato == null || nombrePlato.trim().isEmpty()) {
			throw new RepositorioException("El nombre del plato no puede ser null o vacío");
		}
    	
        return repositorioRestaurante.removePlato(idRestaurante, nombrePlato);
    }
    
    @Override
    public boolean actualizarPlato(String idRestaurante, Plato plato) throws RepositorioException, EntidadNoEncontrada {
    	if (plato.getNombre() == null || plato.getNombre().trim().isEmpty()) {
	        throw new RepositorioException("El nombre del plato no puede ser null o vacío");
	    }
	    if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty()) {
	        throw new RepositorioException("La descripción del plato no puede ser null o vacía");
	    }
	    if (plato.getPrecio() <= 0) {
	        throw new RepositorioException("El precio del plato debe ser mayor que 0");
	    }

        return repositorioRestaurante.updatePlato(idRestaurante, plato);   
    }
    
    @Override
    public Restaurante recuperarRestaurante(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
        return repositorioRestaurante.findById(idRestaurante);
    }
    
    @Override
    public boolean borrarRestaurante(String idRestaurante) throws RepositorioException, EntidadNoEncontrada {
        return repositorioRestaurante.delete(idRestaurante);
    }
    
    @Override
    public List<ResumenRestaurante> recuperarTodosRestaurantes() throws RepositorioException {
        return repositorioRestaurante.findAll();
    }
    
//    @Override
//    public List<Valoracion> obtenerValoracionesRestaurante(String idRestaurante){
//    	Restaurante restaurante = recuperarRestaurante(idRestaurante);
//        if (restaurante != null) {
//            return servicioOpiniones.obtenerValoraciones(restaurante.getOpinionId());
//        }
//        return null;
//
//    }

	

}
