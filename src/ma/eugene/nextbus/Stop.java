package ma.eugene.nextbus;

public class Stop extends TaggedTitle {
    public double latitude;
    public double longitude;
    // TODO: stopId

    public Stop(String tag, String title, double latitude, double longitude) {
        super(tag, title);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
