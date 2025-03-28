package han.jvk.spotitube.remoteFacade.responses;

public class LogInUserResponse {
    private String token;
    private String user;

    public LogInUserResponse(String token, String user){
        this.token = token;
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public String getUser() {return user;}
}
