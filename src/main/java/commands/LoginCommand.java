package commands;

import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public class LoginCommand extends Command {
    public LoginCommand(String trigger) {
        super(trigger);
    }

    /**
     * @param parameters parameter0 is username, parameter1 is password
     * @return an authentication token
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        CommandOutput commandOutput = new CommandOutput();
        UserEntity user = UserDAO.getUserByUsername(parameters.get(0));

        if (user != null && parameters.get(1).equals(user.getPassword())) {
            commandOutput.setMessage(user.getAuthenticationToken());
            commandOutput.setStatus(1);
        }

        return commandOutput;
    }
}
