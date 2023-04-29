package repositorios;


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

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import servicios.ServicioSitiosTuristicos;

public class RepositorioRestaurante implements IRepositorioRestaurante{

	private final MongoCollection<Document> restauranteCollection;

    public RepositorioRestaurante(MongoDatabase database) {
        this.restauranteCollection = database.getCollection("restaurantes");
    }

	@Override
	public String insert(String nombre, double latitud, double longitud) {
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
    public List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante) {
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

        Document doc = restauranteCollection.find(Filters.eq("_id", objectId)).first();
        if (doc == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setLatitud(doc.getDouble("latitud"));
        restaurante.setLongitud(doc.getDouble("longitud"));
 
        return restaurante;
    }
	
	@Override
    public boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
		ObjectId objectId = new ObjectId(idRestaurante);
        List<Document> sitiosTuristicosDocumentos = sitiosTuristicos.stream()
                .map(sitioTuristico -> new Document()
                		.append("titulo", sitioTuristico.getTitulo())
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
