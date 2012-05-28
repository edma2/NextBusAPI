import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Predictions extends Command {
    private List<Prediction> predictions = new LinkedList<Prediction>();
    private int stopId;

    public Predictions(String agency, int stopId) {
        super(agency);
        this.stopId = stopId;
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

    public static void main(String[] args) {
        Predictions preds = new Predictions("actransit", 58558);
        preds.execute();
        for (Prediction p : preds.predictions) {
            System.out.println("new Prediction!");
            System.out.println(p.routeTag);
            System.out.println(p.stopTag);
            System.out.println(p.dirTag);
            System.out.println(p.arrivalTimes);
        }
    }
}
