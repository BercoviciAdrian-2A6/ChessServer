package dao;

import Database.Singleton;
import Entities.UserEntity;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


}
