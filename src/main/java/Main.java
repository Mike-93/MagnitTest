import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/magnit_test";
    private static final String USER = "postgres";
    private static final String PASSWORD = "02029315";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

        DataBase dataBase = new DataBase(10, connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
