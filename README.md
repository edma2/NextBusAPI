NextBusAPI
----------------
Example: find all bus arrivals within 300 meters.

```java
package ma.eugene.nextbus;

import java.io.IOException;
import org.xml.sax.SAXException;

public class TestDrive {
    public static void main(String[] args) {
        try {
            NextBus nb = new NextBus("actransit") {
                @Override
                protected float stopDistance(BusStop bs) {
                    return distance(bs.getLatitude(), bs.getLongitude(),
                            37.873464, -122.271481);
                }

                /**
                 * Calculates the great-circle distance between two points
                 * using the Haversine forumla.
                 *
                 * @return  distance in meters
                 */
                private float distance(double lat1, double long1, double lat2,
                        double long2) {
                    int earthRadius = 6372797;
                    double dlat = Math.toRadians(lat2-lat1);
                    double dlong = Math.toRadians(long2-long1);
                    double a = Math.sin(dlat/2) * Math.sin(dlat/2) +
                        Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) * Math.sin(dlong/2) *
                        Math.sin(dlong/2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double d = earthRadius * c;
                    return (float)d;
                }
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
```
