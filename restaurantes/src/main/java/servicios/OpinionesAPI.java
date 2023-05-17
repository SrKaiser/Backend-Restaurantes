package servicios;

import java.util.List;

import modelos.Valoracion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpinionesAPI {
    @POST("api/opiniones/registrarRecurso/{nombreRecurso}")
    Call<String> registrarRecurso(@Path("nombreRecurso") String nombreRecurso);

    @GET("api/opiniones/{idOpinion}/valoraciones")
    Call<List<Valoracion>> obtenerValoraciones(@Path("idOpinion") String idOpinion);

}

