package commands;

import Database.Singleton;
import tcp.ClientThread;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

public class ViewReceivedInvitesCommand extends Command
{
    protected ViewReceivedInvitesCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (getSender() == null)
            return null;

        CommandOutput commandOutput = new CommandOutput();

        Connection dbConnection = Singleton.getDataBase().getConnection();

        CallableStatement statement = dbConnection.prepareCall("begin ? := get_received_invitations(?); end;");

        statement.registerOutParameter(1, Types.VARCHAR);

        statement.setString(2, getSender().getAuthenticationToken() );

        statement.execute();

        commandOutput.setMessage(statement.getString(1));

        statement.close();

        return commandOutput;
    }
}
