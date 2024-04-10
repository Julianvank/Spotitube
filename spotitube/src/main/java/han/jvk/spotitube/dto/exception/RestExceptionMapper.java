package han.jvk.spotitube.dto.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {

    @Override
    public Response toResponse(RestException exception) {
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
