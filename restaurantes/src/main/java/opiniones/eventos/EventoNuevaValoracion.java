package opiniones.eventos;

import modelos.Valoracion;

public class EventoNuevaValoracion {
	private String idOpinion;
	private Valoracion nuevaValoracion;
	private int numValoraciones;
	private double calificacionMedia;
	
	public String getIdOpinion() {
		return idOpinion;
	}
	
	public void setIdOpinion(String idOpinion) {
		this.idOpinion = idOpinion;
	}
	
	public Valoracion getNuevaValoracion() {
		return nuevaValoracion;
	}
	
	public void setNuevaValoracion(Valoracion nuevaValoracion) {
		this.nuevaValoracion = nuevaValoracion;
	}
	
	public int getNumValoraciones() {
		return numValoraciones;
	}
	
	public void setNumValoraciones(int numValoraciones) {
		this.numValoraciones = numValoraciones;
	}
	
	public double getCalificacionMedia() {
		return calificacionMedia;
	}
	
	public void setCalificacionMedia(double calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}
	
	
}
