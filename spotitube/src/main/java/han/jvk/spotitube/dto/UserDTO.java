package han.jvk.spotitube.dto;

public class UserDTO {
    private String user, password;

    public UserDTO(String user, String password, int id){
        this.user = user;
        this.password = password;
    }

    public UserDTO(){

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
