import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {

    private int n;

    private Connection connection;

    public DataBase(int n, Connection connection) throws SQLException {
        this.n = n;
        this.connection = connection;
        createTable();
        fillTable();
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void createTable() throws SQLException {
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS test(field INTEGER)").execute();
    }

    private void fillTable() throws SQLException {
        connection.prepareStatement("DELETE * FROM test").execute();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO test(field) VALUES (?)");
        for (int i = 1; i <= n; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.execute();
        }
    }
}
