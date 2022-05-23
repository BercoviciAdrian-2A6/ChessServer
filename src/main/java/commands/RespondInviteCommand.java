package commands;

import Entities.UserEntity;
import dao.InviteDAO;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;

public class RespondInviteCommand extends Command
{
    protected RespondInviteCommand(String trigger) {
        super(trigger);
    }

    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        UserEntity originalSender = UserDAO.getUserByUsername( parameters.get(0) );

        if (parameters.get(1).equals("y"))
        {

        }
        else if (parameters.get(1).equals("n"))
        {
            InviteDAO.voidInvite( originalSender.getUsername(), getSender().getUsername() );
            CommandOutput inviteRejected = new CommandOutput();
            inviteRejected.setMessage("Invite from " + originalSender.getUsername() + " rejected!");
            return inviteRejected;
        }

        return null;
    }
}
