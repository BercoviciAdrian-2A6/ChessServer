package commands;

import dao.MatchDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public class ReplayCommand extends Command
{

    protected ReplayCommand(String trigger) {
        super(trigger);
        requiredParameters = 1;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput commandOutput = new CommandOutput();

        int matchIndex = Integer.parseInt( parameters.get(0) );

        commandOutput.setMessage("matchRep:" + MatchDAO.getMatchReplay(matchIndex));

        return commandOutput;
    }
}
