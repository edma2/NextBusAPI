package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

public class Prediction {
    public String routeTitle;
    public String stopTitle;
    public String dirTitle;
    private List<Integer> arrivalTimes = new LinkedList<Integer>();

    public Prediction(String routeTitle, String stopTitle, String dirTitle) {
        this.routeTitle = routeTitle;
        this.stopTitle = stopTitle;
        this.dirTitle = dirTitle;
    }

    private int[] toIntArray(List<Integer> list){
        int[] ret = new int[list.size()];
        for (int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }

    public int[] getArrivalTimes() {
        return toIntArray(arrivalTimes);
    }

    public void addArrivalTime(int seconds) {
        arrivalTimes.add(seconds);
    }
}
