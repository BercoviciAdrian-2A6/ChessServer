package chessboard;

import chessboard.pieces.ChessPiece;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;

public class Chessboard
{
    private ChessField board[][] = new ChessField[8][8];

    private final String INITIAL_CONFIGURATION ="r@A8/n@B8/b@C8/q@D8/k@E8/b@F8/n@G8/r@H8/" +
                                                "p@A7/p@B7/p@C7/p@D7/p@E7/p@F7/p@G7/p@H7/" +
                                                "R@A1/N@B1/B@C1/Q@D1/K@E1/B@F1/N@G1/R@H1/" +
                                                "P@A2/P@B2/P@C2/P@D2/P@E2/P@F2/P@G2/P@H2";

    public Chessboard ()
    {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++ )
        {
            boolean isWhite;

            if (lineIndex % 2 == 0)
                isWhite = true;
            else
                isWhite = false;

            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
            {
                if (isWhite)
                    board[lineIndex][columnIndex] = new ChessField( this, lineIndex, columnIndex, ChessColor.WHITE);
                else
                    board[lineIndex][columnIndex] = new ChessField( this, lineIndex, columnIndex, ChessColor.BLACK);

                isWhite = !isWhite;
            }
        }

        createBoardFromString( INITIAL_CONFIGURATION );//"Q@D4/p@G4/P@D6""B@D4/p@G7/P@B2""N@D4/p@B5/P@C6"

        /*ArrayList<Integer> ij = fieldCodeToCoordinates("C4");

        ArrayList<ChessField> moves = getFieldAtCoordinates( ij.get(0), ij.get(1) ).getChessPiece().getThreatenedFields();

        for (ChessField field: moves)
            field.setChessPiece( ChessPiece.getChessPieceFromCode('*') );*/
    }

    ArrayList<ChessField> getThreatenedFields(ChessColor movedPieceColor)
    {
        ArrayList<ChessField> threatenedFields = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
        {
            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
            {
                ChessPiece piece = board[ lineIndex ][ columnIndex ].getChessPiece();

                if (piece == null || piece.getPieceColor().equals(movedPieceColor))
                    continue;

                threatenedFields.addAll( piece.getThreatenedFields() );
            }
        }

        return threatenedFields;
    }

    boolean kingIsThreatened(ChessColor movedPieceColor)
    {
        char kingCode = 'K';

        if (movedPieceColor.equals(ChessColor.BLACK))
            kingCode = 'k';

        ArrayList<ChessField> threatenedFields = getThreatenedFields(movedPieceColor);

        for (ChessField field: threatenedFields)
        {
            ChessPiece piece = field.getChessPiece();

            if (piece == null)
                continue;

            if (piece.getPieceCode() == kingCode)
                return true;
        }

        return false;

    }

    public int movePiece(String move, ChessColor playerColor)
    {
        String parseMove[] = move.split("-");

        ArrayList<Integer> startIJ = fieldCodeToCoordinates(parseMove[0]);

        ArrayList<Integer> targetIJ = fieldCodeToCoordinates(parseMove[1]);

        int startI = startIJ.get(0);
        int startJ = startIJ.get(1);

        int targetI = targetIJ.get(0);
        int targetJ = targetIJ.get(1);

        ChessPiece movedPiece = board[ startI ][ startJ ].getChessPiece();

        if (movedPiece == null)
            return -2;

        if (movedPiece.getPieceColor() != playerColor)
            return -3;

        ChessField targetField = board[ targetI ][ targetJ ];

        ChessPiece attackedPiece = targetField.getChessPiece();

        ArrayList<ChessField> validMoves = movedPiece.availableMoves();

        boolean moveIsValid = false;

        for ( ChessField field: validMoves )
        {
            if (field == targetField)
            {
                moveIsValid = true;
                break;
            }
        }

        if ( !moveIsValid )
            return -2;


        //apply move

        board[ startI ][ startJ ].setChessPiece(null);

        board[ targetI ][ targetJ ].setChessPiece( movedPiece );

        movedPiece.setParentField( board[ targetI ][ targetJ ] );

        if (kingIsThreatened( movedPiece.getPieceColor() ))
        {
            //undo move

            board[ startI ][ startJ ].setChessPiece(movedPiece);

            board[ targetI ][ targetJ ].setChessPiece( attackedPiece );

            movedPiece.setParentField( board[ startI ][ startJ ] );

            return -1;
        }

        debugPrintChessboard();

        return 0;
    }

    public void createBoardFromString(String input)
    {
        String pieces[] = input.split("/");

        for (int pieceIndex = 0; pieceIndex < pieces.length; pieceIndex++)
        {
            String pieceInfo[] = pieces[ pieceIndex ].split("@");

            ChessPiece chessPiece = ChessPiece.getChessPieceFromCode( pieceInfo[0].charAt(0) );

            ArrayList<Integer> ij = fieldCodeToCoordinates( pieceInfo[1] );

            board[ij.get(0)][ij.get(1)].setChessPiece(chessPiece);

            chessPiece.setParentField( board[ij.get(0)][ij.get(1)] );
        }
    }

    public ArrayList<Integer> fieldCodeToCoordinates( String coordinates )
    {
        int i, j;

        j = coordinates.charAt(0) - 'A';
        i = 8 - (coordinates.charAt(1) - '1' + 1);

        ArrayList<Integer> ij = new ArrayList<>();

        ij.add(i);
        ij.add(j);

        return ij;
    }

    public ChessField getFieldAtCoordinates(int i, int j)
    {
        if (i < 0 || i >= 8 || j < 0 || j >= 8)
            return null;

        return board[i][j];
    }

    public void debugPrintChessboard()
    {
        System.out.println("------------------------------");

        for (int i = 0; i < 8; i ++ )
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].getChessPiece() == null) {
                    if (board[i][j].getFieldColor() == ChessColor.WHITE)
                        System.out.print("  ");
                    else
                        System.out.print("- ");
                }
                else
                {
                    System.out.print(  board[i][j].getChessPiece().getPieceCode() + " " );
                }
            }

            System.out.print("\n");
        }
    }
}
