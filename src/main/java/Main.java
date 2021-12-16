import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/magnit_test";
    private static final String USER = "postgres";
    private static final String PASSWORD = "02029315";

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DataBase dataBase = new DataBase(1000000, connection);
            dataBase.createXml("1.xml");
            dataBase.disconnect();

            XmlUtil.convertXml("1.xml", "2.xml", "src/main/resources/style.xsl");
            System.out.println("Arithmetic sum of all attribute values: " + XmlUtil.sumOfElements("2.xml"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
