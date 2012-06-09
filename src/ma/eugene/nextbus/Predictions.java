package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

import org.xml.sax.Attributes;

public class Predictions extends Command {
    protected List<Prediction> predictions = new LinkedList<Prediction>();
    private String stopId;

    public Predictions(Agency agency, String stopId) {
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
        sb.append("&a=" + enc(getAgency().tag));
        sb.append("&stopId=" + stopId);
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            String routeTitle = "";
            String stopTitle = "";
            String dirTitle = "";

            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body")) {
                    predictions.clear();
                } else if (tag.equals("predictions")) {
                    routeTitle = attributes.getValue("routeTitle");
                    stopTitle = attributes.getValue("stopTitle");
                } else if (tag.equals("direction")) {
                    dirTitle = attributes.getValue("title");
                } else if (tag.equals("prediction")) {
                    int seconds = Integer.parseInt(
                            attributes.getValue("seconds"));
                    predictions.add(new Prediction(routeTitle, stopTitle,
                                dirTitle, seconds));
                }
            }
        };
    }

    public static void main(String[] args) {
        Agency mbta = new Agency("mbta", "MBTA");
        Predictions command = new Predictions(mbta, "00731");
        command.execute();
        for (Prediction p : command.getPredictions()) {
            System.out.println("----------------------");
            System.out.println(p.routeTitle);
            System.out.println(p.dirTitle);
            System.out.println(p.stopTitle);
            System.out.println(p.seconds);
            System.out.println("----------------------");
        }
    }
}
