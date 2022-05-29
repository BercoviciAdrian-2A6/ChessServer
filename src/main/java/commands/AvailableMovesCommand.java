package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class AvailableMovesCommand extends Command
{

    protected AvailableMovesCommand(String trigger)
    {
        super(trigger);
        requiredParameters = 1;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (clientThread.getGameRoom() == null)
            return null;

        CommandOutput availableMoves = new CommandOutput();

        availableMoves.setMessage( clientThread.getGameRoom().getChessboard().getAvailableMoves(parameters.get(0)) );

        return availableMoves;
    }
}
