package dao;

import Database.Singleton;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class InviteDAO
{
    public static void voidInvite(String senderUsername, String receiverUsername) throws SQLException
    {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := void_invite(?,?); end;");

        statement.registerOutParameter(1, Types.INTEGER);

        statement.setString(2, senderUsername);

        statement.setString(3, receiverUsername);

        statement.execute();

        statement.close();
    }
}
