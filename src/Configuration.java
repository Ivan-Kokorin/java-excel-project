import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static final String TAG_DB = "db_config";
    public static final String TAG_LOG = "login";
    public static final String TAG_PASS = "password";
    private static final String TAG_URL = "url";
    Document document;
    Element config;
    Map<String, String> dbConfig = new HashMap<>();

    public Configuration(String pathXml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new File(pathXml));
            config = document.getDocumentElement();
            getDbConfig();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void getDbConfig() {
        NodeList nodes = config.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_DB.equals(node.getNodeName())) {
                NodeList properties = node.getChildNodes();
                for (int j = 0; j < properties.getLength(); j++) {
                    Node prop = properties.item(j);
                    switch (prop.getNodeName()) {
                        case TAG_LOG:
                            dbConfig.put(TAG_LOG, prop.getAttributes().getNamedItem("val").getNodeValue());
                            break;
                        case TAG_PASS:
                            dbConfig.put(TAG_PASS, prop.getAttributes().getNamedItem("val").getNodeValue());
                            break;
                        case TAG_URL:
                            dbConfig.put(TAG_URL, prop.getAttributes().getNamedItem("val").getNodeValue());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public String getLoginDb() {
        return dbConfig.get(TAG_LOG);
    }

    public String getPasswordDb() {
        return dbConfig.get(TAG_PASS);
    }

    public String getUrlDb() {
        return dbConfig.get(TAG_URL);
    }
}
