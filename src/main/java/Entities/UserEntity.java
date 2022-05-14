package Entities;

public class UserEntity
{
    private String username;
    private String password;
    private String authenticationToken;

    public UserEntity(String username, String password, String authenticationToken) {
        this.username = username;
        this.password = password;
        this.authenticationToken = authenticationToken;
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

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
