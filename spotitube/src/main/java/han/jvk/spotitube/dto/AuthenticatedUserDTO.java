package han.jvk.spotitube.dto;

public class AuthenticatedUserDTO {
    private String username;
    private String token;

    private int id;

    public AuthenticatedUserDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public AuthenticatedUserDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
