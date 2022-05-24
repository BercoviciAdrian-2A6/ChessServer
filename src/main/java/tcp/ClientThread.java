package tcp;

import Entities.UserEntity;
import commands.CommandManager;
import commands.CommandOutput;
import dao.UserDAO;
import game.GameRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientThread extends Thread
{
    private Socket socket = null;
    private GameRoom gameRoom;
    volatile ArrayList<String> responseQueue = new ArrayList<>();
    UserEntity loggedInUser;

    public ClientThread (Socket socket)
    {
        this.socket = socket;
    }
    public void run ()
    {
        System.out.println("Connection to client started");

        try {
            while (true)
            {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientRequest = in.readLine();

                if (clientRequest == null)
                    continue;

                CommandOutput commandOutput = CommandManager.getSingleton().runCommand(clientRequest, this);

                PrintWriter out = new PrintWriter(socket.getOutputStream());

                if (commandOutput == null)
                {
                    commandOutput = new CommandOutput();
                }

                if (commandOutput.getMessage() == "--idle--" && responseQueue.size() > 0)
                {
                    commandOutput.setMessage( responseQueue.get(0) );
                    responseQueue.remove(0);
                }

                String serverResponse = commandOutput.getMessage();
                out.println(serverResponse);
                out.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }

        try
        {
            UserDAO.logoutUser(loggedInUser);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Connection to client ended");
    }

    public GameRoom getGameRoom()
    {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom)
    {
        this.gameRoom = gameRoom;
    }

    public void queueResponse(String response)
    {
        responseQueue.add( response );
    }

    public void setLoggedInUser(UserEntity loggedInUser)
    {
        this.loggedInUser = loggedInUser;
        System.out.println(loggedInUser.getUsername() +  " has logged in!");
    }

    public UserEntity getLoggedInUser()
    {
        return loggedInUser;
    }
}
