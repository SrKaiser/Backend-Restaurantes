package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import repositorios.RepositorioRestauranteMemoria;

public class PruebasUnitarias {
	
	private RepositorioRestauranteMemoria repositorio;
	
	@Before
	public void setUp() throws Exception {
		PruebasBasicas.isTestEnvironment = true;
		repositorio = new RepositorioRestauranteMemoria();
	}

	@Test
    public void testAltaRestaurante() {
        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
        Restaurante restaurante = repositorio.recuperarRestaurante(id);
        Assert.assertNotNull(restaurante);
        Assert.assertEquals("Restaurante Test", restaurante.getNombre());
        Assert.assertEquals(10.0, restaurante.getLatitud(), 0.001);
        Assert.assertEquals(20.0, restaurante.getLongitud(), 0.001);
    }
	
	@Test
    public void testActualizarRestaurante() {
        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
        boolean resultado = repositorio.actualizarRestaurante(id, "Restaurante Actualizado", 30.0, 40.0);
        Assert.assertTrue(resultado);
        Restaurante restaurante = repositorio.recuperarRestaurante(id);
        Assert.assertEquals("Restaurante Actualizado", restaurante.getNombre());
        Assert.assertEquals(30.0, restaurante.getLatitud(), 0.001);
        Assert.assertEquals(40.0, restaurante.getLongitud(), 0.001);
    }
	
	 @Test
	    public void testFindSitiosTuristicosProximos() {
	        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        List<SitioTuristico> sitiosTuristicos = repositorio.findSitiosTuristicosProximos(id);
	        Assert.assertNotNull(sitiosTuristicos);
	    }

	    @Test
	    public void testSetSitiosTuristicosDestacados() {
	        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        List<SitioTuristico> sitiosTuristicos = new ArrayList<>();
	        SitioTuristico sitio1 = new SitioTuristico("Sitio 1", "Resumen 1", null, null, null);
	        SitioTuristico sitio2 = new SitioTuristico("Sitio 2", "Resumen 2", null, null, null);
	        sitiosTuristicos.add(sitio1);
	        sitiosTuristicos.add(sitio2);
	        boolean resultado = repositorio.setSitiosTuristicosDestacados(id, sitiosTuristicos);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = repositorio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getSitiosTuristicos());
	        Assert.assertEquals(2, restaurante.getSitiosTuristicos().size());
	    }
	    
	    @Test
	    public void testAddPlato() {
	        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        boolean resultado = repositorio.addPlato(id, plato);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = repositorio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(1, restaurante.getPlatos().size());
	        Assert.assertEquals("Plato Test", restaurante.getPlatos().get(0).getNombre());
	        Assert.assertEquals(10.0, restaurante.getPlatos().get(0).getPrecio(), 0.001);
	    }

	    
	    @Test
	    public void testRemovePlato() {
	        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        repositorio.addPlato(id, plato);
	        boolean resultado = repositorio.removePlato(id, "Plato Test");
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = repositorio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(0, restaurante.getPlatos().size());
	    }

	    @Test
	    public void testUpdatePlato() {
	        String id = repositorio.altaRestaurante("Restaurante Test", 10.0, 20.0);
	        Plato plato = new Plato("Plato Test", "Descripcion Test", 10.0);
	        repositorio.addPlato(id, plato);
	        Plato platoActualizado = new Plato("Plato Actualizado", "Descripcion Actualizada", 20.0);
	        platoActualizado.setNombre("Plato Test");
	        boolean resultado = repositorio.updatePlato(id, platoActualizado);
	        Assert.assertTrue(resultado);
	        Restaurante restaurante = repositorio.recuperarRestaurante(id);
	        Assert.assertNotNull(restaurante.getPlatos());
	        Assert.assertEquals(1, restaurante.getPlatos().size());
	        Assert.assertEquals("Plato Actualizado", restaurante.getPlatos().get(0).getNombre());
	        Assert.assertEquals(20.0, restaurante.getPlatos().get(0).getPrecio(), 0.001);
	    }

	    @Test
	    public void testListarRestaurantes() {
	        String id1 = repositorio.altaRestaurante("Restaurante 1", 10.0, 20.0);
	        repositorio.addPlato(id1, new Plato("Plato Test 1", "Descripcion Test 1", 10.0));
	        String id2 = repositorio.altaRestaurante("Restaurante 2", 30.0, 40.0);
	        repositorio.addPlato(id2, new Plato("Plato Test 2", "Descripcion Test 1", 10.0));
	        List<ResumenRestaurante> restaurantes = repositorio.listarRestaurantes();
	        Assert.assertEquals(2, restaurantes.size());
	        Assert.assertEquals("Restaurante 1", restaurantes.get(0).getNombre());
	        Assert.assertEquals(1, restaurantes.get(0).getNumeroPlatos());
	        Assert.assertEquals(0, restaurantes.get(0).getNumeroSitiosTuristicos());
	        Assert.assertEquals("Restaurante 2", restaurantes.get(1).getNombre());
	        Assert.assertEquals(1, restaurantes.get(1).getNumeroPlatos());
	        Assert.assertEquals(0, restaurantes.get(1).getNumeroSitiosTuristicos());
	    }

}
