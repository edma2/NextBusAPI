NextBusAPI
----------------
Example: find all stops within 500 meters of a coordinate.

```java
public static void main(String[] args) {
    try {
        NextBus nb = new NextBus("actransit");
        for (BusStop bs : nb.stopsWithinRange(37.873464, -122.271481, 300))
            System.out.println(bs);
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (SAXException ex1) {
        ex1.printStackTrace();
    }
}
```
