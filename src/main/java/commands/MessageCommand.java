package commands;

import Entities.UserEntity;
import dao.MessageDAO;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;
import java.util.List;

public class MessageCommand extends Command
{
    public MessageCommand(String trigger)
    {
        super(trigger);
    }

    /**
     * @param parameters each parameter is the username of a user that will receive the message, the last parameter is the message content
     * @return the users that received the message
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        CommandOutput commandOutput = new CommandOutput();

        if(getSender() == null){
            commandOutput.setMessage("You are not logged in");
            return commandOutput;
        }

        String messageContent = parameters.get( parameters.size() - 1 );

        parameters.remove( parameters.size() - 1 );

        List<UserEntity> validMessagedUsers = UserDAO.getUsersByUsernames(parameters);

        String output = "(valid) Users messaged: ";

        for (UserEntity receiver: validMessagedUsers)
        {
            MessageDAO.createMessage(getSender(), receiver, messageContent);
            output += "," + receiver.getUsername() + " ";
        }

        commandOutput.setMessage(output);

        return commandOutput;
    }
}
