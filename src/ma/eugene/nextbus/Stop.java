package ma.eugene.nextbus;

public class Stop {
    public String title;
    public String tag;
    public double latitude;
    public double longitude;
    // TODO: stopId

    public Stop() {
    }

    public Stop(String tag, String title, double latitude, double longitude) {
        this.tag = tag;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
