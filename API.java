/**
 * Interface for NextBus live XML feed API.
 * http://www.actransit.org/rider-info/nextbus-xml-data/
 * Author: Eugene Ma (github.com/edma2)
 */
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;

import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;

public class API {
    private final String URL = "http://webservices.nextbus.com/service/publicXMLFeed";

    public LinkedList<Route> getRoutes(int stopId) throws IOException, SAXException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("command", "predictions");
        params.put("a", "actransit");
        params.put("stopId", Integer.toString(stopId));
        String url = urlFromParams(params);
        XMLReader xr = XMLReaderFactory.createXMLReader();
        Parser parser = new Parser();
        xr.setContentHandler(parser);
        xr.setErrorHandler(parser);
        xr.parse(new InputSource(retrieve(url)));
        return parser.routes;
    }

    /**
     * Returns a URL string with appended query parameters.
     */
    private String urlFromParams(HashMap<String, String> params) {
        String url = URL + "?";
        for (String key : params.keySet()) {
            url = url + key + "=" + params.get(key) + "&";
        }
        return url;
    }

    /**
     * Returns the resource located at a the given URL with query string
     * parameters. The data received is assumed to be encoded as UTF-8 by
     * default, and a Reader pointing to it is returned.
     */
    private Reader retrieve(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.connect();
        return new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
    }

    public static void main(String[] args) {
        API api = new API();
        try {
            for (Route r : api.getRoutes(57776)) {
                System.out.println(r.title + "->" + r.direction);
                for (int seconds : r.predictions)
                    System.out.println(seconds + " seconds left");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
