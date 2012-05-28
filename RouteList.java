import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RouteList extends Command {
    private List<String> routeTags = new LinkedList<String>();

    public RouteList(String agency) {
        super(agency);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=routeList");
        sb.append("&a=" + getAgency());
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body"))
                    routeTags.clear();
                else if (tag.equals("route"))
                    routeTags.add(attributes.getValue("tag"));
            }
        };
    }
}
