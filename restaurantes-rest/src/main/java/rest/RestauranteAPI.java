package rest;

import retrofit2.Call;
import retrofit2.http.*;

import modelos.Restaurante;
import modelos.SolicitudRestaurante;
import modelos.Plato;
import modelos.SitioTuristico;
import modelos.ResumenRestaurante;

import java.util.List;

import okhttp3.ResponseBody;

public interface RestauranteAPI {

    @GET("restaurantes/{id}")
    Call<Restaurante> getRestaurante(@Path("id") String id);

    @POST("restaurantes")
    Call<ResponseBody> crearRestaurante(@Body SolicitudRestaurante restauranteRequest);

    @PUT("restaurantes/{id}")
    Call<Boolean> updateRestaurante(@Path("id") String idRestaurante, @Body SolicitudRestaurante restauranteRequest);

    @GET("restaurantes/{id}/sitiosTuristicos")
    Call<List<SitioTuristico>> obtenerSitiosTuristicosCercanos(@Path("id") String idRestaurante);

    @PUT("restaurantes/{id}/sitiosTuristicos")
    Call<Boolean> setSitiosTuristicosDestacados(@Path("id") String idRestaurante, @Body List<SitioTuristico> sitiosTuristicos);

    @POST("restaurantes/{id}/platos")
    Call<Boolean> addPlato(@Path("id") String idRestaurante, @Body Plato plato);

    @DELETE("restaurantes/{id}/platos/{nombrePlato}")
    Call<Boolean> removePlato(@Path("id") String idRestaurante, @Path("nombrePlato") String nombrePlato);

    @PUT("restaurantes/{id}/platos")
    Call<Boolean> updatePlato(@Path("id") String idRestaurante, @Body Plato plato);

    @DELETE("restaurantes/{id}")
    Call<Boolean> borrarRestaurante(@Path("id") String idRestaurante);

    @GET("restaurantes")
    Call<List<ResumenRestaurante>> listarRestaurantes();


}

