package servicios;

import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import repositorios.RepositorioRestaurante;

public class ServicioRestaurante implements IServicioRestaurante {
	
	private RepositorioRestaurante repositorioRestaurante;

    public ServicioRestaurante() {
    	String connectionString = "mongodb://arso:arso@ac-v8ez3vj-shard-00-00.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-01.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-02.kzwz6ia.mongodb.net:27017/?ssl=true&replicaSet=atlas-b3t6zg-shard-0&authSource=admin&retryWrites=true&w=majority";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("proyecto-arso");
        this.repositorioRestaurante = new RepositorioRestaurante(database);
    }
    
    @Override
    public String altaRestaurante(String nombre, double latitud, double longitud) {
    	return repositorioRestaurante.insert(nombre, latitud, longitud);
    }
    
    @Override
    public boolean actualizarRestaurante(String idRestaurante, String nombre, double latitud, double longitud) {
        return repositorioRestaurante.update(idRestaurante, nombre, latitud, longitud);
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
    public List<ResumenRestaurante> recuperarTodosRestaurantes() {
        return repositorioRestaurante.findAll();
    }

}
