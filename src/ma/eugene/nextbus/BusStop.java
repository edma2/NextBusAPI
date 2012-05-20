/**
 * Bus stop state.
 * Knows prediction information for each incoming route in either direction.
 */
package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.SAXException;

public class BusStop {
    private API api;
    private RouteConfigInfo info;

    public BusStop(API api, RouteConfigInfo info) {
        this.api = api;
        this.info = info;
    }

    /**
     * @param s 53819|Meekland and A St|37.66703|-122.09905
     */
    public BusStop(API api, String s) {
        this.api = api;
        this.info = new RouteConfigInfo(s);
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
        return info.toString();
    }

    public List<Prediction> getPredictions() throws IOException, SAXException {
        List<Prediction> predictions = new LinkedList<Prediction>();
        for (PredictionsInfo info : api.getPredictions(getStopId()))
            predictions.add(new Prediction(this, info));
        return predictions;
    }
}
