package han.jvk.spotitube.exception;

public class UtilException extends APIException {


    public UtilException(String message, Integer httpStatusCode) {
        super(message, httpStatusCode);
    }

    public UtilException(String message, Throwable cause, Integer httpStatusCode) {
        super(message, cause, httpStatusCode);
    }
}
