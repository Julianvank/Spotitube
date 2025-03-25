package han.jvk.spotitube.util;

import han.jvk.spotitube.exception.ErrorResponse;
import han.jvk.spotitube.exception.ServiceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {
    @Override
    public Response toResponse(ServiceException e) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        Integer httpStatusCode = e.getHttpStatusCode();
        if (httpStatusCode != null) {
            statusCode = httpStatusCode;
        }

        return Response
                .status(statusCode)
                .entity(new ErrorResponse(e.getMessage(), statusCode))
                .build();
    }
}
