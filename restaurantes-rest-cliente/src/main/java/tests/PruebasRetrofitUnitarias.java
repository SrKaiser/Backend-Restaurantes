package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;

public class PruebasRetrofitUnitarias {
	
	private ServicioRetrofit servicioRetrofit;

	@Before
	public void setUp() throws Exception {
		servicioRetrofit = new ServicioRetrofit();
	}

	@Test
    public void pruebaCrearRestaurante() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        assertNotNull(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaObtenerRestaurante() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        assertEquals("Prueba", restaurante.getNombre());  
        assertEquals(10.0, restaurante.getLatitud(), 0.001); 
        assertEquals(20.0, restaurante.getLongitud(), 0.001);
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaActualizarRestaurante() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        SolicitudRestaurante restauranteActualizado = new SolicitudRestaurante("PruebaActualizada", 20.0, 30.0);
        servicioRetrofit.updateRestaurante(idRestaurante, restauranteActualizado);
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        assertEquals("PruebaActualizada", restaurante.getNombre());  
        assertEquals(20.0, restaurante.getLatitud(), 0.001); 
        assertEquals(30.0, restaurante.getLongitud(), 0.001);
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
	}
	
	@Test
    public void pruebaObtenerSitiosTuristicos() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("McDonalds",  37.25241153058483, -3.6102678802605594));
        List<SitioTuristico> sitios = servicioRetrofit.obtenerSitiosTuristicosCercanos(idRestaurante);
        assertNotNull(sitios);
        assertFalse(sitios.isEmpty());
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaEstablecerSitiosTuristicos() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("McDonalds",  37.25241153058483, -3.6102678802605594));
        List<SitioTuristico> sitios = servicioRetrofit.obtenerSitiosTuristicosCercanos(idRestaurante);
        servicioRetrofit.setSitiosTuristicosDestacados(idRestaurante, sitios);
        
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        List<SitioTuristico> sitiosDestacados = restaurante.getSitiosTuristicos();
        assertNotNull(sitiosDestacados);
        assertEquals(sitios.size(), sitiosDestacados.size());
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	
	@Test
    public void pruebaAddPlato() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        List<Plato> platos = restaurante.getPlatos();
        assertNotNull(platos);
        assertEquals(1, platos.size()); 
        assertEquals(nuevoPlato, platos.get(0));
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaActualizarPlato() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        Plato platoActualizado = new Plato("Plato 1", "Descripción actualizada", 15.0);
        servicioRetrofit.updatePlato(idRestaurante, platoActualizado);
        
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        List<Plato> platos = restaurante.getPlatos();
        assertEquals(platoActualizado, platos.get(0)); 
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaBorrarPlato() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        Plato nuevoPlato = new Plato("Plato 1", "Descripción", 12.5);
        servicioRetrofit.addPlato(idRestaurante, nuevoPlato);
        servicioRetrofit.removePlato(idRestaurante, "Plato 1");
        
        Restaurante restaurante = servicioRetrofit.obtenerRestaurante(idRestaurante);
        List<Plato> platos = restaurante.getPlatos();
        assertTrue(platos.isEmpty());
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
    }
	

	@Test
    public void pruebaListarRestaurantes() {
        String idRestaurante = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba", 10.0, 20.0));
        String idRestaurante2 = servicioRetrofit.crearRestaurante(new SolicitudRestaurante("Prueba 2", 50.0, 70.0));
        List<ResumenRestaurante> restaurantes = servicioRetrofit.listarRestaurantes();
        
        assertNotNull(restaurantes);
        assertTrue(restaurantes.size() >= 2);
        
        servicioRetrofit.borrarRestaurante(idRestaurante);
        servicioRetrofit.borrarRestaurante(idRestaurante2);
    }

}
