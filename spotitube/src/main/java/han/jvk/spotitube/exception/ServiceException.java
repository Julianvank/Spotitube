package han.jvk.spotitube.exception;

import han.jvk.spotitube.remoteFacade.TokenRequiredResource;
import org.jboss.logging.Logger;

public class ServiceException extends APIException {

    public ServiceException(APIException cause, int httpStatusCode) {
        super(cause, httpStatusCode);
    }

    public ServiceException(String message, int httpStatusCode) {
        super(message, httpStatusCode);
    }

    public ServiceException(String message, APIException cause) {
        super(message, cause);
    }

    public ServiceException(APIException cause) {
        super(cause);
    }
}
