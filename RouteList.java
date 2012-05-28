import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class RouteList extends Command {
    private List<String> routeTags;

    public RouteList(String agency) {
        super(agency);
    }

    public String[] getRouteTags() {
        return routeTags.toArray(new String[0]);
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
                    routeTags = new LinkedList<String>();
                else if (tag.equals("route"))
                    routeTags.add(attributes.getValue("tag"));
            }
        };
    }
}
