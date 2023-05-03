package tests;

import java.io.IOException;
import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;
import okhttp3.ResponseBody;
import rest.FactoriaRetrofit;
import rest.RestauranteAPI;
import retrofit2.Call;
import retrofit2.Response;

public class PruebasRetrofit {
	static RestauranteAPI api = FactoriaRetrofit.getApi();
	static String idRestaurante = "64515a365764ef6631390f24";
	public static void main(String[] args) {
        obtenerRestaurante();
		crearRestaurante();
		updateRestaurante();
		List<SitioTuristico> sitios = obtenerSitiosTuristicosCercanos();
		setSitiosTuristicosDestacados(sitios);
		String nombrePlato = "Plato 1";
		addPlato(nombrePlato);
		removePlato(nombrePlato);
		String nombrePlato2 = "Plato 2";
		addPlato(nombrePlato2);
		updatePlato(nombrePlato2);
        borrarRestaurante();
        listarRestaurantes();
		
    }
	
	public static void obtenerRestaurante() {
		Call<Restaurante> getRestauranteCall = api.getRestaurante(idRestaurante);
        try {
            Response<Restaurante> response = getRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void crearRestaurante() {
        SolicitudRestaurante nuevoRestauranteRequest = new SolicitudRestaurante("Goiko", 40.123, -3.567);
        Call<ResponseBody> crearRestauranteCall = api.crearRestaurante(nuevoRestauranteRequest);

        try {
            Response<ResponseBody> response = crearRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.headers().get("Location"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void updateRestaurante() {
        SolicitudRestaurante restauranteActualizado = new SolicitudRestaurante("McDonalds", 37.25241153058483, -3.6102678802605594);
        Call<Boolean> updateRestauranteCall = api.updateRestaurante(idRestaurante, restauranteActualizado);

        try {
            Response<Boolean> response = updateRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<SitioTuristico> obtenerSitiosTuristicosCercanos() {
        Call<List<SitioTuristico>> obtenerSitiosTuristicosCercanosCall = api.obtenerSitiosTuristicosCercanos(idRestaurante);
        try {
            Response<List<SitioTuristico>> response = obtenerSitiosTuristicosCercanosCall.execute();
            System.out.println(response.code());
            List<SitioTuristico> sitiosTuristicos = response.body();
            if (sitiosTuristicos != null) {
                for (SitioTuristico sitio : sitiosTuristicos) {
                    System.out.println(sitio);
                }
            }
            return sitiosTuristicos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setSitiosTuristicosDestacados(List<SitioTuristico> sitiosTuristicosDestacados) {
        Call<Boolean> setSitiosTuristicosDestacadosCall = api.setSitiosTuristicosDestacados(idRestaurante, sitiosTuristicosDestacados);

        try {
            Response<Boolean> response = setSitiosTuristicosDestacadosCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPlato(String nombre) {
        Plato nuevoPlato = new Plato(nombre, "Descripción", 12.5);
        Call<Boolean> addPlatoCall = api.addPlato(idRestaurante, nuevoPlato);

        try {
            Response<Boolean> response = addPlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removePlato(String nombre) {
        Call<Boolean> removePlatoCall = api.removePlato(idRestaurante, nombre);

        try {
            Response<Boolean> response = removePlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updatePlato(String nombre) {
        Plato platoActualizado = new Plato(nombre, "Descripción actualizada", 15.0);
        Call<Boolean> updatePlatoCall = api.updatePlato(idRestaurante, platoActualizado);

        try {
            Response<Boolean> response = updatePlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void borrarRestaurante() {
        Call<Boolean> borrarRestauranteCall = api.borrarRestaurante(idRestaurante);

        try {
            Response<Boolean> response = borrarRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listarRestaurantes() {
        Call<List<ResumenRestaurante>> listarRestaurantesCall = api.listarRestaurantes();

        try {
            Response<List<ResumenRestaurante>> response = listarRestaurantesCall.execute();
            System.out.println(response.code());
            List<ResumenRestaurante> restaurantes = response.body();
            for (ResumenRestaurante restaurante : restaurantes) {
                System.out.println(restaurante);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
