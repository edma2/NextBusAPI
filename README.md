NextBusAPI
----------------
Example: find all bus arrivals within 300 meters.

```java
public static void main(String[] args) {
    try {
        NextBus nb = new NextBus("actransit");
        for (Prediction p : nb.getPredictionsInRange(37.873464, -122.271481, 300))
            System.out.println(p);
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (SAXException ex1) {
        ex1.printStackTrace();
    }
}
```
