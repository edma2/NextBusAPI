package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashSet;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        HashSet<Integer> stopIds = new HashSet<Integer>();
        API api = new API("actransit");
        try {
            for (PredictionsInfo prediction: api.getPredictions(54080))
                System.out.println(prediction.title + '-' + prediction.direction);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
