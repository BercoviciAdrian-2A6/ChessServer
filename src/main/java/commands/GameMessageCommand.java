package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class GameMessageCommand extends Command
{

    protected GameMessageCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (clientThread.getGameRoom() == null || getSender() == null)
            return null;

        String messageContent = "";

        for (int i = 0; i < parameters.size(); i++)
        {
            messageContent += parameters.get(i);
            messageContent += " ";
        }

        clientThread.getGameRoom().addMessage( getSender().getUsername(), messageContent );

        CommandOutput messageSent = new CommandOutput();

        messageSent.setMessage("MESSAGE SENT!");

        return messageSent;
    }
}
