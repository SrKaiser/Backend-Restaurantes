package rest;

import java.io.IOException;

import modelos.Restaurante;
import modelos.SolicitudRestaurante;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PruebasRetrofit {
	static RestauranteAPI api = FactoriaRetrofit.getApi();
	public static void main(String[] args) {
        obtenerRestaurante();
		crearRestaurante();
    }
	
	public static void obtenerRestaurante() {
		Call<Restaurante> getRestauranteCall = api.getRestaurante("644d43a346426330f3e1c98f");
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
}
