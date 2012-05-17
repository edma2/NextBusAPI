/**
 * Bus stop state.
 * Knows prediction information for each incoming route in either direction.
 */
package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public class BusStop {
    private String agency;
    private int stopId;
    private String title;
    public double latitude;
    public double longitude;

    public BusStop(String agency, String title, int stopId, double latitude,
            double longitude) {
        this.agency = agency;
        this.title = title;
        this.stopId = stopId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        return title;
    }

    public List<PredictionsInfo> getPredictions()
                        throws IOException, SAXException {
        API api = new API(agency);
        return api.getPredictions(stopId);
    }
}
