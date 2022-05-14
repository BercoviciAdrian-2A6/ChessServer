package chessboard.pieces;

import chessboard.ChessField;

import java.util.ArrayList;

public class Knight extends ChessPiece
{
    public static final int MOVE_DIRECTION_I[] = {-2, -1,  1,  2,  2,  1, -1, -2};
    public static final int MOVE_DIRECTION_J[] = { 1,  2,  2,  1, -1, -2, -2, -1};

    @Override
    public ArrayList<ChessField> availableMoves()
    {
        return availableMoves_positionList(MOVE_DIRECTION_I, MOVE_DIRECTION_J);
    }
}
