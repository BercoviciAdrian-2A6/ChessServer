package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public static final int PORT = 8100;
    ServerSocket serverSocket = null;

    public Server () throws IOException {
        serverSocket = new ServerSocket(PORT);
    }


    public void run()
    {

        try {
            System.out.println("Server is waiting for connections");

            while (true)
            {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();

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
}
