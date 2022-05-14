package commands;

import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public class RegisterCommand extends Command
{
    public RegisterCommand(String trigger)
    {
        super(trigger);
    }

    /**
     * @param parameters parameter0 is username, parameter1 is password
     * @return informs the client if a user has been created
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        CommandOutput commandOutput = new CommandOutput();

        String username = parameters.get(0);
        String password = parameters.get(1);

        UserEntity userExists = UserDAO.getUserByUsername( username );

        if (userExists != null)
        {
            commandOutput.setMessage("Username already in use!");
            commandOutput.setStatus(-1);
            return commandOutput;
        }

        UserDAO.createUser(username, password);

        commandOutput.setMessage("Username: " + username + " was created!");
        commandOutput.setStatus(1);
        return commandOutput;
    }
}
