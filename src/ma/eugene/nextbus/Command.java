package ma.eugene.nextbus;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;

import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;

public abstract class Command {
    private final String URL =
        "http://webservices.nextbus.com/service/publicXMLFeed";
    private String agency;

    public Command(String agency) {
        this.agency = agency;
    }

    /**
     * TODO: write custom Exception class
     */
    public void execute() {
        try {
            parseXML(getXML(getURL()));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }

    protected abstract NextBusHandler getHandler();
    protected abstract String getURL();

    protected String getAgency() { return agency; }
    protected String baseURL() { return URL; }

    private void parseXML(Reader source) throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(getHandler());
        reader.parse(new InputSource(source));
    }

    /**
     * Returns the resource located at a the given URL with query string
     * parameters. The data received is assumed to be encoded as UTF-8 by
     * default, and a Reader pointing to it is returned.
     */
    private Reader getXML(String url) throws IOException {
        HttpURLConnection conn =
                (HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.connect();
        return new BufferedReader(new InputStreamReader(conn.getInputStream(),
                    "UTF8"));
    }
}
