package Entities;

public class MessageEntity
{
    private UserEntity sender;
    private UserEntity receiver;
    private String content;

    public MessageEntity(UserEntity sender, UserEntity receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
