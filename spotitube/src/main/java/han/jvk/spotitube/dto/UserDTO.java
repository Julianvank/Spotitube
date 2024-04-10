package han.jvk.spotitube.dto;

public class UserDTO {
    private String username, password;

    public UserDTO(String username, String password, int id){
        this.username = username;
        this.password = password;
    }

    public UserDTO(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
