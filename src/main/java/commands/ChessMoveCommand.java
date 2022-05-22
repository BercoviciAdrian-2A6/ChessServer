package commands;

import chessboard.ChessColor;
import game.GameRoom;
import tcp.ClientThread;

import java.util.ArrayList;

public class ChessMoveCommand extends Command
{
    protected ChessMoveCommand(String trigger) {
        super(trigger);
    }

    /**
     * @param parameters should contain a single parameter of type A1-A2 (example) indicating which piece is moved
     * @param clientThread
     * @return
     * @throws Exception
     */
    @Override
    protected CommandOutput runCommand(ArrayList<String> parameters, ClientThread clientThread) throws Exception
    {
        if (clientThread.getGameRoom() == null)
        {
            CommandOutput beInGame = new CommandOutput();
            beInGame.setMessage("You must be in game to move chess pieces!");
            return beInGame;
        }

        if ( !getSender().getUsername().equals( clientThread.getGameRoom().getRoundOwner().getUsername() ))
        {
            CommandOutput waitForTurn = new CommandOutput();
            waitForTurn.setMessage("PLEASE WAIT FOR YOUR TURN");
            return waitForTurn;
        }

        CommandOutput commandOutput = new CommandOutput();

        GameRoom gameRoom = clientThread.getGameRoom();

        ChessColor activePlayerColor = gameRoom.getRoundOwnerColor();

        int moveStatus = gameRoom.getChessboard().movePiece(parameters.get(0), activePlayerColor);

        if (moveStatus == -3)
            commandOutput.setMessage("PLEASE LEAVE YOUR OPPONENT'S PIECES ALONE!");

        if (moveStatus == -2)
            commandOutput.setMessage("INVALID MOVE!");

        if (moveStatus == -1)
            commandOutput.setMessage("INVALID MOVE - CHECK");

        if (moveStatus == 0)
        {
            commandOutput.setMessage("MOVE WAS APPLIED!");
            clientThread.getGameRoom().changeRoundOwner(parameters.get(0));
        }

        return commandOutput;
    }
}
