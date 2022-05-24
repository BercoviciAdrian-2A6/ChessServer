package commands;

import game.GameDispatcher;
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

        int dequeueStatus = GameDispatcher.getInstance().dequeue(getSender(), clientThread);

        if (dequeueStatus == 1)
        {
            CommandOutput dequeueSuccessful = new CommandOutput();

            dequeueSuccessful.setMessage("Dequeued succesfully");

            return dequeueSuccessful;
        }

        CommandOutput dequeueFailed = new CommandOutput();

        dequeueFailed.setMessage("Dequeue failed");

        return dequeueFailed;
    }
}
