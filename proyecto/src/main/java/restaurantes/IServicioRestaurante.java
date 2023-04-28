package restaurantes;

import java.util.List;

public interface IServicioRestaurante {
	
	public String altaRestaurante(String nombre, double latitud, double longitud);
	
	boolean actualizarRestaurante(String id, String nombre, double latitud, double longitud);
	
	List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante);
	
	boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
	
	boolean añadirPlato(String idRestaurante, Plato plato);
	
	boolean borrarPlato(String idRestaurante, String nombrePlato);
	
	boolean actualizarPlato(String idRestaurante, Plato plato);
	
	Restaurante recuperarRestaurante(String idRestaurante);
	
	boolean borrarRestaurante(String idRestaurante);
	
	List<ResumenRestaurante> recuperarTodosRestaurantes();
	
}
