package tests;

import java.io.IOException;
import java.util.List;

import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;
import okhttp3.ResponseBody;
import rest.RestauranteAPI;
import retrofit.FactoriaRetrofit;
import retrofit2.Call;
import retrofit2.Response;

public class ServicioRetrofit {
	private RestauranteAPI api;
	
	public ServicioRetrofit() {
		this.api = FactoriaRetrofit.getApi();
	}
	
	public Restaurante obtenerRestaurante(String idRestaurante) {
		Call<Restaurante> getRestauranteCall = api.getRestaurante(idRestaurante);
        try {
            Response<Restaurante> response = getRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public String crearRestaurante(SolicitudRestaurante nuevoRestaurante) {
        
        Call<ResponseBody> crearRestauranteCall = api.crearRestaurante(nuevoRestaurante);

        try {
            Response<ResponseBody> response = crearRestauranteCall.execute();
            System.out.println(response.code());
            if (response.isSuccessful()) {
                String idRestaurante = response.body().string();
                System.out.println("ID del restaurante creado: " + idRestaurante);
                return idRestaurante;
            } else {
                System.out.println("Error al crear el restaurante");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
	}
	
	public void updateRestaurante(String idRestaurante, SolicitudRestaurante restauranteActualizado) {
        Call<Boolean> updateRestauranteCall = api.updateRestaurante(idRestaurante, restauranteActualizado);

        try {
            Response<Boolean> response = updateRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SitioTuristico> obtenerSitiosTuristicosCercanos(String idRestaurante) {
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
    
    public void setSitiosTuristicosDestacados(String idRestaurante, List<SitioTuristico> sitiosTuristicosDestacados) {
        Call<Boolean> setSitiosTuristicosDestacadosCall = api.setSitiosTuristicosDestacados(idRestaurante, sitiosTuristicosDestacados);

        try {
            Response<Boolean> response = setSitiosTuristicosDestacadosCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlato(String idRestaurante, Plato plato) {
        Call<Boolean> addPlatoCall = api.addPlato(idRestaurante, plato);

        try {
            Response<Boolean> response = addPlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlato(String idRestaurante, String nombre) {
        Call<Boolean> removePlatoCall = api.removePlato(idRestaurante, nombre);

        try {
            Response<Boolean> response = removePlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePlato(String idRestaurante, Plato platoActualizado) {
        Call<Boolean> updatePlatoCall = api.updatePlato(idRestaurante, platoActualizado);

        try {
            Response<Boolean> response = updatePlatoCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrarRestaurante(String idRestaurante) {
        Call<Boolean> borrarRestauranteCall = api.borrarRestaurante(idRestaurante);

        try {
            Response<Boolean> response = borrarRestauranteCall.execute();
            System.out.println(response.code());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ResumenRestaurante> listarRestaurantes() {
        Call<List<ResumenRestaurante>> listarRestaurantesCall = api.listarRestaurantes();

        try {
            Response<List<ResumenRestaurante>> response = listarRestaurantesCall.execute();
            System.out.println(response.code());
            List<ResumenRestaurante> restaurantes = response.body();
            for (ResumenRestaurante restaurante : restaurantes) {
                System.out.println(restaurante);
            }
            return restaurantes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
