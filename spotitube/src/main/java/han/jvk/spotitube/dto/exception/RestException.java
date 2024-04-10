package han.jvk.spotitube.dto.exception;

public class RestException extends APIException {
    public RestException(String s, APIException e) {
        super(s, e);
    }
}
