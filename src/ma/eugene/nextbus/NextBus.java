package ma.eugene.nextbus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.SAXException;

public abstract class NextBus {
    /** Live XML feed API. */
    private API api;

    /** For mapping stopIds to unique stops. */
    private HashMap<Integer, BusStop> stops = new HashMap<Integer, BusStop>();

    /** Concrete implementation returns distance to stop in meters. */
    protected abstract float stopDistance(BusStop bs);

    public NextBus(String agency) throws IOException, SAXException {
        api = new API(agency);
        collectStops(); /* might take some time */
    }

    public NextBus(String agency, String s) {
        api = new API(agency);
        for (String line : s.split("\n")) {
            BusStop bs = new BusStop(api, line);
            stops.put(bs.getStopId(), bs);
        }
    }

    private void collectStops() throws IOException, SAXException {
        for (String route : api.getRouteList()) {
            for (RouteConfigInfo info : api.getRouteConfig(route)) {
                BusStop stop = new BusStop(api, info);
                stops.put(stop.getStopId(), stop);
            }
        }
    }

    public List<BusStop> getStopsInRange(int radius) {
        List<BusStop> results = new LinkedList<BusStop>();
        for (BusStop bs : stops.values()) {
            if (stopDistance(bs) < radius)
                results.add(bs);
        }
        return results;
    }

    public List<Prediction> getPredictionsInRange(int radius)
                                    throws IOException, SAXException {
        List<Prediction> predictions = new LinkedList<Prediction>();
        for (BusStop bs : getStopsInRange(radius)) {
            for (Prediction pred : bs.getPredictions())
                predictions.add(pred);
        }
        return predictions;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BusStop bs : stops.values())
            sb.append(bs + "\n");
        if (sb.charAt(sb.length()-1) == '\n')
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
