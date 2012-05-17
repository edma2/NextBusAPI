package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            NextBus nb = new NextBus("actransit");
            for (BusStop bs : nb.stopsWithinRange(37.873464, -122.271481, 300))
                System.out.println(bs);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
        HashMap<Integer, String> stops = new HashMap<Integer, String>();
        API api = new API("actransit");
        try {
            for (String route : api.getRouteList()) {
                for (RouteConfigInfo stop : api.getRouteConfig(route))
                    stops.put(stop.stopId, Double.toString(stop.latitude) + ","
                            + Double.toString(stop.longitude));
            }
            for (int stopId : stops.keySet())
                System.out.println(stopId + "," + stops.get(stopId));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
    */
}
