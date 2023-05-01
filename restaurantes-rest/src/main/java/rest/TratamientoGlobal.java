package rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TratamientoGlobal implements ExceptionMapper<Exception>{

	@Override
	public Response toResponse(Exception exception) {
		// Aquí puedes personalizar cómo manejar las excepciones
        // Por ejemplo, puedes devolver un objeto JSON con el mensaje de error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
