package commands;

import Entities.UserEntity;
import dao.InviteDAO;
import dao.UserDAO;
import game.GameRoom;
import tcp.ClientThread;
import tcp.Server;

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
            ClientThread originalSenderThread = Server.getClientThreadByUser(originalSender);

            if (originalSenderThread == null)
            {
                CommandOutput userNotLoggedIn = new CommandOutput();
                userNotLoggedIn.setMessage("User is not logged in!");
                return userNotLoggedIn;
            }

            if (originalSenderThread.getGameRoom() != null)
            {
                CommandOutput userInGame = new CommandOutput();
                userInGame.setMessage("User is ingame!");
                return userInGame;
            }

            InviteDAO.voidInvite(originalSender.getUsername(), getSender().getUsername());
            GameRoom invitationRoom = new GameRoom(originalSender, originalSenderThread);
            invitationRoom.setPlayerTwo( getSender(), clientThread );

            originalSenderThread.setGameRoom(invitationRoom);
            clientThread.setGameRoom(invitationRoom);

            invitationRoom.start();

            CommandOutput commandOutput = new CommandOutput();

            commandOutput.setMessage("GAME IS ABOUT TO START!");

            return commandOutput;

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
