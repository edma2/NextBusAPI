import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class Parser extends DefaultHandler {
    public static void main(String[] args) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }
}
