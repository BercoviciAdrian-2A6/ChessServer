package dao;

import Database.Singleton;
import Entities.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsDAO
{
    public static void createFriendship(UserEntity firstUser, UserEntity secondUser) throws SQLException
    {
        //check if users are already friends

        if (Singleton.getInstance().friendshipExists(firstUser, secondUser))
            return;

        Singleton.getInstance().addFriendship(firstUser,secondUser);
    }

    public static void emptyTable() throws SQLException {
        Singleton.getInstance().emptyTableFriendships();
    }


}
