package commands;

import Database.Singleton;
import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

public class RegisterCommand extends Command
{
    public RegisterCommand(String trigger)
    {
        super(trigger);
        requiresAuthentication = false;
        requiredParameters = 2;
    }

    /**
     * @param parameters parameter0 is username, parameter1 is password
     * @return informs the client if a user has been created
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        CommandOutput commandOutput = new CommandOutput();

        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := register(?,?); end;");

        statement.registerOutParameter(1, Types.INTEGER);

        statement.setString(2, parameters.get(0) );

        statement.setString(3, parameters.get(1) );

        statement.execute();

        int createResult = statement.getInt(1);

        statement.close();

        if (createResult == 1)
            commandOutput.setMessage("USER REGISTERED SUCCESSFULLY!");
        else
            commandOutput.setMessage("REGISTRATION FAILED! USERNAME ALREADY TAKEN!");

        return commandOutput;
    }
}
