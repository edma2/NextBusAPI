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
    private double latitude;
    private double longitude;

    public BusStop(String agency, int stopId, double latitude, double longitude)
                          throws IOException, org.xml.sax.SAXException {
        API api = new API(agency);
        for (PredictionsInfo p : api.getPredictions(stopId))
            predictions.put(new Route(p.title, p.direction), p.times);
        this.latitude = latitude;
        this.longitude = longitude;
        allStops.add(this);
    }

    /**
     * Calculates the great-circle distance between two points using the
     * Haversine forumla.
     *
     * @return  distance in meters
     */
    private double distance(int lat1, int long1, int lat2, int long2) {
        int earthRadius = 6372797;
        double dlat = Math.toRadians(lat2-lat1);
        double dlong = Math.toRadians(long2-long1);
        double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dlong/2) * Math.sin(dlong/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = earthRadius * c;
        return d;
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
