package restaurantes;

import java.util.LinkedList;
import java.util.List;

public class SitioTuristico {
	
	private String titulo;
	private String resumen;
	private List<String> categorias;
	private List<String> enlaces;
	private List<String> imagenes;
	
	public SitioTuristico(String titulo, String resumen, List<String> categorias, List<String> enlaces, List<String> imagenes) {
		this.titulo = titulo;
		this.resumen = resumen;
		this.categorias = categorias;
		this.enlaces = enlaces;
		this.imagenes = imagenes;
	}
	
	public SitioTuristico() {
		this.categorias = new LinkedList<String>();
		this.enlaces = new LinkedList<String>();
		this.imagenes = new LinkedList<String>();
	}
		
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumen() {
		return resumen;
	}
	
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
	public List<String> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	
	public void addCategoria(String categoria) {
		categorias.add(categoria);
	}
	
	public List<String> getEnlaces() {
		return enlaces;
	}
	
	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
	}
	
	public void addEnlace(String enlace) {
		categorias.add(enlace);
	}
	
	public List<String> getImagenes() {
		return imagenes;
	}
	
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	
	public void addImagen(String imagen) {
		categorias.add(imagen);
	}

	@Override
	public String toString() {
		return "SitioTuristico [resumen=" + resumen + ", categorias=" + categorias + ", enlaces=" + enlaces
				+ ", imagenes=" + imagenes + "]";
	}
	
	
	
	
}
