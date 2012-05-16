package ma.eugene.nextbusapi;

import java.io.IOException;
import java.util.HashSet;

import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            BusStop stop = new BusStop("actransit", 54080, 1337, 1337);
            System.out.println(stop);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex1) {
            ex1.printStackTrace();
        }
    }
}
