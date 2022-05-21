package Database;

import Entities.MessageEntity;
import Entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    static Connection connection;

    public Database() throws SQLException {
        //String address = "jdbc:postgresql://localhost:5432/JavaLab10";
        //connection = DriverManager.getConnection(address, "postgres", "STUDENT");
        String address = "jdbc:oracle:thin:@//localhost:1521/XE";
        connection = DriverManager.getConnection(address, "STUDENT", "STUDENT");
    }


    public void emptyTableUsers() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("truncate table users cascade");
    }

    public void emptyTableMessages() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("truncate table messages");
    }

    public void emptyTableFriendships() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("truncate table friendships");
    }

    public void addUser(UserEntity user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("insert into useri (id_user, username, parola, token) values (" + getIdForUser() + ", \'" + user.getUsername() + "\', \'" + user.getPassword()
                + "\', \'" + user.getAuthenticationToken() + "\')");
    }

    public int getIdForUser() throws SQLException {
        Statement statement = connection.createStatement();
        int maxId = 0;
        ResultSet resultSet = statement.executeQuery("select max(id_user) as \"max\" from useri");
        if (resultSet.next()) {
            maxId = resultSet.getInt("max");
        }
        return maxId + 1;
    }


    public void addMessage(MessageEntity message) throws Exception {
        Statement statement = connection.createStatement();
        int senderId = findIdOfUser(message.getSender());
        int receiverId = findIdOfUser(message.getReceiver());

        if (senderId != -1 && receiverId != -1)
            statement.execute("insert into messages (id, sender_id, receiver_id, content) values (" + getIdForMessage() + ", " + senderId + ", " + receiverId + ", \'" + message.getContent() + "\')");
//        else
//            return null;
    }

    public void addFriendship(UserEntity firstUser, UserEntity secondUser) throws SQLException {
        int idFirstUser = findIdOfUser(firstUser);
        int idSecondUser = findIdOfUser(secondUser);
        Statement statement = connection.createStatement();
        statement.execute("insert into friendships (id_first_user, id_second_user) values (" + idFirstUser + ", " + idSecondUser + ")");
    }

    public boolean friendshipExists(UserEntity firstUser, UserEntity secondUser) throws SQLException
    {
        Statement statement = connection.createStatement();
        int firstUserId = findIdOfUser(firstUser);
        int secondUserId = findIdOfUser(secondUser);

        ResultSet resultSet_1 = statement.executeQuery(
                "select * from friendships where id_first_user = " + firstUserId + " and id_second_user = " + secondUserId);

        ResultSet resultSet_2 = statement.executeQuery(
                "select * from friendships where id_first_user = " + secondUserId + " and id_second_user = " + firstUserId);//check for the reverse

        if (resultSet_1.next() || resultSet_1.next())
        {
            System.out.println("USERS ARE ALREADY FRIENDS");
            return true;
        }
        return false;
    }

    public int findIdOfUser(UserEntity user) throws SQLException {
        int id = -1;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id from users where username = \'" + user.getUsername() + "\'");
        if (resultSet.next())
            id = resultSet.getInt("id");
        return id;
    }

    public int getIdForMessage() throws SQLException {
        Statement statement = connection.createStatement();
        int maxId = 0;
        ResultSet resultSet = statement.executeQuery("select max(id) as \"max\" from messages");
        if (resultSet.next()) {
            maxId = resultSet.getInt("max");
        }
        return maxId + 1;
    }

    public List<MessageEntity> getMessagesTo(UserEntity user) throws Exception {
        int idUser = findIdOfUser(user);
        if(idUser!=-1){
            List<MessageEntity> messages = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from messages where receiver_id = " + idUser);

            while (resultSet.next()){
                UserEntity sender = getUserById(resultSet.getInt("sender_id"));
                messages.add(new MessageEntity(sender,user,resultSet.getString("content")));
            }

            return messages;
        }
        else
            return null;
    }

    public UserEntity getUserById(int id) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where id = " + id);
        if(resultSet.next()){
            return new UserEntity(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("authentication_token"));
        }
        return null;
    }

    public UserEntity getUserByName(String name) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where username = \'" + name + "\'");
        if(resultSet.next()){
            return new UserEntity(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("authentication_token"));
        }
        return null;
    }

    public UserEntity getUserByAuthenticationToken(String authenticationToken) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where authentication_token = \'" + authenticationToken + "\'");
        if(resultSet.next()){
            return new UserEntity(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("authentication_token"));
        }
        return null;
    }

    public List<UserEntity> getUsersByUsername(List<String> usernames) throws Exception {
        List<UserEntity> users = new ArrayList<>();
        for(String username : usernames){
            UserEntity temp = getUserByName(username);
            if(temp!=null)
            users.add(temp);
        }
        return users;
    }

    public ArrayList<UserEntity> getAllUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        ArrayList<UserEntity> allUsers = new ArrayList<>();

        while (resultSet.next())
        {
            allUsers.add( new UserEntity(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("authentication_token")) );
        }

        return allUsers;
    }


}
