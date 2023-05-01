package rest;

import retrofit2.Call;
import retrofit2.http.*;

import modelos.Restaurante;
import modelos.SolicitudRestaurante;
import okhttp3.ResponseBody;

public interface RestauranteAPI {
	
    @GET("restaurantes/{id}")
    Call<Restaurante> getRestaurante(@Path("id") String id);

    @POST("restaurantes")
    Call<ResponseBody> crearRestaurante(@Body SolicitudRestaurante restauranteRequest);



}

