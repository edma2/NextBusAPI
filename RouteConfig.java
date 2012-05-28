import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.xml.sax.Attributes;

public class RouteConfig extends Command {
    private String routeTag; 
    private Map<Direction, List<Stop>> paths =
                new HashMap<Direction, List<Stop>>();

    public RouteConfig(String agency, String routeTag) {
        super(agency);
        this.routeTag = routeTag;
    }

    public Map<Direction, List<Stop>> getPaths() {
        return paths;
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=routeConfig");
        sb.append("&a=" + getAgency());
        sb.append("&r=" + routeTag);
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            // stopTag -> Stop
            Map<String, Stop> stops = new HashMap<String, Stop>();
            Direction dir;

            @Override
            public void handleElement(String tag, Attributes attributes) {
                String parentTag = getLastTag();
                if (tag.equals("body")) {
                    // <body>
                    paths.clear();
                } else if (parentTag.equals("route") && tag.equals("stop")) {
                    // <body>
                    //   <route>
                    //     <stop>
                    String stopTag = attributes.getValue("tag");
                    Stop stop = new
                        Stop(stopTag,
                             attributes.getValue("title"),
                             Double.parseDouble(attributes.getValue("lon")),
                             Double.parseDouble(attributes.getValue("lat")));
                    stops.put(stopTag, stop);
                } else if (parentTag.equals("route") && tag.equals("direction")) {
                    // <body>
                    //   <route>
                    //     <direction>
                    String dirTag = attributes.getValue("tag");
                    String dirTitle = attributes.getValue("title");
                    dir = new Direction(dirTag, dirTitle);
                    paths.put(dir, new LinkedList<Stop>());
                } else if (parentTag.equals("direction") && tag.equals("stop")) {
                    // <body>
                    //   <route>
                    //     <direction>
                    //       <stop>
                    String stopTag = attributes.getValue("tag");
                    Stop stop = stops.get(stopTag);
                    paths.get(dir).add(stop);
                }
            }
        };
    }
}
