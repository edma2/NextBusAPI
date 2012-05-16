package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashMap;

import org.xml.sax.SAXException;

public class NextBus {
    private String agency;

    /* For mapping stopIds to unique stops */
    private HashMap<Integer, BusStop> stops = new HashMap<Integer, BusStop>();

    public NextBus(String agency) {
        this.agency = agency;
    }

    public void collectStops() throws IOException, org.xml.sax.SAXException {
        API api = new API("actransit");
        for (String route : api.getRouteList()) {
            for (RouteConfigInfo i : api.getRouteConfig(route)) {
                BusStop stop = new BusStop(agency, i.stopId, i.latitude,
                        i.longitude);
                stops.put(i.stopId, stop);
            }
        }
    }
}
