package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class IdleCommand extends Command
{
    protected IdleCommand(String trigger) {
        super(trigger);
        requiresAuthentication = false;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput commandOutput = new CommandOutput();
        commandOutput.setMessage("--idle--");
        return commandOutput;
    }
}
