package chessboard.pieces;

import chessboard.ChessColor;
import chessboard.ChessField;

import java.security.PublicKey;
import java.util.ArrayList;

public abstract class ChessPiece
{
    private ChessColor pieceColor;
    private ChessField parentField;
    private char pieceCode;

    public ArrayList<ChessField> getThreatenedFields()
    {
        return availableMoves();
    }

    public abstract ArrayList<ChessField> availableMoves();

    public ArrayList<ChessField> availableMoves_positionList(int[] moveOffsetI, int[] moveOffsetJ)//used by Knight and King
    {
        int currentI = getParentField().getCoordinateI();
        int currentJ = getParentField().getCoordinateJ();

        ArrayList<ChessField> moves = new ArrayList<>();

        for (int moveIndex = 0; moveIndex < 8; moveIndex++)
        {
            ChessField targetField = getParentField().getChessboard().getFieldAtCoordinates(
                    currentI + moveOffsetI[moveIndex], currentJ + moveOffsetJ[moveIndex] );

            if (chessFieldIsAvailable(targetField))
                moves.add( targetField );
        }

        return moves;
    }

    public ArrayList<ChessField> availableMoves_multiDirectional( int[] moveDirectionI, int[] moveDirectionJ)//used by Queen, Rook, Bishop
    {
        int currentI = getParentField().getCoordinateI();
        int currentJ = getParentField().getCoordinateJ();

        ArrayList<ChessField> moves = new ArrayList<>();

        for (int diagonalIndex = 0; diagonalIndex < moveDirectionI.length; diagonalIndex++)
        {
            moves.addAll( availableMovesForDirection( currentI, currentJ, moveDirectionI[diagonalIndex], moveDirectionJ[diagonalIndex] ) );
        }

        return moves;
    }

    public ArrayList<ChessField> availableMovesForDirection(int startI, int startJ, int offsetI, int offsetJ)
    {
        ArrayList<ChessField> moves = new ArrayList<>();

        int initialOffsetI = offsetI;
        int initialOffsetJ = offsetJ;

        while (true)
        {
            ChessField targetField = getParentField().getChessboard().getFieldAtCoordinates( startI + offsetI, startJ + offsetJ );

            if (targetField == null)//ran off the board
                break;

            if (targetField.getChessPiece() == null)//if the box is empty just continue
                moves.add(targetField);
            else
            {
                if (targetField.getChessPiece().pieceColor != pieceColor)//if the first piece encountered on path is enemy add it to the available moves
                    moves.add( targetField );

                break;
            }

            offsetI += initialOffsetI;
            offsetJ += initialOffsetJ;
        }

        return moves;
    }

    public static ChessPiece getChessPieceFromCode(char code)
    {
        if (code == 'K')
        {
            King whiteKing = new King();
            whiteKing.setPieceCode(code);
            whiteKing.setPieceColor(ChessColor.WHITE);
            return whiteKing;
        }

        if (code == 'k')
        {
            King blackKing = new King();
            blackKing.setPieceCode(code);
            blackKing.setPieceColor(ChessColor.BLACK);
            return blackKing;
        }

        if (code == 'Q')
        {
            Queen whiteQueen = new Queen();
            whiteQueen.setPieceCode(code);
            whiteQueen.setPieceColor(ChessColor.WHITE);
            return whiteQueen;
        }

        if (code == 'q')
        {
            Queen blackQueen = new Queen();
            blackQueen.setPieceCode(code);
            blackQueen.setPieceColor(ChessColor.BLACK);
            return blackQueen;
        }

        if (code == 'R')
        {
            Rook whiteRook = new Rook();
            whiteRook.setPieceCode(code);
            whiteRook.setPieceColor(ChessColor.WHITE);
            return whiteRook;
        }

        if (code == 'r')
        {
            Rook blackRook = new Rook();
            blackRook.setPieceCode(code);
            blackRook.setPieceColor(ChessColor.BLACK);
            return blackRook;
        }


        if (code == 'N')
        {
            Knight whiteKnight = new Knight();
            whiteKnight.setPieceCode(code);
            whiteKnight.setPieceColor(ChessColor.WHITE);
            return whiteKnight;
        }

        if (code == 'n')
        {
            Knight blackKnight = new Knight();
            blackKnight.setPieceCode(code);
            blackKnight.setPieceColor(ChessColor.BLACK);
            return blackKnight;
        }

        if (code == 'B')
        {
            Bishop whiteBishop = new Bishop();
            whiteBishop.setPieceCode(code);
            whiteBishop.setPieceColor(ChessColor.WHITE);
            return whiteBishop;
        }

        if (code == 'b')
        {
            Bishop blackBishop = new Bishop();
            blackBishop.setPieceCode(code);
            blackBishop.setPieceColor(ChessColor.BLACK);
            return blackBishop;
        }

        if (code == 'P')//capital P
        {
            Pawn whitePawn = new Pawn();
            whitePawn.setPieceCode(code);
            whitePawn.setPieceColor(ChessColor.WHITE);
            return whitePawn;
        }

        if (code == 'p')//small p
        {
            Pawn blackPawn = new Pawn();
            blackPawn.setPieceCode(code);
            blackPawn.setPieceColor(ChessColor.BLACK);
            return blackPawn;
        }

        return null;

    }

    public void setPieceCode(char pieceCode) {
        this.pieceCode = pieceCode;
    }

    public char getPieceCode() {
        return pieceCode;
    }

    public ChessField getParentField() {
        return parentField;
    }

    public void setParentField(ChessField parentField) {
        this.parentField = parentField;
    }

    public ChessColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(ChessColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    protected boolean chessFieldIsAvailable(ChessField targetField)
    {
        if (targetField == null)
            return false;

        if (targetField.getChessPiece() == null || targetField.getChessPiece().getPieceColor() != pieceColor)
            return true;

        return false;
    }
}
