Usage Examples
===============
```java
RouteList command = new RouteList("actransit");
command.execute();
for (String routeTag : command.getRouteTags())
    System.out.println(routeTag);
```

```java
RouteConfig command = new RouteConfig("actransit", "51B");
command.execute();
for (Direction d : command.getDirections()) {
    System.out.println(d.title);
    for (Stop s : command.getStops(d))
        System.out.println(s.title);
}
```

```java
Predictions command = new Predictions("actransit", 52223);
command.execute();
for (Prediction p : command.getPredictions())
    System.out.println(p.getArrivalTimes());
```

```java
PredictionsForMultiStops command = new
    PredictionsForMultiStops("actransit");
command.addRequest("1", "null", "0301545");
command.addRequest("1", "null", "1008290");
command.execute();
for (Prediction p : command.getPredictions())
    System.out.println(p.getArrivalTimes());
```
