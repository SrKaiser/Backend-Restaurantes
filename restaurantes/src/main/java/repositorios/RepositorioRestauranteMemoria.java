package repositorios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import servicios.ServicioSitiosTuristicos;

public class RepositorioRestauranteMemoria implements IRepositorioRestaurante{
	private final Map<String, Restaurante> restaurantes = new HashMap<>();
	
	@Override
	public String create(String nombre, double latitud, double longitud) {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
        Restaurante restaurante = new Restaurante(id, nombre, latitud, longitud);
        restaurantes.put(id, restaurante);
        return id;
	}

	@Override
	public boolean update(String idRestaurante, String nombre, double latitud, double longitud) {
		Restaurante restaurante = restaurantes.get(idRestaurante);

	    if (restaurante == null) {
	        return false;
	    }

	    restaurante.setNombre(nombre);
	    restaurante.setLatitud(latitud);
	    restaurante.setLongitud(longitud);

	    restaurantes.put(idRestaurante, restaurante);
	    return true;
	}
	
	@Override
	public List<SitioTuristico> findSitiosTuristicosProximos(String idRestaurante) {
		ServicioSitiosTuristicos servSitiosTuristicos = new ServicioSitiosTuristicos();
	    Restaurante r = restaurantes.get(idRestaurante);
	    List<SitioTuristico> listaSitiosTuristicos = new LinkedList<>();

	    if (r == null) {
	        return listaSitiosTuristicos;
	    }

	    try {
	        listaSitiosTuristicos = servSitiosTuristicos.obtenerSitios(r.getLatitud(), r.getLongitud());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return listaSitiosTuristicos;
	}
	
	@Override
	public boolean setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
	    Restaurante restaurante = restaurantes.get(idRestaurante);

	    if (restaurante == null) {
	        return false;
	    }

	    restaurante.setSitiosTuristicos(sitiosTuristicos);
	    return true;
	}

	@Override
	public boolean addPlato(String idRestaurante, Plato plato) {
	    Restaurante restaurante = restaurantes.get(idRestaurante);

	    if (restaurante == null) {
	        return false;
	    }

	    restaurante.addPlato(plato);
	    return true;
	}

	@Override
	public boolean removePlato(String idRestaurante, String nombrePlato) {
	    Restaurante restaurante = restaurantes.get(idRestaurante);

	    if (restaurante == null) {
	        return false;
	    }

	    List<Plato> platos = restaurante.getPlatos();
	    boolean removed = platos.removeIf(plato -> plato.getNombre().equals(nombrePlato));
	    restaurante.setPlatos(platos);
	    return removed;
	}

	@Override
	public boolean updatePlato(String idRestaurante, Plato plato) {
	    Restaurante restaurante = restaurantes.get(idRestaurante);

	    if (restaurante == null) {
	        return false;
	    }

	    List<Plato> platos = restaurante.getPlatos();
	    boolean updated = false;

	    for (int i = 0; i < platos.size(); i++) {
	        if (platos.get(i).getNombre().equals(plato.getNombre())) {
	            platos.set(i, plato);
	            updated = true;
	            break;
	        }
	    }

	    if (updated) {
	        restaurante.setPlatos(platos);
	    }

	    return updated;
	}


	@Override
	public Restaurante findById(String idRestaurante) {
	    return restaurantes.get(idRestaurante);
	}

	@Override
	public boolean delete(String idRestaurante) {
	    Restaurante removed = restaurantes.remove(idRestaurante);
	    return removed != null;
	}

	@Override
	public List<ResumenRestaurante> findAll() {
	    List<ResumenRestaurante> restaurantesList = new ArrayList<>();
	    for (Restaurante restaurante : restaurantes.values()) {
	        ResumenRestaurante resumen = new ResumenRestaurante();
	        resumen.setId(restaurante.getId());
	        resumen.setNombre(restaurante.getNombre());
	        resumen.setLatitud(restaurante.getLatitud());
	        resumen.setLongitud(restaurante.getLongitud());
	        resumen.setNumeroPlatos(restaurante.getPlatos().size());
	        resumen.setNumeroSitiosTuristicos(restaurante.getSitiosTuristicos().size());
	        restaurantesList.add(resumen);
	    }
	    return restaurantesList;
	}


}
