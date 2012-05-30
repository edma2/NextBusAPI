package ma.eugene.nextbus;

public class Prediction {
    public String routeTitle;
    public String stopTitle;
    public String dirTitle;
    public int seconds;

    public Prediction(String routeTitle, String stopTitle, String dirTitle,
                            int seconds) {
        this.routeTitle = routeTitle;
        this.stopTitle = stopTitle;
        this.dirTitle = dirTitle;
        this.seconds = seconds;
    }
}
