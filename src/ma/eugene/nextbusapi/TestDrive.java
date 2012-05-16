package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashMap;

import org.xml.sax.SAXException;

public class TestDrive {
    /*
    public static void main(String[] args) {
        try {
            API api = new API("actransit");
            for (RouteConfigInfo stop : api.getRouteConfig("B")) {
                System.out.println(stop.stopId);
                System.out.println(stop.latitude);
                System.out.println(stop.longitude);
            }
            //BusStop stop = new BusStop("actransit", 54080, 1337, 1337);
            //System.out.println(stop);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }*/

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
}
