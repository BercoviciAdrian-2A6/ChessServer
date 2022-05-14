package chessboard.pieces;

import chessboard.ChessField;

import java.util.ArrayList;

public class Rook extends ChessPiece
{
    public static final int MOVE_DIRECTION_I[] = {-1, 0, 1,  0};
    public static final int MOVE_DIRECTION_J[] = { 0, 1, 0, -1};

    @Override
    public ArrayList<ChessField> availableMoves()
    {
        return availableMoves_multiDirectional(MOVE_DIRECTION_I, MOVE_DIRECTION_J);
    }
}
