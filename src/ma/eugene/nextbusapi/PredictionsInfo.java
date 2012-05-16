package ma.eugene.nextbusapi;

import java.util.LinkedList;

/*
 * Information about an incoming bus servicing the specified direction and
 * route.
 */

public class PredictionsInfo {
    public String direction;
    public String title;
    public LinkedList<Integer> predictions = new LinkedList<Integer>();

    public PredictionsInfo(String direction, String title) {
        this.direction = direction;
        this.title = title;
    }

    public void addPrediction(int seconds) {
        predictions.add(seconds);
    }
}
