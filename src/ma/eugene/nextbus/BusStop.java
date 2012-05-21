package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.SAXException;
import org.json.JSONObject;
import org.json.JSONException;

public class BusStop {
    private API api;
    private RouteConfigInfo info;

    public BusStop(API api, RouteConfigInfo info) {
        this.api = api;
        this.info = info;
    }

    public BusStop(API api, JSONObject json) throws JSONException {
        this.api = api;
        RouteConfigInfo info =
            new RouteConfigInfo((String)json.get("title"),
                    (Integer)json.get("stopId"), (Double)json.get("latitude"),
                    (Double)json.get("longitude"));
        this.info = info;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("stopId", getStopId());
        json.put("title", getTitle());
        json.put("latitude", getLatitude());
        json.put("longitude", getLongitude());
        return json;
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

    public List<Prediction> getPredictions() throws IOException, SAXException {
        List<Prediction> predictions = new LinkedList<Prediction>();
        for (PredictionsInfo info : api.getPredictions(getStopId()))
            predictions.add(new Prediction(this, info));
        return predictions;
    }
}
