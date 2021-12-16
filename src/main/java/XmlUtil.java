import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class XmlUtil {

    public static void convertXml(String fromFile, String toFile, String xsltFile) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(new StreamSource(xsltFile));
        transformer.transform(new StreamSource(fromFile), new StreamResult(toFile));
    }

    public static long sumOfElements(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(xmlFile);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(fXmlFile);
        document.getDocumentElement().normalize();

        NodeList nList = document.getElementsByTagName("entry");

        long result = 0;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element element = (Element) node;
            result += Long.parseLong(element.getAttribute("field"));
        }

        return result;
    }
}
