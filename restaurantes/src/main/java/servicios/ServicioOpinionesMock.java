package servicios;

import java.util.ArrayList;
import java.util.List;

import modelos.Valoracion;

public class ServicioOpinionesMock implements IServicioOpiniones {

    @Override
    public String registrarRecurso(String nombreRecurso) {
        // Devuelve un ID ficticio para las pruebas unitarias
        return "mock_opinion_id";
    }

    @Override
    public List<Valoracion> obtenerValoraciones(String opinionId) {
        // Devuelve una lista vacía para las pruebas unitarias
        return new ArrayList<>();
    }
}
