package tcp;

import Entities.UserEntity;
import dao.MatchDAO;
import dao.UserDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server extends Thread {
    public static final int PORT = 8100;
    private static volatile ArrayList<ClientThread> clientThreads = new ArrayList<>();
    ServerSocket serverSocket = null;

    public Server () throws IOException {
        serverSocket = new ServerSocket(PORT);
    }


    public void run()
    {
        try
        {
            MatchDAO.assureGameIntegrity();
            UserDAO.logoutAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try {
            System.out.println("Server is waiting for connections");

            while (true)
            {
                Socket socket = serverSocket.accept();
                ClientThread newClient = new ClientThread(socket);
                clientThreads.add(newClient);
                newClient.start();

                if (currentThread().isInterrupted())
                    break;
            }
        }
        catch (IOException e)
        {

        }
        finally {
            if (serverSocket != null) {
                try
                {
                    serverSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Server has shut down");
    }

    public static ClientThread getClientThreadByUser(UserEntity user)
    {
        for (ClientThread thread : clientThreads)
        {
            if (thread == null)
                continue;

            if (thread.getLoggedInUser().getUsername().equals( user.getUsername() ))
                return thread;
        }

        return null;
    }
}
