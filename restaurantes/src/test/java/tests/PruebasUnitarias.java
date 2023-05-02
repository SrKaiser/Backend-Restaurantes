package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import servicios.FactoriaServicios;
import servicios.IServicioRestaurante;

public class PruebasUnitarias {
	
	private IServicioRestaurante servicio;
	
	@Before
	public void setUp() throws Exception {
		PruebasBasicas.isTestEnvironment = true;
		servicio = FactoriaServicios.getServicio(IServicioRestaurante.class);
	}

	@Test
    public void testAltaRestaurante() {
        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
        Restaurante restaurante = servicio.recuperarRestaurante(id);
        Assert.assertNotNull(restaurante);
        Assert.assertEquals("Restaurante Test", restaurante.getNombre());
        Assert.assertEquals(10.0, restaurante.getLatitud(), 0.001);
        Assert.assertEquals(20.0, restaurante.getLongitud(), 0.001);
    }
	
	@Test
    public void testActualizarRestaurante() {
        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
        boolean resultado = servicio.actualizarRestaurante(id, "Restaurante Actualizado", 30.0, 40.0);
        Assert.assertTrue(resultado);
        Restaurante restaurante = servicio.recuperarRestaurante(id);
        Assert.assertEquals("Restaurante Actualizado", restaurante.getNombre());
        Assert.assertEquals(30.0, restaurante.getLatitud(), 0.001);
        Assert.assertEquals(40.0, restaurante.getLongitud(), 0.001);
    }
	
	 @Test
	    public void testFindSitiosTuristicosProximos() {
	        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        List<SitioTuristico> sitiosTuristicos = servicio.obtenerSitiosTuristicosProximos(id);
	        Assert.assertNotNull(sitiosTuristicos);
	    }

	    @Test
	    public void testSetSitiosTuristicosDestacados() {
	        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        List<SitioTuristico> sitiosTuristicos = servicio.obtenerSitiosTuristicosProximos(id);;
	        boolean resultado = servicio.establecerSitiosTuristicosDestacados(id, sitiosTuristicos);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = servicio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getSitiosTuristicos());
	    }
	    
	    @Test
	    public void testAddPlato() {
	        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        boolean resultado = servicio.añadirPlato(id, plato);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = servicio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(1, restaurante.getPlatos().size());
	        Assert.assertEquals("Plato Test", restaurante.getPlatos().get(0).getNombre());
	        Assert.assertEquals(10.0, restaurante.getPlatos().get(0).getPrecio(), 0.001);
	    }

	    
	    @Test
	    public void testRemovePlato() {
	        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        servicio.añadirPlato(id, plato);
	        boolean resultado = servicio.borrarPlato(id, "Plato Test");
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = servicio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(0, restaurante.getPlatos().size());
	    }

	    @Test
	    public void testUpdatePlato() {
	        String id = servicio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        servicio.añadirPlato(id, plato);
	        Plato platoActualizado = new Plato("Plato Test", "Descripcion Actualizada", 20.0);
	        boolean resultado = servicio.actualizarPlato(id, platoActualizado);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = servicio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(1, restaurante.getPlatos().size());
	        Assert.assertEquals("Descripcion Actualizada", restaurante.getPlatos().get(0).getDescripcion());
	        Assert.assertEquals(20.0, restaurante.getPlatos().get(0).getPrecio(), 0.001);
	    }

	    @Test
	    public void testListarRestaurantes() {
	        String id1 = servicio.altaRestaurante("Restaurante 1", 10.0, 20.0);
	        servicio.añadirPlato(id1, new Plato("Plato Test 1", "Descripcion Test 1", 10.0));
	        String id2 = servicio.altaRestaurante("Restaurante 2", 30.0, 40.0);
	        servicio.añadirPlato(id2, new Plato("Plato Test 2", "Descripcion Test 2", 20.0));
	        List<ResumenRestaurante> restaurantes = servicio.recuperarTodosRestaurantes();
	        Assert.assertEquals(2, restaurantes.size());
	        Assert.assertEquals("Restaurante 1", restaurantes.get(0).getNombre());
	        Assert.assertEquals(1, restaurantes.get(0).getNumeroPlatos());
	        Assert.assertEquals(0, restaurantes.get(0).getNumeroSitiosTuristicos());
	        Assert.assertEquals("Restaurante 2", restaurantes.get(1).getNombre());
	        Assert.assertEquals(1, restaurantes.get(1).getNumeroPlatos());
	        Assert.assertEquals(0, restaurantes.get(1).getNumeroSitiosTuristicos());
	    }

}
