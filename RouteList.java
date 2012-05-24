import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RouteList extends API {
    private List<String> routes = new LinkedList<String>();

    public RouteList(String agency) {
        super(agency);
    }

    protected DefaultHandler getHandler() {
        return new DefaultHandler() {
            public void startElement(
                    java.lang.String uri,
                    java.lang.String localName,
                    java.lang.String qName,
                    Attributes attributes) throws SAXException {
                String tag = qName;
                if (tag.equals("route")) {
                    String route = attributes.getValue("title");
                    routes.add(route);
                }
            }
        };
    }

    public void update() {
        try {
            routes.clear();
            StringBuilder sb = new StringBuilder(URL + "?");
            sb.append("command=routeList");
            sb.append("&a=" + agency);
            String url = sb.toString();
            XMLParse(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
