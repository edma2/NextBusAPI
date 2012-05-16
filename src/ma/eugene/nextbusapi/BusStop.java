/**
 * Bus stop state.
 * Knows prediction information for each incoming route in either direction.
 */
package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.SAXException;

public class BusStop {
    public static List<BusStop> allStops = new LinkedList<BusStop>();

    private class Route {
        private String title;
        private String direction;

        public Route(String title, String direction) {
            this.title = title;
            this.direction = direction;
        }
    }
    private HashMap<Route, List<Integer>> predictions = new HashMap<Route,
            List<Integer>>();
    private float latitude;
    private float longitude;

    public BusStop(String agency, int stopId, float latitude, float longitude)
                          throws IOException, org.xml.sax.SAXException {
        API api = new API(agency);
        for (PredictionsInfo p : api.getPredictions(stopId))
            predictions.put(new Route(p.title, p.direction), p.times);
        this.latitude = latitude;
        this.longitude = longitude;
        allStops.add(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Route r : predictions.keySet()) {
            sb.append(r.title + ':' + r.direction + predictions.get(r));
            sb.append("\n");
        }
        return sb.toString();
    }
}
