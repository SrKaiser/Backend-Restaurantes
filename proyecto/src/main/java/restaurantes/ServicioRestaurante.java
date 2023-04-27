package restaurantes;

import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ServicioRestaurante implements IServicioRestaurante {
	
	private RepositorioRestaurante repositorioRestaurante;

    public ServicioRestaurante() {
    	String connectionString = "mongodb+srv://arso:arso@proyecto-arso.kzwz6ia.mongodb.net/?retryWrites=true&w=majority";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("proyecto-arso");
        this.repositorioRestaurante = new RepositorioRestaurante(database);
    }
    
    @Override
    public String altaRestaurante(String nombre, String coordenadas) {
    	return repositorioRestaurante.insert(nombre, coordenadas);
    }
    
    @Override
    public boolean actualizarRestaurante(String id, String nombre, String coordenadas) {
        return repositorioRestaurante.update(id, nombre, coordenadas);
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
    public List<Restaurante> recuperarTodosRestaurantes() {
        return repositorioRestaurante.findAll();
    }

}
