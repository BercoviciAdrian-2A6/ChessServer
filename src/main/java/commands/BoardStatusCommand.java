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
        if (clientThread.getGameRoom() == null || !clientThread.getGameRoom().getRoomIsFull())
        {
            CommandOutput notInGame = new CommandOutput();
            notInGame.setMessage("!notingame!");
            return notInGame;
        }

        CommandOutput status = new CommandOutput();

        status.setMessage(clientThread.getGameRoom().getBoardStatus());

        return status;
    }
}
