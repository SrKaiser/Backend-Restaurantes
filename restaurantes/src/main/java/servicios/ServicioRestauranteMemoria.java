package servicios;

import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import repositorios.FactoriaRepositorios;
import repositorios.IRepositorioRestaurante;

public class ServicioRestauranteMemoria implements IServicioRestaurante{
	
	private IRepositorioRestaurante repositorioRestaurante;

    public ServicioRestauranteMemoria() {
    	this.repositorioRestaurante  = FactoriaRepositorios.getRepositorio(IRepositorioRestaurante.class);
    }

	@Override
	public String altaRestaurante(String nombre, double latitud, double longitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean actualizarRestaurante(String id, String nombre, double latitud, double longitud) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SitioTuristico> obtenerSitiosTuristicosProximos(String idRestaurante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean establecerSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean añadirPlato(String idRestaurante, Plato plato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarPlato(String idRestaurante, String nombrePlato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actualizarPlato(String idRestaurante, Plato plato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Restaurante recuperarRestaurante(String idRestaurante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean borrarRestaurante(String idRestaurante) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ResumenRestaurante> recuperarTodosRestaurantes() {
		// TODO Auto-generated method stub
		return null;
	}

}
