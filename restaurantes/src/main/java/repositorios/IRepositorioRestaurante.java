package repositorios;

import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;

public interface IRepositorioRestaurante {
	
	public String insert(String nombre, double latitud, double longitud);
	
	boolean update(String idRestaurante, String nombre, double latitud, double longitud);
	
	List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante);
	
	boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
	
	boolean addPlato(String idRestaurante, Plato plato);
	
	boolean removePlato(String idRestaurante, String nombrePlato);
	
	boolean updatePlato(String idRestaurante, Plato plato);
	
	Restaurante findById(String idRestaurante);
	
	boolean delete(String idRestaurante);
	
	List<ResumenRestaurante> findAll();
}
