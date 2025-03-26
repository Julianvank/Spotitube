package han.jvk.spotitube.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class APIExceptionMapper implements ExceptionMapper<APIException> {

    @Override
    public Response toResponse(APIException exception) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        Integer httpStatusCode = exception.getHttpStatusCode();
        if (httpStatusCode != null) {
            statusCode = httpStatusCode;
        }

        return Response.status(statusCode)
                .entity(new ErrorResponse(exception.getMessage(), statusCode))
                .build();
    }
}
