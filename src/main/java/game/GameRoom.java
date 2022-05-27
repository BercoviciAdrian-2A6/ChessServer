package game;

import Entities.UserEntity;
import chessboard.ChessColor;
import chessboard.Chessboard;
import dao.MatchDAO;
import dao.MoveDAO;
import tcp.ClientThread;

import java.sql.SQLException;
import java.util.ArrayList;

public class GameRoom extends Thread
{
    public static final String NEW_TURN_STRING = "Make a move!";
    public static final float START_TIMER = 30000;
    public static final float MIN_TIMER = 10;

    int matchIndex = -1;
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

    ArrayList<String> appliedMoves = new ArrayList<>();
    String timersString;
    String endGameMessage = null;
    ArrayList<UserEntity> winner = new ArrayList<>();//chess matches can end in a draw, idk if this is the best representation but w/e


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
        long matchBeginningNano = System.nanoTime();

        String matchBegin = "The match between '" + playerOne.getUsername() + "' and '" + playerTwo.getUsername() + "' has begun!";
        System.out.println(matchBegin);

        p1Thread.queueResponse("GAME IS ABOUT TO BEGIN!");
        p1Thread.queueResponse(matchBegin);
        p2Thread.queueResponse(matchBegin);

        chessboard = new Chessboard();
        chessboard.debugPrintChessboard();

        roundOwner = playerOne;

        try {
            matchIndex = MatchDAO.addMatch(playerOne, playerTwo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setRoundBeginNano();


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

            timersString = playerOne.getUsername() + "-" + p1currentRemaining + "&"
                    + playerTwo.getUsername() + "-" + p2currentRemaining;

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

        int winnerFlag = -1;

        if (winner.size() == 1)
        {
            endGameMessage = "The winner is: '" + winner.get(0).getUsername() + "'!";
            winnerFlag = winner.get(0).getId();
        }
        else
        {
            endGameMessage = "The match has ended in a draw between '" + winner.get(0).getUsername() + "' and '" + winner.get(1).getUsername() + "'.";
        }

        if (p1Thread != null && p1Thread.isAlive())
        {
            p1Thread.queueResponse("The game has ended");
            p1Thread.setGameRoom(null);
            p1Thread.queueResponse(endGameMessage);
        }

        if (p2Thread != null && p2Thread.isAlive())
        {
            p2Thread.queueResponse("The game has ended");
            p2Thread.setGameRoom(null);
            p2Thread.queueResponse(endGameMessage);
        }

        try {
            MatchDAO.endMatch(
                    matchIndex,
                    winnerFlag,
                    (int)((System.nanoTime() - matchBeginningNano) / 1000000000));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        debugPrintMessages();
    }

    public Chessboard getChessboard()
    {
        return chessboard;
    }

    public void changeRoundOwner(String moveString) throws SQLException
    {
        logRound(roundOwner, moveString);
        appliedMoves.add(moveString);

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

    void logRound(UserEntity user,String moveString) throws SQLException {
        MoveDAO.addMove( matchIndex, user, moveString, getRoundEtmr());
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

    public String getBoardStatus()
    {
        String stat = "";
        stat += "%T%" + timersString;

        stat += "%R-" + appliedMoves.size() + "%";

        if (appliedMoves.size() == 0)
            stat += "nomoves";

        for (int moveIndex = 0; moveIndex < appliedMoves.size(); moveIndex++)
        {
            stat += appliedMoves.get(moveIndex);

            if (moveIndex < appliedMoves.size() - 1)
                stat += "&";
        }

        stat += "%M-" + messages.size() + "%";

        if (messages.size() == 0)
            stat += "nomessages";

        for (int messageIndex = 0; messageIndex < messages.size(); messageIndex++)
        {
            stat += messageAuthor.get(messageIndex) + "-" + messages.get(messageIndex);

            if (messageIndex < messages.size() - 1)
            {
                stat += "&";
            }
        }

        /*stat += "%S-";

        if (winner == null || winner.size() == 0)
            stat += "inprogress%-";
        else
        {
            stat += "ended%";

            if (winner.size() == 1)
                stat += "W-" + winner.get(0).getUsername();
            else
                stat += "D";
        }*/

        return stat;
    }

    void debugPrintMessages()
    {
        for (int i = 0; i < messageAuthor.size(); i++)
        {
            System.out.println(messageAuthor.get(i) + " said: " + messages.get(i));
        }
    }
}
