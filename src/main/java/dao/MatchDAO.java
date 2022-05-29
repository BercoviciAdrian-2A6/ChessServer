package dao;

import Database.Singleton;
import Entities.MatchEntity;
import Entities.UserEntity;

import javax.print.DocFlavor;
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

    public static List<MatchEntity> getMatchHistory(int targetPlayerId) throws Exception
    {
        ArrayList<MatchEntity> history = new ArrayList<>();

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from games where white_player_id = " + targetPlayerId + " or black_player_id = " + targetPlayerId + " order by start_timestamp");
        while (resultSet.next())
        {
            int matchId = resultSet.getInt("id_match");
            UserEntity white = UserDAO.getUserById(resultSet.getInt("white_player_id"));
            UserEntity black = UserDAO.getUserById(resultSet.getInt("black_player_id"));
            UserEntity winner = UserDAO.getUserById(resultSet.getInt("winnerflag"));
            String startTimestamp = resultSet.getString("start_timestamp");
            history.add( new MatchEntity( matchId, white, black, winner, startTimestamp ) );
        }

        statement.close();

        return history;
    }

    public static void assureGameIntegrity() throws SQLException
    {
        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin assure_games_integrity; end;");

        statement.execute();

        statement.close();
    }

    public static String getAllMatches(UserEntity user) throws Exception
    {
       int playerId = user.getId();

        Statement statement = Singleton.getDataBase().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from games where (white_player_id = " + playerId +
                "or black_player_id = " + playerId + ") and winnerflag is not null order by start_timestamp");

        String concat = "";

        while (resultSet.next())
        {
            concat += "#";
            int idMatch = resultSet.getInt("id_match");
            int whiteId = resultSet.getInt("white_player_id");
            UserEntity whitePlayer = UserDAO.getUserById(whiteId);
            int blackId = resultSet.getInt("black_player_id");
            UserEntity blackPlayer = UserDAO.getUserById(blackId);
            int winnerFlag = resultSet.getInt("winnerflag");
            String startTimestamp = resultSet.getString("start_timestamp");
            int duration = resultSet.getInt("duration_seconds");
            concat += "id:" + idMatch + "/";
            concat += "p1:" + whitePlayer.getUsername() + "/";
            concat += "p2:" + blackPlayer.getUsername() + "/";
            concat += "st:" + startTimestamp + "/";
            concat += "d:" + duration + "/";
            if (winnerFlag == -1)
                concat += "gs:draw";
            else
            {
                UserEntity winner = UserDAO.getUserById(winnerFlag);
                concat += "gs:" + winner.getUsername();
            }
        }

        statement.close();
        return concat;
    }
}
