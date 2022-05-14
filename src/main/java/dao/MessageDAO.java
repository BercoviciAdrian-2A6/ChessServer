package dao;

import Database.Singleton;
import Entities.MessageEntity;
import Entities.UserEntity;

import java.sql.SQLException;
import java.util.List;

public class MessageDAO
{
    public static void createMessage(UserEntity sender, UserEntity receiver, String content) throws Exception {
        MessageEntity message = new MessageEntity(sender, receiver, content);
        Singleton.getInstance().addMessage(message);
    }

    public static List<MessageEntity> getMessagesTo(UserEntity user) throws Exception {
        return Singleton.getInstance().getMessagesTo(user);
    }

    public static void emptyTable() throws SQLException {
        Singleton.getInstance().emptyTableMessages();
    }
}
