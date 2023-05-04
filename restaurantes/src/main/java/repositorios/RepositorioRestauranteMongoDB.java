package repositorios;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;

public class RepositorioRestauranteMongoDB implements IRepositorioRestaurante{

	private final MongoCollection<Document> restauranteCollection;

    public RepositorioRestauranteMongoDB() {
	  String connectionString = "mongodb://arso:arso@ac-v8ez3vj-shard-00-00.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-01.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-02.kzwz6ia.mongodb.net:27017/?ssl=true&replicaSet=atlas-b3t6zg-shard-0&authSource=admin&retryWrites=true&w=majority";
	  MongoClientSettings settings = MongoClientSettings.builder()
	          .applyConnectionString(new ConnectionString(connectionString))
	          .build();
	  MongoClient mongoClient = MongoClients.create(settings);
	  MongoDatabase database = mongoClient.getDatabase("proyecto-arso");
      this.restauranteCollection = database.getCollection("restaurantes");
    }

	@Override
	public String create(String nombre, double latitud, double longitud) {
	 Document doc = new Document("nombre", nombre)
                .append("latitud", latitud)
                .append("longitud", longitud);
        restauranteCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        return id.toHexString();
		
	}
	
	@Override
    public boolean update(String idRestaurante, String nombre, double latitud, double longitud) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.combine(Updates.set("nombre", nombre), Updates.set("latitud", latitud), Updates.set("longitud", longitud))).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    public boolean setSitiosTuristicosDestacados(ObjectId objectId, List<Document> sitiosTuristicosDocumentos) {
		UpdateResult result = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.set("sitiosTuristicosDestacados", sitiosTuristicosDocumentos));
        return result.getModifiedCount() == 1;
    }
	
    @Override
	public boolean addPlato(ObjectId objectId, Document platoDoc) {
        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.push("platos", platoDoc)).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    public boolean removePlato(ObjectId objectId, String nombrePlato) {
        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId),
                Updates.pull("platos", new Document("nombre", nombrePlato))).getModifiedCount();
        return updatedCount > 0;
    }
	
    @Override
    public boolean updatePlato(ObjectId objectId, String nombre, Document platoDoc) {
        long updatedCount = restauranteCollection.updateOne(
                Filters.and(
                    Filters.eq("_id", objectId),
                    Filters.elemMatch("platos", Filters.eq("nombre", nombre))
                ),
                Updates.set("platos.$", platoDoc)
        ).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    @SuppressWarnings("unchecked")
    public Restaurante findById(String idRestaurante) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Document doc = restauranteCollection.find(Filters.eq("_id", objectId)).first();
        if (doc == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setId(doc.getObjectId("_id").toString());
        restaurante.setNombre(doc.getString("nombre"));
        restaurante.setLatitud(doc.getDouble("latitud"));
        restaurante.setLongitud(doc.getDouble("longitud"));
        // TODO Obtener los platos y sitios turisticos?
      
        List<Plato> platos = new ArrayList<>();
		List<Document> platosDocs = (List<Document>) doc.get("platos");
        if (platosDocs != null) {
            for (Document platoDoc : platosDocs) {
                Plato plato = new Plato();
                plato.setNombre(platoDoc.getString("nombre"));
                plato.setDescripcion(platoDoc.getString("descripcion"));
                plato.setPrecio(platoDoc.getDouble("precio"));
                platos.add(plato);
            }
        }
        restaurante.setPlatos(platos);
        
        List<SitioTuristico> sitiosTuristicos = new ArrayList<>();
    
		List<Document> sitiosTuristicosDocs = (List<Document>) doc.get("sitiosTuristicosDestacados");
        if (sitiosTuristicosDocs != null) {
            for (Document sitioTuristicoDoc : sitiosTuristicosDocs) {
                SitioTuristico sitioTuristico = new SitioTuristico();
                sitioTuristico.setTitulo(sitioTuristicoDoc.getString("titulo"));
                sitioTuristico.setResumen(sitioTuristicoDoc.getString("resumen"));
                sitioTuristico.setCategorias((List<String>) sitioTuristicoDoc.get("categorias"));
                sitioTuristico.setEnlaces((List<String>) sitioTuristicoDoc.get("enlaces"));
                sitioTuristico.setImagenes((List<String>) sitioTuristicoDoc.get("imagenes"));
                sitiosTuristicos.add(sitioTuristico);
            }
        }
        restaurante.setSitiosTuristicos(sitiosTuristicos);
        return restaurante;
    }
	
	@Override
	public boolean delete(String idRestaurante) {
		ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        long deletedCount = restauranteCollection.deleteOne(Filters.eq("_id", objectId)).getDeletedCount();
        return deletedCount > 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
    public List<ResumenRestaurante> findAll() {
        List<ResumenRestaurante> restaurantesList = new ArrayList<>();
        try (MongoCursor<Document> cursor = restauranteCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                ResumenRestaurante restaurante = new ResumenRestaurante();
                restaurante.setId(doc.getObjectId("_id").toString());
                restaurante.setNombre(doc.getString("nombre"));
                restaurante.setLatitud(doc.getDouble("latitud"));
                restaurante.setLongitud(doc.getDouble("longitud"));
                List<Document> platosDocs = (List<Document>) doc.get("platos");
                restaurante.setNumeroPlatos(platosDocs.size());
                List<Document> sitiosTuristicosDocs = (List<Document>) doc.get("sitiosTuristicosDestacados");
                restaurante.setNumeroSitiosTuristicos(sitiosTuristicosDocs.size());
                restaurantesList.add(restaurante);
            }
        }
        return restaurantesList;
    }


}
