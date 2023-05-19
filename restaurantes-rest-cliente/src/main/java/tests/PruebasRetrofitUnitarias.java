package tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelos.Plato;
import modelos.Restaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;

public class PruebasRetrofitUnitarias {
	
	private ServicioRetrofit servicioRetrofit;

	@Before
	public void setUp() throws Exception {
		servicioRetrofit = new ServicioRetrofit();
	}

	@Test
    public void pruebaCrearRestaurante() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        assertNotNull(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaObtenerRestaurante() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaActualizarRestaurante() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        SolicitudRestaurante restauranteActualizado = new SolicitudRestaurante("PruebaActualizada", 20.0, 30.0);
        servicioRetrofit.updateRestaurante(idRestaurante, restauranteActualizado);
        Restaurante r = servicioRetrofit.obtenerRestaurante(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante);
	}
	
	@Test
    public void pruebaBorrarRestaurante() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaObtenerSitiosTuristicos() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("McDonalds",  37.25241153058483, -3.6102678802605594));
        List<SitioTuristico> sitios = servicioRetrofit.obtenerSitiosTuristicosCercanos(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaEstablecerSitiosTuristicos() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("McDonalds",  37.25241153058483, -3.6102678802605594));
        List<SitioTuristico> sitios = servicioRetrofit.obtenerSitiosTuristicosCercanos(idRestaurante);
        servicioRetrofit.setSitiosTuristicosDestacados(idRestaurante, sitios);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaAddPlato() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaActualizarPlato() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        Plato platoActualizado = new Plato("Plato actualizado", "Descripción actualizada", 15.0);
        servicioRetrofit.updatePlato(idRestaurante, platoActualizado);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaBorrarPlato() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        servicioRetrofit.removePlato(idRestaurante, "Plato 1");
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaListarRestaurantes() throws IOException {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        String idRestaurante2 = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba 2", 50.0, 70.0));
        servicioRetrofit.borrarRestaurante(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante2);
    }

}
