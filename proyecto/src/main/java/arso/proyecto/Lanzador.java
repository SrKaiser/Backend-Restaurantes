package arso.proyecto;

import java.util.List;

import restaurantes.Plato;
import restaurantes.Restaurante;
import restaurantes.ResumenRestaurante;
import restaurantes.ServicioRestaurante;
import restaurantes.SitioTuristico;


public class Lanzador {
	public static void main(String[] args) {
		ServicioRestaurante serv = new ServicioRestaurante();
		String id = serv.altaRestaurante("Goiko", 40.42039145624014, -3.6996503622016954);
		String id2 = serv.altaRestaurante("McDonalds", 37.25241153058483, -3.6102678802605594);
		serv.actualizarRestaurante(id, "Burger", 42.347384117579004, -3.699256208170313);
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
		List<ResumenRestaurante> rests = serv.recuperarTodosRestaurantes();
		System.out.println("\nTodos los restaurantes:");
		for(ResumenRestaurante r : rests) {
			System.out.println(r);
		}
		System.out.println("Fin");
	}
}
