package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class RouteList extends Command {
    private List<Route> routes = new LinkedList<Route>();

    public RouteList(Agency agency) {
        super(agency);
    }

    public Route[] getRoutes() {
        return routes.toArray(new Route[0]);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=routeList");
        sb.append("&a=" + enc(getAgency().tag));
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body")) {
                    routes.clear();
                } else if (tag.equals("route")) {
                    String routeTag = attributes.getValue("tag");
                    String routeTitle = attributes.getValue("title");
                    routes.add(new Route(routeTag, routeTitle));
                }
            }
        };
    }

    public static void main(String[] args) {
        RouteList command = new RouteList(new Agency("actransit", "AC Transit"));
        command.execute();
        for (Route route : command.getRoutes())
            System.out.println(route.title);
    }
}
