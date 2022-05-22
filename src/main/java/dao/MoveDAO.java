package dao;

import Database.Singleton;
import Entities.UserEntity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class MoveDAO
{
    public static void addMove(int matchIndex, UserEntity user, String moveString, float duration) throws SQLException
    {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin add_move(?,?,?,?); end;");

        statement.setInt(1, matchIndex);

        statement.setInt(2, user.getId());

        statement.setString(3, moveString);

        statement.setFloat(4, duration);

        statement.execute();

        statement.close();
    }
}
