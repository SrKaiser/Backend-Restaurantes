package rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;
import modelos.Valoracion;
import rest.seguridad.AvailableRoles;
import rest.seguridad.Secured;
import servicios.FactoriaServicios;
import servicios.IServicioRestaurante;



@Api
@Path("restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteControladorRest {
	
	private IServicioRestaurante servicioRestaurante = FactoriaServicios.getServicio(IServicioRestaurante.class);
	@Context
	private UriInfo uriInfo;
	@Context
	private SecurityContext securityContext;
	@Context
    private HttpServletRequest request;
    
    @GET
    @Path("/{id}")
    @Secured({AvailableRoles.GESTOR, AvailableRoles.CLIENTE})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Recupera un restaurante por ID", response = Restaurante.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante recuperado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X GET http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
    // curl -i -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0M2U5YTE0ZS01YTVlLTQ1MDUtODZkNi01YTY4MmE1ZWZkYjYiLCJpc3MiOiJQYXNhcmVsYSBadXVsIiwiZXhwIjoxNjg0NTA1MjM2LCJzdWIiOiJTckthaXNlciIsInVzdWFyaW8iOiJjZXNhci5wYWdhbnZpbGxhZmFuZUBnbWFpbC5jb20iLCJyb2wiOiJHRVNUT1IifQ.pZFcc48-4oe6eVmYYq4nKuTMJ7ZpfX679EwV96a5Y54" http://localhost:8090/restaurantes/ID_DEL_RESTAURANTE
    public Response obtenerRestaurante(@ApiParam(value = "ID del restaurante a recuperar", required = true) @PathParam("id") String id) throws Exception {
        return Response.status(Response.Status.OK).entity(servicioRestaurante.recuperarRestaurante(id)).build();
    }
    

    @POST
    @Secured(AvailableRoles.GESTOR)
    @ApiOperation(value = "Añade un nuevo restaurante", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Restaurante creado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -i -X POST -H "Content-Type: application/json" -d "{\"nombre\": \"Goiko\", \"latitud\": 40.42039145624014, \"longitud\": -3.6996503622016954}" http://localhost:8080/api/restaurantes
    // curl -i -X POST -H "Authorization: Bearer <token>" "Content-Type: application/json" -d "{\"nombre\": \"Goiko\", \"latitud\": 40.42039145624014, \"longitud\": -3.6996503622016954}" http://localhost:8090/api/restaurantes
    public Response crearRestaurante(SolicitudRestaurante nuevoRestaurante) throws Exception {
    	String nombre = nuevoRestaurante.getNombre();
        double latitud = nuevoRestaurante.getLatitud();
        double longitud = nuevoRestaurante.getLongitud();
    	
        String id = servicioRestaurante.altaRestaurante(nombre, latitud, longitud);
   
        if(id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Solicitud incorrecta").build();
        }
        else {
        	// "X-Forwarded-Host" contiene la URL original de la pasarela
            String pasarelaUrl = request.getHeader("X-Forwarded-Host");

            // URL del nuevo restaurante utilizando la URL de la pasarela
            String restauranteUrl = "http://" + pasarelaUrl + "/restaurantes/" + id;

            return Response.status(Response.Status.CREATED).entity(restauranteUrl).build();
        }
    }
    
    @PUT
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/update-restaurante")
    @ApiOperation(value = "Actualiza un restaurante por ID", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante actualizado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -i -X PUT -H "Content-Type: application/json" -d '{"nombre": "NuevoNombre", "latitud": 40.123456, "longitud": -3.654321}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
    public Response updateRestaurante(
        @ApiParam(value = "ID del restaurante a actualizar", required = true) @PathParam("id") String id,
        SolicitudRestaurante actualizacionRestaurante) throws Exception {

        String nombre = actualizacionRestaurante.getNombre();
        double latitud = actualizacionRestaurante.getLatitud();
        double longitud = actualizacionRestaurante.getLongitud();

        boolean updated = servicioRestaurante.actualizarRestaurante(id, nombre, latitud, longitud);

        if (updated) {
            return Response.status(Response.Status.OK).entity(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }
    }
    
    @GET
    @Secured({AvailableRoles.GESTOR, AvailableRoles.CLIENTE})
    @Path("/{id}/sitios-turisticos")
    @ApiOperation(value = "Recupera los sitios turísticos cercanos a un restaurante", response = SitioTuristico.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Sitios turísticos recuperados correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X GET http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/sitios-turisticos
    public Response obtenerSitiosTuristicosCercanos(@ApiParam(value = "ID del restaurante para buscar sitios turísticos cercanos", required = true) @PathParam("id") String idRestaurante) throws Exception {
        
    	System.out.println(idRestaurante);
    	List<SitioTuristico> sitiosTuristicos = servicioRestaurante.obtenerSitiosTuristicosProximos(idRestaurante);
        
        if (sitiosTuristicos == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(sitiosTuristicos).build();
    }
    
    @PUT
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/sitios-turisticos")
    @ApiOperation(value = "Establece los sitios turísticos destacados de un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Sitios turísticos destacados actualizados correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -i -X PUT -H "Content-Type: application/json" -d '[{"titulo": "Titulo1", "resumen": "Resumen1", "categorias": ["Categoria1"], "enlaces": ["Enlace1"], "imagenes": ["Imagen1"]}]' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/sitios-turisticos
    public Response setSitiosTuristicosDestacados(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                                                  @ApiParam(value = "Lista de sitios turísticos destacados", required = true) List<SitioTuristico> sitiosTuristicos) throws Exception {
        boolean resultado = servicioRestaurante.establecerSitiosTuristicosDestacados(idRestaurante, sitiosTuristicos);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }

    @POST
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/platos")
    @ApiOperation(value = "Agrega un plato a un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato agregado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -i -X POST -H "Content-Type: application/json" -d '{"nombre": "Plato1", "descripcion": "Descripcion1", "precio": 10.0}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos
    public Response addPlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                             @ApiParam(value = "Plato a agregar", required = true) Plato plato) throws Exception {
        boolean resultado = servicioRestaurante.añadirPlato(idRestaurante, plato);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
    @DELETE
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/platos/{nombrePlato}")
    @ApiOperation(value = "Elimina un plato de un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato eliminado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X DELETE http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos/NOMBRE_DEL_PLATO
    public Response removePlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                                @ApiParam(value = "Nombre del plato a eliminar", required = true) @PathParam("nombrePlato") String nombrePlato) throws Exception {
        
    	boolean resultado = servicioRestaurante.borrarPlato(idRestaurante, nombrePlato);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
    @PUT
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/update-plato")
    @ApiOperation(value = "Actualiza un plato de un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato actualizado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante o plato no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -i -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre", "descripcion": "NuevaDescripcion", "precio": NuevoPrecio}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos
    public Response updatePlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                                Plato plato) throws Exception {
        boolean resultado = servicioRestaurante.actualizarPlato(idRestaurante, plato);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante o plato no encontrado").build();
        }

        return Response.ok(resultado).build();
    }

    @DELETE
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}")
    @ApiOperation(value = "Elimina un restaurante por ID", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante eliminado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X DELETE http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
    public Response borrarRestaurante(@ApiParam(value = "ID del restaurante a eliminar", required = true) @PathParam("id") String idRestaurante) throws Exception {
        boolean resultado = servicioRestaurante.borrarRestaurante(idRestaurante);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
    @GET
    @Secured({AvailableRoles.GESTOR, AvailableRoles.CLIENTE})
    @ApiOperation(value = "Lista todos los restaurantes", response = ResumenRestaurante.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Listado de restaurantes generado correctamente")
    })
    // curl -i -X GET http://localhost:8080/api/restaurantes
    // curl -i -X GET -H "Authorization: Bearer <token>" http://localhost:8090/restaurantes
    public Response listarRestaurantes() throws Exception {
        List<ResumenRestaurante> restaurantesList = servicioRestaurante.recuperarTodosRestaurantes();
        
        return Response.ok(restaurantesList).build();
    }
    
    @PUT
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/activar-opiniones")
    @ApiOperation(value = "Activa las opiniones para un restaurante por ID", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Opiniones para el restaurante activadas correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X PUT -H "Content-Type: application/json" http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/activar-opiniones
    public Response activarOpiniones(
        @ApiParam(value = "ID del restaurante para activar opiniones", required = true) @PathParam("id") String id) throws Exception {

        String idOpinion = servicioRestaurante.activarOpiniones(id);

        if (idOpinion != null) {
            return Response.status(Response.Status.OK).entity(idOpinion).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }
    }
    

	@GET
    @Secured(AvailableRoles.GESTOR)
    @Path("/{id}/valoraciones")
    @ApiOperation(value = "Recupera todas las valoraciones para un restaurante por ID", response = Valoracion.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Valoraciones recuperadas correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/api/restaurantes/646647a7b74fb24a2063466d/valoraciones
    public Response recuperarTodasValoraciones(
        @ApiParam(value = "ID del restaurante para recuperar valoraciones", required = true) @PathParam("id") String id) throws Exception {
      
           List<Valoracion> valoraciones = servicioRestaurante.recuperarTodasValoraciones(id);
            
            if (valoraciones != null) {
                return Response.status(Response.Status.OK).entity(valoraciones).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
            }
    }

}
