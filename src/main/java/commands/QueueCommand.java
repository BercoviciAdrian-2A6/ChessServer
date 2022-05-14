package commands;

import game.GameDispatcher;
import tcp.ClientThread;

import javax.lang.model.util.ElementScanner6;
import java.util.ArrayList;

public class QueueCommand extends Command
{

    protected QueueCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (getSender() == null)
        {
            CommandOutput notLoggedIn = new CommandOutput();
            notLoggedIn.setMessage("Please login to queue for matches!");
            return notLoggedIn;
        }

        CommandOutput commandOutput = new CommandOutput();

        clientThread.setGameRoom( GameDispatcher.getInstance().enterQueue( getSender(), clientThread ) );

        if (clientThread.getGameRoom().getRoomIsFull())
            commandOutput.setMessage("GAME IS ABOUT TO BEGIN!");
        else
            commandOutput.setMessage("WAITING FOR OPPONENT..");

        return commandOutput;
    }
}
