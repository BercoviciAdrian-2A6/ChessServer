package chessboard.pieces;

import chessboard.ChessField;

import java.util.ArrayList;

public class King extends ChessPiece
{

    public static final int MOVE_DIRECTION_I[] = {-1, -1, -1,  0, 1, 1,  1,  0};
    public static final int MOVE_DIRECTION_J[] = {-1,  0,  1,  1, 1, 0, -1, -1};


    @Override
    public ArrayList<ChessField> availableMoves()
    {
        return availableMoves_positionList(MOVE_DIRECTION_I, MOVE_DIRECTION_J);
    }
}
