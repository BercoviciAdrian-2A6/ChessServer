package commands;

import Database.Singleton;
import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.Types;
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
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput commandOutput = new CommandOutput();

        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := login(?,?); end;");

        statement.registerOutParameter(1, Types.VARCHAR);

        statement.setString(2, parameters.get(0) );

        statement.setString(3, parameters.get(1) );

        statement.execute();

        commandOutput.setMessage(statement.getString(1));

        statement.close();

        clientThread.setLoggedInUser( UserDAO.getUserByAuthenticationToken(commandOutput.getMessage()) );

        return commandOutput;
    }
}
