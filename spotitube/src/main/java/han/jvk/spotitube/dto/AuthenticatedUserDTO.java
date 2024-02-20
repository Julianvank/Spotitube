package han.jvk.spotitube.dto;

public class AuthenticatedUserDTO {
    private String username;
    private String token;

    public AuthenticatedUserDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
