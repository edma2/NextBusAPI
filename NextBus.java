import java.util.HashSet;
import java.util.Set;

public class NextBus {
    private String agency;

    public NextBus(String agency) {
        this.agency = agency;
    }

    public Stop[] getStops() {
        Set<Stop> stops = new HashSet<Stop>();
        RouteList rl = new RouteList(agency);
        rl.execute();
        for (String routeTag : rl.getRouteTags()) {
            RouteConfig rc = new RouteConfig(agency, routeTag);
            rc.execute();
            for (Direction dir : rc.getDirections()) {
                for (Stop stop : rc.getStops(dir))
                    stops.add(stop);
            }
        }
        return stops.toArray(new Stop[0]);
    }

    public static void main(String[] args) {
        NextBus nb = new NextBus("actransit");
        for (Stop s : nb.getStops())
            System.out.println(s.title);
    }
}
