package ma.enset.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignletonConnexionDB {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                    "team_player_db","","");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
    public static Connection getConnection(){
        return connection;
    }
}
