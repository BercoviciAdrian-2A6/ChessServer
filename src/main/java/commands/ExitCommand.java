package commands;

import tcp.ClientThread;

import java.util.ArrayList;

public class ExitCommand extends Command
{

    protected ExitCommand(String trigger)
    {
        super(trigger);
        requiresAuthentication = false;
        requiredParameters = 0;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        System.out.println("exit command inputed");
        CommandOutput exit0 = new CommandOutput();
        exit0.setMessage("@Exit0");

        return exit0;
    }
}
