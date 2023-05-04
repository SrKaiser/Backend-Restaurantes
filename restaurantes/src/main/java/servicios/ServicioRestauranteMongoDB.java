package servicios;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import repositorios.FactoriaRepositorios;
import repositorios.IRepositorioRestaurante;

public class ServicioRestauranteMongoDB implements IServicioRestaurante {
	
	private IRepositorioRestaurante repositorioRestaurante;

    public ServicioRestauranteMongoDB() {
    	this.repositorioRestaurante  = FactoriaRepositorios.getRepositorio(IRepositorioRestaurante.class);
    }
    
    @Override
    public String altaRestaurante(String nombre, double latitud, double longitud) {
    	return repositorioRestaurante.create(nombre, latitud, longitud);
    }
    
    @Override
    public boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud) {
        return repositorioRestaurante.update(idRestaurante, nombre, latitud, longitud);
    }
    
    public List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante) {
    	ServicioSitiosTuristicos servSitiosTuristicos = new ServicioSitiosTuristicos();
        Restaurante r = getCoordenadas(idRestaurante);
        List<SitioTuristico> listaSitiosTuristicos = new LinkedList<>();
        try {
            listaSitiosTuristicos = servSitiosTuristicos.obtenerSitios(r.getLatitud(), r.getLongitud());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaSitiosTuristicos;
    }
    
    private Restaurante getCoordenadas(String idRestaurante) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Restaurante rest = repositorioRestaurante.findById(objectId.toString());
        if (rest == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setLatitud(rest.getLatitud());
        restaurante.setLongitud(rest.getLongitud());
 
        return restaurante;
    }
    
    @Override
    public boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
    	ObjectId objectId = new ObjectId(idRestaurante);
        List<Document> sitiosTuristicosDocumentos = sitiosTuristicos.stream()
                .map(sitioTuristico -> new Document()
                		.append("titulo", sitioTuristico.getTitulo())
                        .append("resumen", sitioTuristico.getResumen())
                        .append("categorias", sitioTuristico.getCategorias())
                        .append("enlaces", sitioTuristico.getEnlaces())
                        .append("imagenes", sitioTuristico.getImagenes()))
                .collect(Collectors.toList());
        return repositorioRestaurante.setSitiosTuristicosDestacados(objectId, sitiosTuristicosDocumentos);
    }
    
    @Override
    public boolean añadirPlato(String idRestaurante, Plato plato) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        Document platoDoc = new Document()
                .append("nombre", plato.getNombre())
                .append("descripcion", plato.getDescripcion())
                .append("precio", plato.getPrecio());

        return repositorioRestaurante.addPlato(objectId, platoDoc);
    }
    
    @Override
    public boolean borrarPlato(String idRestaurante, String nombrePlato) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return repositorioRestaurante.removePlato(objectId, nombrePlato);
    }
    
    @Override
    public boolean actualizarPlato(String idRestaurante, Plato plato) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        Document platoDoc = new Document()
                .append("nombre", plato.getNombre())
                .append("descripcion", plato.getDescripcion())
                .append("precio", plato.getPrecio());
        return repositorioRestaurante.updatePlato(objectId, plato.getNombre(), platoDoc);
       
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

}
