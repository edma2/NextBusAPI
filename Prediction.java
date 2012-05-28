import java.util.List;
import java.util.LinkedList;

public class Prediction {
    String routeTag;
    String stopTag;
    String dirTag;
    List<Integer> arrivalTimes = new LinkedList<Integer>();

    public Prediction(String routeTag, String stopTag, String dirTag) {
        this.routeTag = routeTag;
        this.stopTag = stopTag;
        this.dirTag = dirTag;
    }
}
