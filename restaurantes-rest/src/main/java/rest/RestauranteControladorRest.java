package rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import modelos.Plato;
import modelos.Restaurante;
import modelos.ResumenRestaurante;
import modelos.SitioTuristico;
import modelos.SolicitudRestaurante;
import modelos.Valoracion;
import retrofit2.http.DELETE;
import servicios.FactoriaServicios;
import servicios.IServicioRestaurante;
import servicios.ServicioOpinionesRetrofit;

@Api
@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteControladorRest {
	
	private IServicioRestaurante servicioRestaurante = FactoriaServicios.getServicio(IServicioRestaurante.class);
	private ServicioOpinionesRetrofit servicioOpiniones = new ServicioOpinionesRetrofit();
    
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Recupera un restaurante por ID", response = Restaurante.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante recuperado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
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
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Restaurante creado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta")
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
    
//    @PUT
//    @Path("/{id}/update")
//    @ApiOperation(value = "Actualiza un restaurante por ID", response = String.class)
//    @ApiResponses(value = {
//        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante actualizado correctamente"),
//        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Solicitud incorrecta"),
//        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
//    })
//    @Consumes(MediaType.APPLICATION_JSON)
//    // curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "NuevoNombre", "latitud": 40.123456, "longitud": -3.654321}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
//    public Response updateRestaurante(
//        @ApiParam(value = "ID del restaurante a actualizar", required = true) @PathParam("id") String id,
//        SolicitudRestaurante actualizacionRestaurante) {
//
//        String nombre = actualizacionRestaurante.getNombre();
//        double latitud = actualizacionRestaurante.getLatitud();
//        double longitud = actualizacionRestaurante.getLongitud();
//
//        boolean updated = servicioRestaurante.actualizarRestaurante(id, nombre, latitud, longitud);
//
//        if (updated) {
//            return Response.status(Response.Status.OK).entity("Restaurante actualizado correctamente").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
//        }
//    }
    
    @GET
    @Path("/{id}/sitios-turisticos")
    @ApiOperation(value = "Recupera los sitios turísticos cercanos a un restaurante", response = SitioTuristico.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Sitios turísticos recuperados correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -X GET http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/sitios-turisticos
    public Response obtenerSitiosTuristicosCercanos(@ApiParam(value = "ID del restaurante para buscar sitios turísticos cercanos", required = true) @PathParam("id") String idRestaurante) {
        
    	System.out.println(idRestaurante);
    	List<SitioTuristico> sitiosTuristicos = servicioRestaurante.obtenerSitiosTuristicosProximos(idRestaurante);
        
        if (sitiosTuristicos == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(sitiosTuristicos).build();
    }
    
//    @PUT
//    @Path("/{id}/sitios-turisticos-destacados")
//    @ApiOperation(value = "Establece los sitios turísticos destacados de un restaurante", response = Boolean.class)
//    @ApiResponses(value = {
//        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Sitios turísticos destacados actualizados correctamente"),
//        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
//    })
//    @Consumes(MediaType.APPLICATION_JSON)
//    // curl -X PUT -H "Content-Type: application/json" -d '[{"titulo": "Titulo1", "resumen": "Resumen1", "categorias": ["Categoria1"], "enlaces": ["Enlace1"], "imagenes": ["Imagen1"]}]' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/sitios-turisticos-destacados
//    public Response setSitiosTuristicosDestacados(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
//                                                  @ApiParam(value = "Lista de sitios turísticos destacados", required = true) List<SitioTuristico> sitiosTuristicos) {
//        boolean resultado = servicioRestaurante.establecerSitiosTuristicosDestacados(idRestaurante, sitiosTuristicos);
//
//        if (!resultado) {
//            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
//        }
//
//        return Response.ok(resultado).build();
//    }

    @POST
    @Path("/{id}/platos")
    @ApiOperation(value = "Agrega un plato a un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato agregado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    // curl -X POST -H "Content-Type: application/json" -d '{"nombre": "Plato1", "descripcion": "Descripcion1", "precio": 10.0}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos
    public Response addPlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                             @ApiParam(value = "Plato a agregar", required = true) Plato plato) {
        boolean resultado = servicioRestaurante.añadirPlato(idRestaurante, plato);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
    @DELETE
    @Path("/{id}/platos/{nombrePlato}")
    @ApiOperation(value = "Elimina un plato de un restaurante", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato eliminado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -X DELETE http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos/NOMBRE_DEL_PLATO
    public Response removePlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
                                @ApiParam(value = "Nombre del plato a eliminar", required = true) @PathParam("nombrePlato") String nombrePlato) {
        
    	boolean resultado = servicioRestaurante.borrarPlato(idRestaurante, nombrePlato);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
//    @PUT
//    @Path("/{id}/update-plato")
//    @ApiOperation(value = "Actualiza un plato de un restaurante", response = Boolean.class)
//    @ApiResponses(value = {
//        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Plato actualizado correctamente"),
//        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante o plato no encontrado")
//    })
//    @Consumes(MediaType.APPLICATION_JSON)
//    // curl -X PUT -H "Content-Type: application/json" -d '{"nombre": "nombre", "descripcion": "NuevaDescripcion", "precio": NuevoPrecio}' http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE/platos
//    public Response updatePlato(@ApiParam(value = "ID del restaurante", required = true) @PathParam("id") String idRestaurante,
//                                Plato plato) {
//        boolean resultado = servicioRestaurante.actualizarPlato(idRestaurante, plato);
//
//        if (!resultado) {
//            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante o plato no encontrado").build();
//        }
//
//        return Response.ok(resultado).build();
//    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Elimina un restaurante por ID", response = Boolean.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Restaurante eliminado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    // curl -X DELETE http://localhost:8080/api/restaurantes/ID_DEL_RESTAURANTE
    public Response borrarRestaurante(@ApiParam(value = "ID del restaurante a eliminar", required = true) @PathParam("id") String idRestaurante) {
        boolean resultado = servicioRestaurante.borrarRestaurante(idRestaurante);

        if (!resultado) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurante no encontrado").build();
        }

        return Response.ok(resultado).build();
    }
    
    @GET
    @ApiOperation(value = "Lista todos los restaurantes", response = ResumenRestaurante.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Listado de restaurantes generado correctamente")
    })
    // curl -X GET http://localhost:8080/api/restaurantes
    public Response listarRestaurantes() {
        List<ResumenRestaurante> restaurantesList = servicioRestaurante.recuperarTodosRestaurantes();
        return Response.ok(restaurantesList).build();
    }
    
    @POST
    @Path("/registrarOpinion")
    @ApiOperation(value = "Registra un nuevo recurso en el servicio de opiniones", response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Recurso registrado correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Error al registrar el recurso")
    })
    public Response registrarRecurso(String nombreRecurso) {
        String opinionId = servicioOpiniones.registrarRecurso(nombreRecurso);
        if (opinionId != null) {
            return Response.ok(opinionId).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{idRestaurante}/valoraciones")
    @ApiOperation(value = "Obtiene las valoraciones de un restaurante", response = Valoracion.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Valoraciones obtenidas correctamente"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado")
    })
    public Response obtenerValoraciones(@ApiParam(value = "ID del restaurante", required = true) @PathParam("idRestaurante") String idRestaurante) {
        Restaurante restaurante = servicioRestaurante.recuperarRestaurante(idRestaurante);
        if (restaurante != null) {
            String idOpinion = restaurante.getOpinionId();
            List<Valoracion> valoraciones = servicioOpiniones.obtenerValoraciones(idOpinion);
            if (valoraciones != null) {
                return Response.ok(valoraciones).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
