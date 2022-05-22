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
        if (getSender() == null)
            return null;

        CommandOutput output = new CommandOutput();

        String historyMatchesId = "";

        List<MatchEntity> history = MatchDAO.getMatchHistory( getSender().getId() );

        for (int matchIndex = 0; matchIndex < history.size(); matchIndex++)
        {
            historyMatchesId += history.get(matchIndex).getMatchId();

            System.out.println(history.get(matchIndex));

            if ( matchIndex != history.size() - 1)
                historyMatchesId += "/";
        }

        output.setMessage(historyMatchesId);

        return output;
    }
}
