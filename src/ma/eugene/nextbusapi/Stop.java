package ma.eugene.nextbusapi;

public class Stop {
    public String title;
    public int stopId;
    public float longitude;
    public float latitude;

    public Stop(String title, int stopId, float longitude, float latitude) {
        this.title = title;
        this.stopId = stopId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
