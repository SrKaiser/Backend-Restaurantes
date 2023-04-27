package restaurantes;

import java.util.List;

public interface IRepositorioRestaurante {
	
	public String insert(String nombre, String coordenadas);
	
	boolean update(String id, String nombre, String coordenadas);
	
	List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante);
	
	boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
	
	boolean addPlato(String idRestaurante, Plato plato);
	
	boolean removePlato(String idRestaurante, String nombrePlato);
	
	boolean updatePlato(String idRestaurante, Plato plato);
	
	Restaurante findById(String idRestaurante);
	
	boolean delete(String idRestaurante);
	
	List<Restaurante> findAll();
}
