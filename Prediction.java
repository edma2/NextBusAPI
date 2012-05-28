import java.util.List;
import java.util.LinkedList;

public class Prediction {
    String routeTitle;
    String stopTitle;
    String dirTitle;
    List<Integer> arrivalTimes = new LinkedList<Integer>();

    public Prediction(String routeTitle, String stopTitle, String dirTitle) {
        this.routeTitle = routeTitle;
        this.stopTitle = stopTitle;
        this.dirTitle = dirTitle;
    }
}
