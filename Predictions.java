import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Predictions extends Command {
    private int stopId;
    private String routeTag = null;
    // Direction -> [ETAs]
    private Map<String, List<Integer>> predictions =
            new HashMap<String, List<Integer>>();

    public Predictions(String agency, int stopId) {
        super(agency);
        this.stopId = stopId;
    }

    public Predictions(String agency, String routeTag, int stopId) {
        this(agency, stopId);
        this.routeTag = routeTag;
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(super.getURL());
        sb.append("?command=predictions");
        sb.append("&a=" + getAgency());
        sb.append("&stopId=" + stopId);
        if (routeTag != null)
            sb.append("&routeTag=" + routeTag);
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            private String dir = "";

            public void startElement(
                    java.lang.String uri,
                    java.lang.String localName,
                    java.lang.String qName,
                    Attributes attributes) throws SAXException {
                String tag = qName;
                if (tag.equals("body")) {
                    predictions.clear();
                } else if (tag.equals("direction")) {
                    dir = attributes.getValue("direction");
                    predictions.put(dir, new LinkedList<Integer>());
                } else if (tag.equals("prediction")) {
                    int minutes =
                        Integer.parseInt(attributes.getValue("minutes"));
                    predictions.get(dir).add(minutes);
                }
            }
        };
    }
}
