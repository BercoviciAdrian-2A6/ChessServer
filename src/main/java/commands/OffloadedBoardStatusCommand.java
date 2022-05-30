package commands;

import tcp.ClientThread;
import tcp.Server;

import java.util.ArrayList;

public class OffloadedBoardStatusCommand extends Command
{

    protected OffloadedBoardStatusCommand(String trigger)
    {
        super(trigger);
        requiredParameters = 0;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        ClientThread targetThread = Server.getClientThreadByUser(getSender());

        clientThread = targetThread;

        BoardStatusCommand boardStatusCommand = new BoardStatusCommand("-");

        return boardStatusCommand.triggerCommand(getSender().getAuthenticationToken(), "-", parameters, clientThread);
    }
}
