package ma.eugene.nextbus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            NextBus nb = new NextBus("actransit");
            for (Prediction p : nb.predictionsWithinRange(37.873464, -122.271481, 300))
                System.out.println(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
