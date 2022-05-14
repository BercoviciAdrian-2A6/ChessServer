package chessboard.pieces;

import chessboard.ChessField;

import java.util.ArrayList;

public class Pawn extends ChessPiece
{
    @Override
    public ArrayList<ChessField> availableMoves()
    {
        int currentI = getParentField().getCoordinateI();
        int currentJ = getParentField().getCoordinateJ();

        int moveDirection = -1;
        int frontalReach = 1;

        if (getPieceColor() != 0)//is not white
            moveDirection = 1;

        if (( getPieceColor() == 0 && currentI == 6 ) || (getPieceColor() != 0 && currentI == 1) )
            frontalReach = 2;

        ArrayList<ChessField> moves = new ArrayList<>();

        for (int i = 1; i <= frontalReach; i++)//this takes care of the frontal movement of the pawns
        {
            ChessField targetField = getParentField().getChessboard().getFieldAtCoordinates( currentI + i * moveDirection, currentJ );

            if (targetField == null)
                break;

            if (targetField.getChessPiece() != null)//you can't take a piece right in front of the pawn so it doesn't matter if ii is opposite color
                break;

            moves.add( targetField );
        }

        ChessField diagonal1 = getParentField().getChessboard().getFieldAtCoordinates( currentI + moveDirection, currentJ + 1 );

        ChessField diagonal2 = getParentField().getChessboard().getFieldAtCoordinates( currentI + moveDirection, currentJ - 1 );

        if ( chessFieldIsAvailable(diagonal1) )
            moves.add( diagonal1 );

        if ( chessFieldIsAvailable(diagonal2) )
            moves.add( diagonal2 );

        return moves;
    }

    @Override
    public ArrayList<ChessField> getThreatenedFields()
    {
        int currentI = getParentField().getCoordinateI();
        int currentJ = getParentField().getCoordinateJ();

        ArrayList<ChessField> threatenedFields = new ArrayList<>();

        int moveDirection = -1;

        if (getPieceColor() != 0)//is not white
            moveDirection = 1;

        ChessField diagonal1 = getParentField().getChessboard().getFieldAtCoordinates( currentI + moveDirection, currentJ + 1 );

        ChessField diagonal2 = getParentField().getChessboard().getFieldAtCoordinates( currentI + moveDirection, currentJ - 1 );

        if ( chessFieldIsAvailable(diagonal1) )
            threatenedFields.add( diagonal1 );

        if ( chessFieldIsAvailable(diagonal2) )
            threatenedFields.add( diagonal2 );

        return threatenedFields;
    }
}
