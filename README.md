NextBusAPI
----------------
Example: print out all stopIds used by AC Transit

```java
public static void main(String[] args) {
    HashSet<Integer> stopIds = new HashSet<Integer>();
    API api = new API("actransit");

    try {
        for (String route : api.getRouteList()) {
            for (RouteConfigInfo stop : api.getRouteConfig(route))
                stopIds.add(stop.stopId);
        }
        for (int stopId : stopIds) {
            System.out.println(stopId);
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (SAXException ex1) {
        ex1.printStackTrace();
    }
}
```
