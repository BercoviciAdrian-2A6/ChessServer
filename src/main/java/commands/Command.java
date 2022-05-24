package commands;

import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public abstract class Command
{
    private UserEntity sender;
    private String trigger;
    protected boolean requiresAuthentication = true;
    protected int requiredParameters = 0;

    protected Command(String trigger)
    {
        this.trigger = trigger;
    }

    public CommandOutput triggerCommand(String senderAuthenticationToken, String trigger, ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        if (!trigger.equalsIgnoreCase( this.trigger ))
            return null;

        if (senderAuthenticationToken != "NULL")
            this.sender = UserDAO.getUserByAuthenticationToken(senderAuthenticationToken);
        else
            this.sender = null;

        if (sender == null && requiresAuthentication)
        {
            CommandOutput loginpls = new CommandOutput();
            loginpls.setMessage("LOG IN TO USE THIS COMMAND!");
            return loginpls;
        }

        if (parameters.size() < requiredParameters)
        {
            CommandOutput insufficientParameters = new CommandOutput();
            insufficientParameters.setMessage("INSUFFICIENT PARAMETERS! THIS COMMAND REQUIRES " + requiredParameters + " parameters");
            return insufficientParameters;
        }

        return runCommand(parameters, clientThread);
    }

    protected abstract CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread ) throws Exception;

    public UserEntity getSender() {
        return sender;
    }
}
