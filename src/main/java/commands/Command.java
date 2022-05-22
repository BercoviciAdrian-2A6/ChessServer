package commands;

import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public abstract class Command
{
    private UserEntity sender;
    private String trigger;

    protected Command(String trigger)
    {
        this.trigger = trigger;
    }

    public CommandOutput triggerCommand(String senderAuthenticationToken, String trigger, ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        if (!trigger.equals( this.trigger ))
            return null;

        if (senderAuthenticationToken != "NULL")
            this.sender = UserDAO.getUserByAuthenticationToken(senderAuthenticationToken);
        else
            this.sender = null;

        return runCommand(parameters, clientThread);
    }

    protected abstract CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread ) throws Exception;

    public UserEntity getSender() {
        return sender;
    }
}
