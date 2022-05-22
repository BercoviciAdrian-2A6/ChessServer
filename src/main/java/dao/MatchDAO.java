package dao;

import Database.Singleton;
import Entities.MatchEntity;
import Entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO
{
    public static int addMatch(UserEntity userA, UserEntity userB) throws SQLException
    {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := create_game(?,?); end;");

        statement.registerOutParameter(1, Types.INTEGER);

        statement.setInt(2, userA.getId());

        statement.setInt(3, userB.getId());

        statement.execute();

        int matchIndex = statement.getInt(1);

        statement.close();

        return matchIndex;
    }

    public static void endMatch(int matchId, int winnerFlag, int duration) throws SQLException {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin end_game(?,?,?); end;");

        statement.setInt(1, matchId);

        statement.setInt(2, winnerFlag);

        statement.setInt(3, duration);

        statement.execute();

        statement.close();
    }

    public static String getMatchReplay(int matchIndex) throws SQLException
    {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := reconstruct_game(?); end;");

        statement.registerOutParameter(1, Types.VARCHAR);

        statement.setInt(2, matchIndex);

        statement.execute();

        String matchHistory = statement.getString(1);

        statement.close();

        return matchHistory;
    }

    public List<MatchEntity> getMatchHistory(int targetPlayerId) throws SQLException
    {
        ArrayList<MatchEntity> history = new ArrayList<>();

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from games where white_player_id = " + targetPlayerId + " or black_player_id = " + targetPlayerId + " order by start_timestamp");
        if(resultSet.next()){
            result =  new UserEntity( resultSet.getInt("id_user") ,resultSet.getString("username"),resultSet.getString("parola"),resultSet.getString("token"));
        }

        statement.close();
    }
}