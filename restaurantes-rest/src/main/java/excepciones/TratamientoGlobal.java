package excepciones;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TratamientoGlobal implements ExceptionMapper<Exception>{

	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof RestauranteNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
