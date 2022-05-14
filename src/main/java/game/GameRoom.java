package game;

import Entities.UserEntity;
import chessboard.ChessColor;
import chessboard.Chessboard;
import tcp.ClientThread;

import java.util.ArrayList;

public class GameRoom extends Thread
{
    public static final String NEW_TURN_STRING = "Make a move!";
    public static final float START_TIMER = 30000;
    public static final float MIN_TIMER = 10;

    UserEntity playerOne;//white
    UserEntity playerTwo;//black
    UserEntity invitedPlayerTwo;

    ClientThread p1Thread, p2Thread;

    UserEntity roundOwner;
    Chessboard chessboard;
    Long roundBeginNano;

    float player1RemainingTime = START_TIMER;
    float player2RemainingTime = START_TIMER;

    UserEntity forfeit;

    ArrayList<String> messageAuthor = new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>();

    public GameRoom ( UserEntity playerOne, ClientThread playerThread )
    {
        this.playerOne = playerOne;
        p1Thread = playerThread;
    }

    public UserEntity getPlayerOne()
    {
        return playerOne;
    }

    public void setPlayerTwo(UserEntity playerTwo, ClientThread playerThread)
    {
        this.playerTwo = playerTwo;
        p2Thread = playerThread;
    }

    public boolean getRoomIsFull()
    {
        if (playerOne != null && playerTwo != null)
            return true;

        return false;
    }

    public void run()
    {
        String matchBegin = "The match between '" + playerOne.getUsername() + "' and '" + playerTwo.getUsername() + "' has begun!";
        System.out.println(matchBegin);

        p1Thread.queueResponse("GAME IS ABOUT TO BEGIN!");
        p1Thread.queueResponse(matchBegin);
        p2Thread.queueResponse(matchBegin);

        chessboard = new Chessboard();
        chessboard.debugPrintChessboard();

        roundOwner = playerOne;

        setRoundBeginNano();

        ArrayList<UserEntity> winner = new ArrayList<>();//chess matches can end in a draw, idk if this is the best representation but w/e

        p1Thread.queueResponse(NEW_TURN_STRING);

        while (winner.size() == 0)
        {
            if ( !p1Thread.isAlive() )
            {
                winner.add( playerTwo );
                break;
            }

            if ( !p2Thread.isAlive() )
            {
                winner.add( playerOne );
                break;
            }

            float p1currentRemaining = player1RemainingTime;
            float p2currentRemaining = player2RemainingTime;

            if (roundOwner == playerOne)
            {
                p1currentRemaining -= getRoundEtmr();
            }
            else
            {
                p2currentRemaining -= getRoundEtmr();
            }

            if (p1currentRemaining <= 0)
                winner.add( playerTwo );

            if (p2currentRemaining <= 0)
                winner.add( playerOne );

            if (forfeit != null)
            {
                if (forfeit.getUsername() == playerOne.getUsername())
                    winner.add( playerTwo );
                else
                    winner.add( playerOne );
            }

            //System.out.println(p1currentRemaining + " - " + p2currentRemaining);
        }

        String endGameMessage = "";

        if (winner.size() == 1)
        {
            endGameMessage = "The winner is: '" + winner.get(0).getUsername() + "'!";
        }
        else
        {
            endGameMessage = "The match has ended in a draw between '" + winner.get(0).getUsername() + "' and '" + winner.get(1).getUsername() + "'.";
        }

        if (p1Thread != null && p1Thread.isAlive())
        {
            p1Thread.queueResponse("The game has ended");
            p1Thread.queueResponse(endGameMessage);
        }

        if (p2Thread != null && p2Thread.isAlive())
        {
            p2Thread.queueResponse("The game has ended");
            p2Thread.queueResponse(endGameMessage);
        }

        debugPrintMessages();
    }

    public Chessboard getChessboard()
    {
        return chessboard;
    }

    public void changeRoundOwner()
    {
        if (roundOwner == playerOne)
        {
            player1RemainingTime -= getRoundEtmr();//decrease remaining timer before changing
            roundOwner = playerTwo;
            p2Thread.queueResponse(NEW_TURN_STRING);

            player2RemainingTime = Math.max( player2RemainingTime, MIN_TIMER );//if timer is less than minimum set to minimum
        }
        else
        {
            player2RemainingTime -= getRoundEtmr();//decrease remaining timer before changing
            roundOwner = playerOne;
            p1Thread.queueResponse(NEW_TURN_STRING);

            player1RemainingTime = Math.max( player1RemainingTime, MIN_TIMER );
        }

        setRoundBeginNano();
    }

    public UserEntity getRoundOwner()
    {
        return roundOwner;
    }

    public ChessColor getRoundOwnerColor()
    {
        if (roundOwner == playerOne)
            return ChessColor.WHITE;

        return ChessColor.BLACK;
    }

    void setRoundBeginNano()
    {
        roundBeginNano = System.nanoTime();
    }

    float getRoundEtmr()//how many seconds have passed since the current player has begun his round
    {
        return (System.nanoTime() - roundBeginNano) / 1000000000.0f;
    }

    public void setForfeit(UserEntity forfeit) {
        this.forfeit = forfeit;
    }

    public void addMessage( String authorUsername, String messageContent )
    {
        messageAuthor.add(authorUsername);
        messages.add(messageContent);
    }

    void debugPrintMessages()
    {
        for (int i = 0; i < messageAuthor.size(); i++)
        {
            System.out.println(messageAuthor.get(i) + " said: " + messages.get(i));
        }
    }
}
