package servicios;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioOpinionesRetrofit implements IServicioOpiniones {
    private OpinionesAPI api;

    public ServicioOpinionesRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5193/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(OpinionesAPI.class);
    }

    @Override
    public Object registrarRecurso(String nombreRecurso) {    	
        Call<Object> registrarRecursoCall = api.registrarRecurso(nombreRecurso);
   
        try {
            Response<Object> response = registrarRecursoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String obtenerValoraciones(String idOpinion) {
    	
    	Call<String> obtenerValoracionesCall = api.obtenerValoraciones(idOpinion);
    	   
        try {
            Response<String> response = obtenerValoracionesCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}