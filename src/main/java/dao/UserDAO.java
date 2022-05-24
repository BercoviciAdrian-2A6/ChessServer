package dao;

import Database.Singleton;
import Entities.UserEntity;


import java.sql.*;

public class UserDAO
{

    public static void empty() throws SQLException {
        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        statement.execute("truncate table useri");
    }

    public static UserEntity getUserByAuthenticationToken(String authenticationToken) throws Exception
    {
        UserEntity result = null;

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from useri where token = \'" + authenticationToken + "\'");
        if(resultSet.next()){
            result =  new UserEntity( resultSet.getInt("id_user") ,resultSet.getString("username"),resultSet.getString("parola"),resultSet.getString("token"));
        }

        statement.close();
        return result;
    }

    public static UserEntity getUserById(int id) throws Exception
    {
        UserEntity result = null;

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from useri where id_user = " + id);
        if(resultSet.next()){
            result =  new UserEntity( resultSet.getInt("id_user") ,resultSet.getString("username"),resultSet.getString("parola"),resultSet.getString("token"));
        }

        statement.close();
        return result;
    }

    public static UserEntity getUserByUsername(String username) throws Exception
    {
        UserEntity result = null;

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from useri where username = \'" + username + "\'");
        if(resultSet.next()){
            result =  new UserEntity( resultSet.getInt("id_user") ,resultSet.getString("username"),resultSet.getString("parola"),resultSet.getString("token"));
        }

        statement.close();
        return result;
    }

    public static int logoutUser(UserEntity user) throws SQLException
    {
        if (user == null)
            return -1;

        System.out.println("trying to log out user:" + user.getUsername());

        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := logout(?); end;");

        statement.registerOutParameter(1, Types.INTEGER);

        statement.setString(2, user.getAuthenticationToken());

        statement.execute();

        int logoutStatus = statement.getInt(1);

        statement.close();

        if (logoutStatus == -1)
        {
            System.out.println("Invalid user logout..?!");
            return logoutStatus;
        }

        System.out.println(user.getUsername() + " has logged out!");

        return 1;
    }

    public static void logoutAll() throws SQLException
    {
        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        statement.executeQuery("UPDATE useri SET token = null");
        statement.close();
    }
}
