import chessboard.Chessboard;
import dao.UserDAO;
import tcp.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException, SQLException
    {
        Server server = new Server();
        server.start();

        /*Chessboard chessboard = new Chessboard();

        while (true)
        {
            chessboard.debugPrintChessboard();

            System.out.println("input a move: ");

            Scanner scanner = new Scanner( System.in );

            String line = scanner.nextLine();

            int res = chessboard.movePiece( line );

            if (res == -2)
                System.out.println("INVALID MOVE!");

            if (res == -1)
                System.out.println("INVALID MOVE - CHECK");
        }*/
    }
}
