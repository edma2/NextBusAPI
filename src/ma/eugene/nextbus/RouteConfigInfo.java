package ma.eugene.nextbus;

/*
 * Information about a stop.
 */
public class RouteConfigInfo {
    public String title;
    public int stopId;
    public double longitude;
    public double latitude;

    public RouteConfigInfo(String title, int stopId, double longitude, double latitude) {
        this.title = title;
        this.stopId = stopId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * @param s 53819|Meekland and A St|37.66703|-122.09905
     */
    public RouteConfigInfo(String s) {
        String[] fields = s.split("\\|");
        this.stopId = Integer.parseInt(fields[0]);
        this.title = fields[1];
        this.latitude = Double.parseDouble(fields[2]);
        this.longitude = Double.parseDouble(fields[3]);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stopId + "|");
        sb.append(title + "|");
        sb.append(latitude + "|");
        sb.append(longitude);
        return sb.toString();
    }
}
