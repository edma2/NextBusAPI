package ma.eugene.nextbus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            NextBus nb = new NextBus("actransit") {
                protected double getLatitude() { return 37.873464; }
                protected double getLongitude() { return -122.271481; }
            };
            for (Prediction p : nb.getPredictionsInRange(300))
                System.out.println(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
