package servicios;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpinionesAPI {
    @POST("opiniones/registrarRecurso/{nombreRecurso}")
    Call<Object> registrarRecurso(@Path("nombreRecurso") String nombreRecurso);

    @GET("opiniones/{idOpinion}/valoraciones")
    Call<String> obtenerValoraciones(@Path("idOpinion") String idOpinion);

}
	
