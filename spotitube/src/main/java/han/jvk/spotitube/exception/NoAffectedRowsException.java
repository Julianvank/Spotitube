package han.jvk.spotitube.exception;

public class NoAffectedRowsException extends APIException {
    public NoAffectedRowsException(String message, Integer httpStatusCode) {
        super(message, httpStatusCode);
    }


}
