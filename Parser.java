import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class Parser extends DefaultHandler {
    public Parser() {
        super();
    }

    public void startElement(java.lang.String uri, java.lang.String localName,
            java.lang.String qName, Attributes attributes) throws SAXException {
        if (!localName.equals("predictions"))
            return;
        String routeTitle = attributes.getValue("routeTitle");
        System.out.println("Route: " + routeTitle);
    }

    public static void main(String[] args) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new Parser());
            xr.setErrorHandler(new Parser());
            xr.parse("test.xml");
        } catch (IOException ex1) {
            ex1.printStackTrace();
        } catch (SAXException ex2) {
            ex2.printStackTrace();
        }
    }
}
