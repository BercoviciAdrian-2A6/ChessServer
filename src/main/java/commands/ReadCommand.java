package commands;

import Entities.MessageEntity;
import dao.MessageDAO;
import tcp.ClientThread;

import java.util.ArrayList;
import java.util.List;

public class ReadCommand extends Command
{
    public ReadCommand(String trigger)
    {
        super(trigger);
    }

    /**
     * @param parameters are irrelevant
     * @return
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        CommandOutput commandOutput = new CommandOutput();

        List<MessageEntity> messages = MessageDAO.getMessagesTo( getSender() );

        if (messages == null || messages.size() == 0)
        {
            commandOutput.setMessage( "No messages are available" );
            commandOutput.setStatus(1);
        }
        else
        {
            commandOutput.setMessage("");
            for (MessageEntity message: messages)
            {
                String currentMessage = commandOutput.getMessage();

                currentMessage += "*** " + message.getSender().getUsername() + " -> " + message.getContent();

                commandOutput.setMessage(currentMessage);
            }

        }

        return commandOutput;
    }
}
