package ma.eugene.nextbus;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import org.xml.sax.SAXException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public abstract class NextBus {
    /** Live XML feed API. */
    private API api;

    /**
     * All BusStops.
     * Protected access to this data is provided with getStops() and addStop().
     */
    private HashMap<Integer, BusStop> busStops =
                        new HashMap<Integer, BusStop>();

    /**
     * Get all BusStops as an Array.
     *
     * @return array of BusStops
     */
    protected BusStop[] getStops() {
        return busStops.values().toArray(new BusStop[0]);
    }

    /**
     * Add a BusStop, or replace an existing BusStop with the same stopId.
     *
     * @param bs the BusStop
     */
    protected void addStop(BusStop bs) {
        busStops.put(bs.getStopId(), bs);
    }

    /**
     * Computes the distance to a given BusStop.
     * The implementation of this function is left up to the subclass. For
     * instance, Android provides nice ways to do this with Locations.
     *
     * @param bs the BusStop
     * @return the distance in meters
     */
    protected abstract float distanceToStop(BusStop bs);

    /**
     * Default constructor.
     * 
     * @param agency the String identifying the NextBus agency
     */
    public NextBus(String agency) {
        api = new API(agency);
    }

    /**
     * File constructor.
     * Use this method to instantiate a new NextBus instance from a JSON file.
     *
     * @param file the file containing whose contents is JSON source string
     */
    public NextBus(File file) throws JSONException, IOException {
        String source = readFromFile(file);
        JSONObject json = new JSONObject(source);
        api = new API((String)json.get("agency"));
        JSONArray jsonStops = (JSONArray)json.get("stops");
        for (int i = 0; i < jsonStops.length(); i++) {
            BusStop bs = new BusStop(api, jsonStops.getJSONObject(i));
            addStop(bs);
        }
    }

    /**
     * Get file contents represented as a String.
     *
     * @param file the source File
     * @return the String representing file contents
     */
    private String readFromFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(file));
        try {
            char[] buf = new char[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(buf)) != -1)
                sb.append(buf, 0, bytesRead);
        } finally {
            in.close();
        }
        return sb.toString();
    }

    /**
     * The String representation is the JSON text.
     * Use this method to export this as a String, possibly writing it to a
     * file. On error, returns the empty string.
     *
     * @return the JSON String representing this
     */
    public String toString() {
        try {
            return toJSON().toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Converts NextBus to JSONObject.
     *
     * @return the corresponding JSONObject
     */
    private JSONObject toJSON() throws JSONException {
        JSONArray stops = new JSONArray();
        for (BusStop bs : getStops())
            stops.put(bs.toJSON());
        JSONObject json = new JSONObject();
        json.put("agency", api.getAgency());
        json.put("stops", stops);
        return json;
    }

    /**
     * Save the state to a JSON file.
     *
     * @param file the File to be written to
     */
    public void saveState(File file) throws IOException, JSONException {
        writeToFile(file, toString());
    }

    /**
     * Write the String to the file.
     *
     * @param file the File to be written to
     * @param s the String to be written.
     */
    private void writeToFile(File file, String s) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        try {
            out.write(s, 0, s.length());
        } finally {
            out.close();
        }
    }

    /**
     * Query the NextBus server for stops.
     * This is slow and expensive because it requires sending many HTTP
     * requests to the NextBus server.
     */
    public void fetchStops() throws IOException, SAXException {
        for (String route : api.getRouteList()) {
            for (RouteConfigInfo info : api.getRouteConfig(route)) {
                BusStop bs = new BusStop(api, info);
                addStop(bs);
            }
        }
    }

    /**
     * All BusStops within range.
     *
     * @param radius distance in meters
     * @return all BusStops within range in meters.
     */
    public List<BusStop> getStopsInRange(int radius) {
        List<BusStop> results = new LinkedList<BusStop>();
        for (BusStop bs : getStops()) {
            if (distanceToStop(bs) < radius)
                results.add(bs);
        }
        return results;
    }

    /**
     * All Predictions within radius.
     *
     * @param radius distance in meters
     * @return all Predictions arriving at stops within range in meters.
     */
    public List<Prediction> getPredictionsInRange(int radius)
                            throws IOException, SAXException {
        List<Prediction> predictions = new LinkedList<Prediction>();
        for (BusStop bs : getStopsInRange(radius)) {
            for (Prediction pred : bs.getPredictions())
                predictions.add(pred);
        }
        return predictions;
    }
}
