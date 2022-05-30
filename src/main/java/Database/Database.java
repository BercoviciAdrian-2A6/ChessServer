package Database;

import java.sql.*;

//https://docs.oracle.com/cd/A84870_01/doc/java.816/a81354/samapp2.htm
//https://datacadamia.com/lang/plsql/dbms_random

public class Database {

    Connection connection;

    public Database() throws SQLException {
        //String address = "jdbc:postgresql://localhost:5432/JavaLab10";
        //connection = DriverManager.getConnection(address, "postgres", "STUDENT");
        String address = "jdbc:oracle:thin:@//localhost:1521/XE";
        connection = DriverManager.getConnection(address, "student", "student");
    }

    public Connection getConnection()
    {
        return connection;
    }

}
