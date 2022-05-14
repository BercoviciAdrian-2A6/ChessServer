package dao;

import Database.Singleton;
import Entities.UserEntity;
import authentication.TokenGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO
{
    public static void createUser(String username, String password ) throws SQLException {
        UserEntity user = new UserEntity( username, password, TokenGenerator.createToken() );
        Singleton.getInstance().addUser(user);
    }


    public static UserEntity getUserByAuthenticationToken(String authenticationToken) throws Exception {
        return Singleton.getInstance().getUserByAuthenticationToken(authenticationToken);
    }

    public static UserEntity getUserByUsername(String username) throws Exception {
        return Singleton.getInstance().getUserByName(username);
    }

    public static List<UserEntity> getUsersByUsernames(List<String> usernames) throws Exception {
        return Singleton.getInstance().getUsersByUsername(usernames);
    }

    public static void emptyTable() throws SQLException {
        Singleton.getInstance().emptyTableUsers();
    }

    public static ArrayList<UserEntity> getAllUsers() throws SQLException {
        return Singleton.getInstance().getAllUsers();
    }

}
