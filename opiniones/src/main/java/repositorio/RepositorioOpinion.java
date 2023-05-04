package repositorio;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import modelos.Opinion;
import modelos.Valoracion;

public class RepositorioOpinion implements IRepositorioOpinion {
	private final MongoCollection<Document> opiniones;

	public RepositorioOpinion() {

		String connectionString = "mongodb://arso:arso@ac-v8ez3vj-shard-00-00.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-01.kzwz6ia.mongodb.net:27017,ac-v8ez3vj-shard-00-02.kzwz6ia.mongodb.net:27017/?ssl=true&replicaSet=atlas-b3t6zg-shard-0&authSource=admin&retryWrites=true&w=majority";
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(connectionString)).build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("proyecto-arso");
		this.opiniones = database.getCollection("opiniones");
	}

	@Override
	public String create(Opinion opinion) {
		Document doc = new Document().append("_id", opinion.getId()).append("nombreRecurso", opinion.getNombreRecurso())
				.append("valoraciones", opinion.getValoraciones());
		opiniones.insertOne(doc);
		return opinion.getId();
	}

	@Override
	public Opinion findById(String id) {
		Document doc = opiniones.find(Filters.eq("_id", id)).first();
		if (doc == null) {
			return null;
		}
		return documentToOpinion(doc);
	}

	@Override
	public List<Opinion> findAll() {
		List<Opinion> result = new ArrayList<>();
		for (Document doc : opiniones.find()) {
			result.add(documentToOpinion(doc));
		}
		return result;
	}

	@Override
	public void update(Opinion opinion) {
		opiniones.updateOne(Filters.eq("_id", opinion.getId()),
				Updates.combine(Updates.set("nombreRecurso", opinion.getNombreRecurso()),
						Updates.set("valoraciones", opinion.getValoraciones())));
	}

	@Override
	public void delete(String id) {
		opiniones.deleteOne(Filters.eq("_id", id));
	}

	private Opinion documentToOpinion(Document doc) {
		Opinion opinion = new Opinion();
		opinion.setId(doc.getString("_id"));
		opinion.setNombreRecurso(doc.getString("nombreRecurso"));
		opinion.setValoraciones(doc.getList("valoraciones", Valoracion.class));
		return opinion;
	}
}
