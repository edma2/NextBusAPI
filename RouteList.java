import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RouteList extends Command {
    private List<String> routes = new LinkedList<String>();

    public RouteList(String agency) {
        super(agency);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(super.getURL());
        sb.append("?command=routeList");
        sb.append("&a=" + getAgency());
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            public void startElement(
                    java.lang.String uri,
                    java.lang.String localName,
                    java.lang.String qName,
                    Attributes attributes) throws SAXException {
                String tag = qName;
                if (tag.equals("body")) {
                    routes.clear();
                } else if (tag.equals("route")) {
                    String route = attributes.getValue("title");
                    routes.add(route);
                }
            }
        };
    }
}
