package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashMap;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            BusStop stop = new BusStop("actransit", 53511,37.7413699,-122.15662);
            System.out.println(stop.getPredictions());
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
