package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class DequeueCommand extends Command
{
    protected DequeueCommand(String trigger)
    {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        return null;
    }
}
