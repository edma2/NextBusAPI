package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public class Prediction {
    BusStop bs;
    private PredictionsInfo info;

    public Prediction(BusStop bs, PredictionsInfo info) {
        this.bs = bs;
        this.info = info;
    }

    public String getDirection() {
        return info.direction;
    }

    public String getTitle() {
        return info.title;
    }

    public int getStopId() {
        return bs.getStopId();
    }

    public String getStopTitle() {
        return bs.getTitle();
    }

    private List<Integer> getTimes() {
        return info.times;
    }

    private <T> String join(String sep, T[] items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i]);
            if (i < items.length-1)
                sb.append(sep);
        }
        return sb.toString();
    }

    public String getTimesAsString() {
        return join(", ", getTimesInMinutes());
    }

    private Integer[] getTimesInMinutes() {
        Integer[] times = new Integer[getTimes().size()];
        int i = 0;
        for(int seconds : getTimes())
            times[i++] = seconds/60;
        return times;
    }
}
