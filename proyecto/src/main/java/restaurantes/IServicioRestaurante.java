package restaurantes;

import java.util.List;

public interface IServicioRestaurante {
	
	public String altaRestaurante(String nombre, String coordenadas);
	
	boolean actualizarRestaurante(String id, String nombre, String coordenadas);
	
	List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante);
	
	boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
	
	boolean añadirPlato(String idRestaurante, Plato plato);
	
	boolean borrarPlato(String idRestaurante, String nombrePlato);
	
	boolean actualizarPlato(String idRestaurante, Plato plato);
	
	Restaurante recuperarRestaurante(String idRestaurante);
	
	boolean borrarRestaurante(String idRestaurante);
	
	List<Restaurante> recuperarTodosRestaurantes();
	
}
