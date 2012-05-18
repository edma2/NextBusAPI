package ma.eugene.nextbus;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

public class Prediction {
    private BusStop bs;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int time : getTimes())
            sb.append(time);
        sb.append("]");
        return sb.toString();
    }

    public List<Integer> getTimes() {
        return info.times;
    }
}