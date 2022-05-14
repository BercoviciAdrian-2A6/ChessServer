package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class ForfeitCommand extends Command
{

    protected ForfeitCommand(String trigger)
    {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (getSender() == null)
            return null;

        if (clientThread.getGameRoom() == null)
        {
            CommandOutput beInGame = new CommandOutput();
            beInGame.setMessage("You must be in game to forfeit!");
            return beInGame;
        }

        CommandOutput forfeit = new CommandOutput();

        forfeit.setMessage("YOU HAVE FORFEITED THE GAME");

        clientThread.getGameRoom().setForfeit( getSender() );

        return forfeit;
    }
}
