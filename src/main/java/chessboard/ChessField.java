package chessboard;

import chessboard.pieces.ChessPiece;

public class ChessField
{
    Chessboard chessboard;
    private int coordinateI, coordinateJ;
    private ChessColor fieldColor;
    private ChessPiece chessPiece = null;
    private String fieldName;

    public ChessField( Chessboard chessboard, int i, int j, ChessColor fieldColor)
    {
        this.chessboard = chessboard;
        coordinateI = i;
        coordinateJ = j;
        this.fieldColor = fieldColor;
        fieldName = "";
        fieldName += (char)('A' + j);
        fieldName += (8 - i + 1);
    }

    public ChessColor getFieldColor() {
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

    public String getFieldName() {
        return fieldName;
    }
}
