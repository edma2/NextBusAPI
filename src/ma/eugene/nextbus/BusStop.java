/**
 * Bus stop state.
 * Knows prediction information for each incoming route in either direction.
 */
package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public class BusStop {
    private API api;
    private RouteConfigInfo info;

    public BusStop(API api, RouteConfigInfo info) {
        this.api = api;
        this.info = info;
    }

    public int getStopId() {
        return info.stopId;
    }

    public String getTitle() {
        return info.title;
    }

    public double getLatitude() {
        return info.latitude;
    }

    public double getLongitude() {
        return info.longitude;
    }

    public String toString() {
        return getTitle() + "(" + getStopId() + ")";
    }

    public List<PredictionsInfo> getPredictions()
                        throws IOException, SAXException {
        return api.getPredictions(getStopId());
    }
}
