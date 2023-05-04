package modelos;

import java.util.ArrayList;
import java.util.List;

public class Opinion {
    private String id;
    private String nombreRecurso;
    private List<Valoracion> valoraciones;

    public Opinion(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
        this.valoraciones = new ArrayList<>();
    }
    
    public Opinion() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public int getNumeroValoraciones() {
        return valoraciones.size();
    }

    public double getCalificacionMedia() {
        if (valoraciones.isEmpty()) {
            return 0;
        }
        double sumaCalificaciones = valoraciones.stream()
                                                .mapToDouble(Valoracion::getCalificacion)
                                                .sum();
        return sumaCalificaciones / valoraciones.size();
    }

}

