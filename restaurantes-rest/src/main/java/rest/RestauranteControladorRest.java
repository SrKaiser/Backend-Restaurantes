package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import excepciones.RestauranteNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import modelos.Restaurante;
import modelos.SolicitudRestaurante;
import retrofit2.http.PUT;
import servicios.IServicioRestaurante;
import servicios.ServicioRestaurante;

@Api
@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteControladorRest {
	
	private final IServicioRestaurante servicioRestaurante;

    public RestauranteControladorRest() {
        this.servicioRestaurante = new ServicioRestaurante();
    }
    
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Recupera un restaurante por ID", response = Restaurante.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restaurante recuperado correctamente"),
        @ApiResponse(code = 404, message = "Restaurante no encontrado")
    })
    // Ejemplo de uso con curl: 
    // curl -X GET http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
    public Response obtenerRestaurante(@ApiParam(value = "ID del restaurante a recuperar", required = true) @PathParam("id") String id) {
        Restaurante restaurante = servicioRestaurante.recuperarRestaurante(id);

        if (restaurante == null) {
        	throw new RestauranteNotFoundException("Restaurante no encontrado");
        }

        return Response.ok(restaurante).build();
    }
    

    @POST
    @ApiOperation(value = "Añade un nuevo restaurante", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Restaurante creado correctamente"),
        @ApiResponse(code = 400, message = "Solicitud incorrecta")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -X POST -H "Content-Type: application/json" -d '{"nombre": "Goiko", "latitud": 40.42039145624014, "longitud": -3.6996503622016954}' http://localhost:8080/api/restaurantes
    public Response crearRestaurante(SolicitudRestaurante nuevoRestaurante) {
    	String nombre = nuevoRestaurante.getNombre();
        double latitud = nuevoRestaurante.getLatitud();
        double longitud = nuevoRestaurante.getLongitud();
    	
        String id = servicioRestaurante.altaRestaurante(nombre, latitud, longitud);
   
        if(id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Solicitud incorrecta").build();
        }
        else {
            return Response.status(Response.Status.CREATED).entity(id).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Actualiza un restaurante por ID", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restaurante actualizado correctamente"),
        @ApiResponse(code = 400, message = "Solicitud incorrecta"),
        @ApiResponse(code = 404, message = "Restaurante no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRestaurante(
        @ApiParam(value = "ID del restaurante a actualizar", required = true) @PathParam("id") String id,
        SolicitudRestaurante actualizacionRestaurante) {

        String nombre = actualizacionRestaurante.getNombre();
        double latitud = actualizacionRestaurante.getLatitud();
        double longitud = actualizacionRestaurante.getLongitud();

        boolean updated = servicioRestaurante.actualizarRestaurante(id, nombre, latitud, longitud);

        if (updated) {
            return Response.status(Response.Status.OK).entity("Restaurante actualizado correctamente").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }
    }


}
