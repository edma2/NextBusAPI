import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RouteListParser extends DefaultHandler {
    public LinkedList<String> tags = new LinkedList<String>();

    public void startElement(java.lang.String uri, java.lang.String localName,
            java.lang.String qName, Attributes attributes) throws SAXException {
        if (localName.equals("route")) {
            String tag = attributes.getValue("tag");
            tags.add(tag);
        }
    }
}
