package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class BoardStatusCommand extends Command
{

    protected BoardStatusCommand(String trigger) {
        super(trigger);

        requiresAuthentication = true;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (clientThread.getGameRoom() == null)
            return null;

        CommandOutput status = new CommandOutput();

        status.setMessage(clientThread.getGameRoom().getBoardStatus());

        return status;
    }
}
