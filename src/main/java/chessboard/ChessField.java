package chessboard;

import chessboard.pieces.ChessPiece;

public class ChessField
{
    Chessboard chessboard;
    private int coordinateI, coordinateJ;
    private int fieldColor = 0;
    private ChessPiece chessPiece = null;

    public ChessField( Chessboard chessboard, int i, int j, int fieldColor)
    {
        this.chessboard = chessboard;
        coordinateI = i;
        coordinateJ = j;
        this.fieldColor = fieldColor;
    }

    public int getFieldColor() {
        return fieldColor;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public int getCoordinateI() {
        return coordinateI;
    }

    public int getCoordinateJ() {
        return coordinateJ;
    }
}
