package commands;

import Entities.MatchEntity;
import dao.MatchDAO;
import tcp.ClientThread;

import java.util.ArrayList;
import java.util.List;

public class MatchHistoryCommand extends Command
{

    protected MatchHistoryCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput history = new CommandOutput();

        history.setMessage( MatchDAO.getAllMatches(getSender()) );

        return history;
    }
}
