import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class Predictions extends Command {
    protected List<Prediction> predictions =
                new LinkedList<Prediction>();
    private int stopId;

    public Predictions(String agency, int stopId) {
        super(agency);
        this.stopId = stopId;
    }

    public Prediction[] getPredictions() {
        return predictions.toArray(new Prediction[0]);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=predictions");
        sb.append("&a=" + getAgency());
        sb.append("&stopId=" + stopId);
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            String routeTag = "";
            String stopTag = "";
            Prediction p = null;

            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body")) {
                    predictions.clear();
                } else if (tag.equals("predictions")) {
                    routeTag = attributes.getValue("routeTag");
                    stopTag = attributes.getValue("stopTag");
                } else if (tag.equals("direction")) {
                    p = null;
                } else if (tag.equals("prediction")) {
                    if (p == null) {
                        String dirTag = attributes.getValue("dirTag");
                        p = new Prediction(routeTag, stopTag, dirTag);
                        predictions.add(p);
                    }
                    int seconds =
                        Integer.parseInt(attributes.getValue("seconds"));
                    p.arrivalTimes.add(seconds);
                }
            }
        };
    }
}
