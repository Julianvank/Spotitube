package han.jvk.spotitube.remoteFacade.responses;

import jakarta.ws.rs.core.Response;

public class JsonResponse <AnyResponse> {
    AnyResponse value;
    int statusCode;

    public JsonResponse(AnyResponse value){
        this.value = value;
        if(value == null){
            statusCode = 204;
        }else{
            statusCode = 200;
        }
    }

    public JsonResponse(AnyResponse value, int statusCode){
        this.value = value;
        this.statusCode = statusCode;
    }

}
