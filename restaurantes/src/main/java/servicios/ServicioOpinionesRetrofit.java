package servicios;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

import modelos.Valoracion;

public class ServicioOpinionesRetrofit implements IServicioOpiniones {
    private OpinionesAPI api;

    public ServicioOpinionesRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:7054")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(OpinionesAPI.class);
    }

    @Override
    public String registrarRecurso(String nombreRecurso) {
        Call<String> registrarRecursoCall = api.registrarRecurso(nombreRecurso);
   
        try {
            Response<String> response = registrarRecursoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Valoracion> obtenerValoraciones(String idOpinion) {
    	
    	Call<List<Valoracion>> obtenerValoracionesCall = api.obtenerValoraciones(idOpinion);
    	   
        try {
            Response<List<Valoracion>> response = obtenerValoracionesCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
            List<Valoracion> valoraciones = response.body();
            if (valoraciones != null) {
                for (Valoracion v : valoraciones) {
                    System.out.println(v);
                }
            }
            return valoraciones;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}