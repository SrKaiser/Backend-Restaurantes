package repositorios;

import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;

public interface IRepositorioRestaurante{
	
	public String altaRestaurante(String nombre, double latitud, double longitud);
	
	boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud);
	
	List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante);
	
	boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
	
	boolean addPlato(String idRestaurante, Plato plato);
	
	boolean removePlato(String idRestaurante, String nombrePlato);
	
	boolean updatePlato(String idRestaurante, Plato plato);
	
	Restaurante recuperarRestaurante(String idRestaurante);
	
	boolean borrarRestaurante(String idRestaurante);
	
	List<ResumenRestaurante> listarRestaurantes();
}
