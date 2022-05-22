package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class LogoutCommand extends Command
{

    protected LogoutCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        return null;
    }
}
