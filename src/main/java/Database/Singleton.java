package Database;

import java.sql.SQLException;

public class Singleton {
    static private Database dataBase;

    static public Database getInstance() throws SQLException {
        if(dataBase == null){
            dataBase = new Database();
        }
        return dataBase;
    }
}
