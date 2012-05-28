Example Usage
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
