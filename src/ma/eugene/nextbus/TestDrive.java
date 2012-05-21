package ma.eugene.nextbus;

import java.io.IOException;
import java.io.File;

import org.xml.sax.SAXException;
import org.json.JSONException;

public class TestDrive extends NextBus {
    private double latitude = 37.873464;
    private double longitude = -122.271481;

    public TestDrive(String agency) {
        super(agency);
    }

    @Override
    protected float distanceToStop(BusStop bs) {
        return distance(bs.getLatitude(), bs.getLongitude(), latitude,
                longitude);
    }

    /**
     * Calculates the great-circle distance between two points
     * using the Haversine forumla.
     *
     * @return  distance in meters
     */
    private float distance(double lat1, double long1, double lat2,
            double long2) {
        int earthRadius = 6372797;
        double dlat = Math.toRadians(lat2-lat1);
        double dlong = Math.toRadians(long2-long1);
        double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
            Math.cos(Math.toRadians(lat1)) *
            Math.cos(Math.toRadians(lat2)) * Math.sin(dlong/2) *
            Math.sin(dlong/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = earthRadius * c;
        return (float)d;
    }

    public static void main(String[] args) {
        try {
            File f = new File("td1.json");

            TestDrive td1 = new TestDrive("actransit");
            td1.fetchStops();
            td1.saveState(f);

            TestDrive td2 = new TestDrive("blah");
            td2.loadState(f);
            System.out.println(td2);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        } catch (JSONException ex2) {
            ex2.printStackTrace();
        }
    }
}
