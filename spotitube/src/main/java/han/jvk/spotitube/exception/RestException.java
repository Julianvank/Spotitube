package han.jvk.spotitube.exception;

import han.jvk.spotitube.remoteFacade.TokenRequiredResource;
import org.jboss.logging.Logger;

public class RestException extends APIException {

    private static final Logger log = Logger.getLogger(TokenRequiredResource.class.getName());

    public RestException(String s, APIException e) {
        super(s, e);
        log.warn(s, e);
    }

    public RestException(APIException e){
        super(e);
    }
}
