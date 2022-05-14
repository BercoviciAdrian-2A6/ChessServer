package commands;

import Entities.UserEntity;
import dao.FriendsDAO;
import dao.UserDAO;
import tcp.ClientThread;

import java.util.ArrayList;
import java.util.List;

public class FriendCommand extends Command
{
    public FriendCommand(String trigger)
    {
        super(trigger);
    }

    /**
     * @param parameters each parameter is a username of a user that will receive a friend request
     * @return the valid users that were befriended
     */
    @Override
    public CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception {
        CommandOutput commandOutput = new CommandOutput();

        List<UserEntity> targetUsers = UserDAO.getUsersByUsernames(parameters);

        String output = "(valid) Users befriended: ";

        for (UserEntity receiver: targetUsers)
        {
            FriendsDAO.createFriendship( getSender(), receiver );
            output += "," + receiver.getUsername() + " ";
        }

        commandOutput.setMessage(output);

        return commandOutput;
    }
}
