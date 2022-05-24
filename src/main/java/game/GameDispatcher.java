package game;

import Entities.UserEntity;
import tcp.ClientThread;

public class GameDispatcher
{
    public static GameDispatcher singleton = null;
    public volatile GameRoom queuedRoom = null;

    public static GameDispatcher getInstance()
    {
        if (singleton == null)
            singleton = new GameDispatcher();

        return singleton;
    }

    public GameRoom enterQueue(UserEntity queueRequester, ClientThread clientThread)
    {
        if ( queuedRoom == null || queuedRoom.getRoomIsFull() )
        {
            queuedRoom = new GameRoom(queueRequester, clientThread);
            return queuedRoom;
        }

        if ( queuedRoom.getPlayerOne() == queueRequester )
            return queuedRoom;

        queuedRoom.setPlayerTwo( queueRequester, clientThread );

        queuedRoom.start();

        return queuedRoom;
    }

    public int dequeue(UserEntity user, ClientThread clientThread)
    {
        if (queuedRoom == null || user == null)
            return 0;

        if (user.getUsername().equals(queuedRoom.getPlayerOne().getUsername()))
        {
            clientThread.setGameRoom(null);
            queuedRoom = null;
            return 1;
        }

        return 0;
    }


}
