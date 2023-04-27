package arso.proyecto;

import java.util.List;

import restaurantes.Plato;
import restaurantes.Restaurante;
import restaurantes.ServicioRestaurante;
import restaurantes.SitioTuristico;


public class Lanzador {
	public static void main(String[] args) {
		ServicioRestaurante serv = new ServicioRestaurante();
		String id = serv.altaRestaurante("Goiko", "-1");
		String id2 = serv.altaRestaurante("McDonalds", "2");
		serv.actualizarRestaurante(id, "Burger", "1");
		System.out.println("Sitios Turisticos:");
		List<SitioTuristico> sits = serv.obtenerSitiosTuristicosProximos(id);
		for(SitioTuristico s : sits) {
			System.out.println(s);
		}
		serv.establecerSitiosTuristicosDestacados(id, sits);
		serv.añadirPlato(id, new Plato("a", "a", 15));
		serv.añadirPlato(id, new Plato("b", "b", 15));
		serv.borrarPlato(id, "a");
		serv.actualizarPlato(id, new Plato("a", "a", 20));
		Restaurante rest = serv.recuperarRestaurante(id);
		System.out.println("\nUn restaurante:");
		System.out.println(rest.toString());
		serv.añadirPlato(id2, new Plato("a", "a", 15));
		serv.borrarRestaurante(id2);
		List<Restaurante> rests = serv.recuperarTodosRestaurantes();
		System.out.println("\nTodos los restaurantes:");
		for(Restaurante r : rests) {
			System.out.println(r);
		}
		System.out.println("Fin");
	}
}
