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
    public LoginCommand(String trigger)
    {
        super(trigger);
        requiresAuthentication = false;
        requiredParameters = 2;
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

        String token = statement.getString(1);

        if(token.equals("Nu exista acest cont!"))
            commandOutput.setMessage(token);
        else
            commandOutput.setMessage("#@Tkn%" + token);

        statement.close();

        clientThread.setLoggedInUser( UserDAO.getUserByAuthenticationToken( token ) );

        return commandOutput;
    }
}
