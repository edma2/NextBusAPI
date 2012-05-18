NextBusAPI
----------------
Example: find all bus arrivals within 500 meters of my location.

```java
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
```
