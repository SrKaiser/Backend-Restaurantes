package restaurantes;

import java.util.LinkedList;
import java.util.List;

public class Restaurante {
	private String id;
	private String nombre;
	private String coordenadas;
	private List<SitioTuristico> sitiosTuristicos;
	
	public Restaurante(String nombre, String coordenadas) {
		this.nombre = nombre;
		this.coordenadas = coordenadas;
		this.sitiosTuristicos = new LinkedList<SitioTuristico>();
	}
	
	public Restaurante() {
		this.sitiosTuristicos = new LinkedList<SitioTuristico>();
	}

	public void addSitioTuristico(SitioTuristico sitio) {
		sitiosTuristicos.add(sitio);
	}
	
	public void deleteSitioTuristico(SitioTuristico sitio) {
		sitiosTuristicos.remove(sitio);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", coordenadas=" + coordenadas + "]";
	}	
}
