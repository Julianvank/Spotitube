package han.jvk.spotitube.dto.exception;

public class ServiceException extends APIException {

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
