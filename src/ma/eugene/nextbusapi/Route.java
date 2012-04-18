package ma.eugene.nextbusapi;

import java.util.LinkedList;

public class Route {
    public String direction;
    public String title;
    public LinkedList<Integer> predictions = new LinkedList<Integer>();

    public Route(String direction, String title) {
        this.direction = direction;
        this.title = title;
    }

    public void addPrediction(int seconds) {
        predictions.add(seconds);
    }
}
