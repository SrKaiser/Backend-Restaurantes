package restaurantes;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class RepositorioRestaurante implements IRepositorioRestaurante{

	private final MongoCollection<Document> restauranteCollection;

    public RepositorioRestaurante(MongoDatabase database) {
        this.restauranteCollection = database.getCollection("restaurantes");
    }

	@Override
	public String insert(String nombre, String coordenadas) {
	 Document doc = new Document("nombre", nombre)
                .append("coordenadas", coordenadas);
        restauranteCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        return id.toHexString();
		
	}
	
	@Override
    public boolean update(String id, String nombre, String coordenadas) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return false;
        }

        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.combine(Updates.set("nombre", nombre), Updates.set("coordenadas", coordenadas))).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    public List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante) {
		ServicioSitiosTuristicos servSitiosTuristicos = new ServicioSitiosTuristicos();
        List<SitioTuristico> listaSitiosTuristicos = new LinkedList<>();
		try {
			listaSitiosTuristicos = servSitiosTuristicos.obtenerSitios();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return listaSitiosTuristicos;
    }
	
	@Override
    public boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
		ObjectId objectId = new ObjectId(idRestaurante);
        List<Document> sitiosTuristicosDocumentos = sitiosTuristicos.stream()
                .map(sitioTuristico -> new Document()
                        .append("resumen", sitioTuristico.getResumen())
                        .append("categorias", sitioTuristico.getCategorias())
                        .append("enlaces", sitioTuristico.getEnlaces())
                        .append("imagenes", sitioTuristico.getImagenes()))
                .collect(Collectors.toList());
        UpdateResult result = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.set("sitiosTuristicosDestacados", sitiosTuristicosDocumentos));
        return result.getModifiedCount() == 1;
    }
	
	@Override
    public boolean addPlato(String idRestaurante, Plato plato) {
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

        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId), Updates.push("platos", platoDoc)).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    public boolean removePlato(String idRestaurante, String nombrePlato) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(idRestaurante);
        } catch (IllegalArgumentException e) {
            return false;
        }

        long updatedCount = restauranteCollection.updateOne(Filters.eq("_id", objectId),
                Updates.pull("platos", new Document("nombre", nombrePlato))).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
    public boolean updatePlato(String idRestaurante, Plato plato) {
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

        long updatedCount = restauranteCollection.updateOne(
                Filters.and(
                    Filters.eq("_id", objectId),
                    Filters.elemMatch("platos", Filters.eq("nombre", plato.getNombre()))
                ),
                Updates.set("platos.$", platoDoc)
        ).getModifiedCount();
        return updatedCount > 0;
    }
	
	@Override
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
        restaurante.setCoordenadas(doc.getString("coordenadas"));
        // TODO Obtener los platos y sitios turisticos?

        return restaurante;
    }
	
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
    public List<Restaurante> findAll() {
        List<Restaurante> restaurantesList = new ArrayList<>();
        try (MongoCursor<Document> cursor = restauranteCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Restaurante restaurante = new Restaurante();
                restaurante.setId(doc.getObjectId("_id").toString());
                restaurante.setNombre(doc.getString("nombre"));
                restaurante.setCoordenadas(doc.getString("coordenadas"));
                //TODO Platos y sitios turisticos?
                restaurantesList.add(restaurante);
            }
        }
        return restaurantesList;
    }

	

}
