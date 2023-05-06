package repositorios;

import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;

public interface IRepositorioRestaurante{
	
	String create(String nombre, double latitud, double longitud, String gestorId, String opinionId);
    
	Restaurante findById(String id);
    
	List<ResumenRestaurante> findAll();
    
	boolean update(String idRestaurante, String nombre, double latitud, double longitud);
    
	boolean delete(String id);
	
	List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante);
	
    boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
    
    boolean addPlato(String idRestaurante, Plato plato);
    
    boolean removePlato(String idRestaurante, String nombrePlato);
    
    boolean updatePlato(String idRestaurante, Plato plato);

}
