package chessboard.pieces;

import chessboard.ChessField;

import java.util.ArrayList;

public class Bishop extends ChessPiece
{
    public static final int MOVE_DIRECTION_I[] = {-1, -1, 1,  1};
    public static final int MOVE_DIRECTION_J[] = {-1,  1, 1, -1};

    @Override
    public ArrayList<ChessField> availableMoves()
    {
        return availableMoves_multiDirectional(MOVE_DIRECTION_I, MOVE_DIRECTION_J);
    }
}
