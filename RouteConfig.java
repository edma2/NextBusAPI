import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RouteConfig extends Command {
    private String routeTag; 
    private Map<Direction, List<Stop>> paths =
                new HashMap<Direction, List<Stop>>();

    class Stop {
        String title;
        String tag;
        double latitude;
        double longitude;

        Stop(String tag, String title, double latitude, double longitude) {
            this.tag = tag;
            this.title = title;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    class Direction {
        String tag;
        String title;

        Direction(String tag, String title) {
            this.tag = tag;
            this.title = title;
        }
    }

    public RouteConfig(String agency, String routeTag) {
        super(agency);
        this.routeTag = routeTag;
    }

    public Direction[] getDirections() {
        List<Direction> dirs = new LinkedList<Direction>();
        for (Direction dir : paths.keySet())
            dirs.add(dir);
        return dirs.toArray(new Direction[0]);
    }

    public Stop[] getPath(Direction dir) {
        return paths.get(dir).toArray(new Stop[0]);
    }

    public int[] getTimes(Direction dir, Stop stop) {
        Predictions p = new Predictions(getAgency(), routeTag, dir.tag,
                stop.tag);
        p.execute();
        return p.getTimes();
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(super.getURL());
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

    public static void main(String[] args) {
        RouteConfig rc = new RouteConfig("actransit", "65");
        rc.execute();
        for (Direction dir : rc.getDirections()) {
            System.out.println("=============");
            System.out.println(dir.title);
            for (Stop s : rc.getPath(dir)) {
                System.out.println("-------------");
                System.out.println(s.title);
                for (int time : rc.getTimes(dir, s))
                    System.out.print(time + ", ");
                System.out.println("-------------");
            }
            System.out.println("=============");
        }
    }
}
