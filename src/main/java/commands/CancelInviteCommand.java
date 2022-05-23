package commands;

import dao.InviteDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public class CancelInviteCommand extends Command
{

    protected CancelInviteCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput commandOutput = new CommandOutput();

        InviteDAO.voidInvite(getSender().getUsername(), parameters.get(0));

        commandOutput.setMessage("Invite canceled!");

        return commandOutput;
    }
}
