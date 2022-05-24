package commands;

import Database.Singleton;
import Entities.UserEntity;
import dao.UserDAO;
import tcp.ClientThread;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

public class InviteCommand extends Command
{
    protected InviteCommand(String trigger) {
        super(trigger);
        requiredParameters = 1;
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (getSender() == null)
            return null;

        CommandOutput commandOutput = new CommandOutput();

        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := place_invitation(?,?); end;");

        statement.registerOutParameter(1, Types.INTEGER);

        statement.setString(2, getSender().getUsername());

        statement.setString(3, parameters.get(0) );

        statement.execute();

        int inviteStatus = statement.getInt(1);

        statement.close();

        switch (inviteStatus)
        {
            case -2: commandOutput.setMessage("You cannot invite yourself!");break;
            case -1: commandOutput.setMessage("Invited user does not exist!");break;
            case 0: commandOutput.setMessage("Invite already sent!");break;
            case 1: commandOutput.setMessage("Invite sent!");break;
        }

        return commandOutput;
    }
}
