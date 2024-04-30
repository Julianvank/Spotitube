package han.jvk.spotitube.exception;


public abstract class APIException extends RuntimeException{

    private Integer httpStatusCode = 500;

    public APIException(String message){
        super(message);
    }
    public APIException(String message, Integer httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public APIException(String message, Throwable cause, Integer httpStatusCode) {
        super(message.concat(" Cause: " + cause.getMessage()), cause);
        this.httpStatusCode = httpStatusCode;
    }

    public APIException(Throwable cause, Integer httpStatusCode) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
    }

    public APIException(String message, Throwable cause) {
        super(message.concat(" Cause: " + cause.getMessage()), cause);

        elabHttpStatusCode(cause);
    }

    public APIException(Throwable cause) {
        super(cause);
        elabHttpStatusCode(cause);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    private void elabHttpStatusCode(Throwable cause) {
        try {
            this.httpStatusCode = ((APIException) cause).httpStatusCode;
        } catch (ClassCastException e) {
            e.printStackTrace();
            this.httpStatusCode = 500;
        }
    }
}
