package han.jvk.spotitube.exception;

import han.jvk.spotitube.remoteFacade.TokenRequiredResource;
import org.jboss.logging.Logger;

import java.sql.SQLException;

public class DALException extends APIException {

    private static final Logger log = Logger.getLogger(TokenRequiredResource.class.getName());

    public DALException(String message){
        super(message);
    }

    public DALException(String message, Throwable cause) {
        super(message, cause);
        log.fatal(message, cause);
    }

    public DALException(Throwable cause) {
        super(cause);
        log.fatal(cause);
    }

    public DALException(String s, SQLException e, int httpInternalError) {
        super(s, e, httpInternalError);
    }
}
