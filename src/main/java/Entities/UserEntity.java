package Entities;

public class UserEntity
{
    private int id;
    private String username;
    private String password;
    private String authenticationToken;

    public UserEntity(int id,String username, String password, String authenticationToken)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authenticationToken = authenticationToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
