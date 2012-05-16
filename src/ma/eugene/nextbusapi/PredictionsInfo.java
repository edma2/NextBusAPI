package ma.eugene.nextbusapi;

import java.util.LinkedList;

/*
 * Predictions for a given route and direction at a stop.
 */
public class PredictionsInfo {
    public String direction;
    public String title;
    public LinkedList<Integer> times = new LinkedList<Integer>();

    public PredictionsInfo(String direction, String title) {
        this.direction = direction;
        this.title = title;
    }

    public void addTime(int seconds) {
        times.add(seconds);
    }
}
