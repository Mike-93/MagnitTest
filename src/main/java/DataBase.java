import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private int n;

    private Connection connection;

    public DataBase(int n, Connection connection) throws SQLException {
        this.n = n;
        this.connection = connection;
        createTable();
        fillTable();
    }

    public void disconnect() throws SQLException {
        connection.close();
        connection = null;
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
        connection.setAutoCommit(false);
        connection.prepareStatement("DELETE FROM test").execute();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO test(field) VALUES (?)");
        for (int i = 1; i <= n; i++) {
           preparedStatement.setInt(1, i);
           preparedStatement.execute();
        }
        connection.commit();
    }

    public void createXml(String fileName) throws ParserConfigurationException, SQLException, TransformerException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element entries = document.createElement("entries");

        document.appendChild(entries);
        for (Integer n : getFields()) {
            Element entry = document.createElement("entry");
            entries.appendChild(entry);

            Element field = document.createElement("field");
            field.appendChild(document.createTextNode(String.valueOf(n)));
            entry.appendChild(field);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));

        transformer.transform(source, result);
    }

    private List<Integer> getFields() throws SQLException {

        List<Integer> fields = new ArrayList<>();

        ResultSet resultSet = connection.prepareStatement("SELECT field FROM test").executeQuery();
        while (resultSet.next()) {
            fields.add(resultSet.getInt("field"));
        }
        resultSet.close();

        return fields;
    }
}
