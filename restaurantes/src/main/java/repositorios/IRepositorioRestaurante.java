package repositorios;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;

public interface IRepositorioRestaurante{
	
	String create(String nombre, double latitud, double longitud);
    
	Restaurante findById(String id);
    
	List<ResumenRestaurante> findAll();
    
	boolean update(String idRestaurante, String nombre, double latitud, double longitud);
    
	boolean delete(String id);
	
    boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos);
    
    boolean addPlato(String idRestaurante, Plato plato);
    
    boolean removePlato(String idRestaurante, String nombrePlato);
    
    boolean updatePlato(String idRestaurante, String nombrePlato, Plato plato);
    

	boolean setSitiosTuristicosDestacados(ObjectId objectId, List<Document> sitiosTuristicosDocumentos);
	
	boolean addPlato(ObjectId objectId, Document platoDoc);
	
	boolean removePlato(ObjectId objectId, String nombrePlato);
	
	boolean updatePlato(ObjectId objectId, String nombre, Document platoDoc);
	
	
	
}
