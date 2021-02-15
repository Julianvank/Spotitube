package DTO;

public class AuthenticatedUserDTO {
    private String user;
    private String token;

    public AuthenticatedUserDTO(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public AuthenticatedUserDTO() {
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}