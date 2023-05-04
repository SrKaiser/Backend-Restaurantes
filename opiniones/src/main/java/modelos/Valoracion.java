package modelos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Valoracion {
	
	@JsonProperty("correoElectronico")
    private String correoElectronico;
	@JsonProperty("fecha")
    private LocalDate fecha;
	@JsonProperty("calificacion")
    private int calificacion;
	@JsonProperty("comentario")
    private String comentario;

    public Valoracion(String correoElectronico, int calificacion) {
        this.correoElectronico = correoElectronico;
        this.fecha = LocalDate.now();
        this.calificacion = calificacion;
    }
    
    public Valoracion(String correoElectronico, int calificacion, String comentario) {
        this.correoElectronico = correoElectronico;
        this.fecha = LocalDate.now();
        this.calificacion = calificacion;
        this.comentario = comentario;
    }
    
    public Valoracion() {}

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

