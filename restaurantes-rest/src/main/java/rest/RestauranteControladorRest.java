package rest;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import modelos.Restaurante;
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

    @POST
    @ApiOperation(value = "Añade un nuevo restaurante", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Restaurante creado correctamente"),
        @ApiResponse(code = 400, message = "Solicitud incorrecta")
    })
    public Response crearRestaurante(String nombre, double latitud, double longitud) {
        String id = servicioRestaurante.altaRestaurante(nombre, latitud, longitud);
        
        if(id == null) {
        	return Response.status(Response.Status.BAD_REQUEST).entity("Solicitud incorrecta").build();
        }
        else {
        	return Response.status(Response.Status.CREATED).entity(id).build();
        }
        
    }
    
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Recupera un restaurante por ID", response = Restaurante.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restaurante recuperado correctamente"),
        @ApiResponse(code = 404, message = "Restaurante no encontrado")
    })
    // Ejemplo de uso con curl: 
    // curl -X GET http://localhost:8080/NombreDeTuProyecto/api/restaurantes/ID_DEL_RESTAURANTE
    public Response obtenerRestaurante(@ApiParam(value = "ID del restaurante a recuperar", required = true) @PathParam("id") String id) {
        Restaurante restaurante = servicioRestaurante.recuperarRestaurante(id);

        if (restaurante == null) {
        	return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(restaurante).build();
    }

}
