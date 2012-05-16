/**
 * Bus stop state.
 * Knows prediction information for each incoming route in either direction.
 */
package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public class BusStop {
    private String agency;
    private int stopId;
    private double latitude;
    private double longitude;

    public BusStop(String agency, int stopId, double latitude,
            double longitude) {
        this.agency = agency;
        this.stopId = stopId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public List<PredictionsInfo> getPredictions()
                        throws IOException, org.xml.sax.SAXException{
        API api = new API(agency);
        return api.getPredictions(stopId);
    }
}
