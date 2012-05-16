package ma.eugene.nextbusapi;

/*
 * Information about a stop.
 */
public class RouteConfigInfo {
    public String title;
    public int stopId;
    public float longitude;
    public float latitude;

    public RouteConfigInfo(String title, int stopId, float longitude, float latitude) {
        this.title = title;
        this.stopId = stopId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
