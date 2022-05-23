package commands;

import dao.UserDAO;
import tcp.ClientThread;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogoutCommand extends Command
{

    protected LogoutCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (getSender() == null)
            return null;

        CommandOutput commandOutput = new CommandOutput();

        int logoutStatus = UserDAO.logoutUser(getSender());

        if (logoutStatus == 1)
            commandOutput.setMessage("Logout successful!");
        else
            commandOutput.setMessage("Logout failed..");

        return commandOutput;
    }
}
