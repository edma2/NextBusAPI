import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Predictions extends Command {
    private List<Integer> times = new LinkedList<Integer>();
    private String routeTag;
    private String dirTag;
    private String stopTag;

    public Predictions(String agency, String routeTag, String dirTag,
                            String stopTag) {
        super(agency);
        this.routeTag = routeTag;
        this.dirTag = dirTag;
        this.stopTag = stopTag;
    }

    public int[] getTimes() {
        return toIntArray(times);
    }

    private int[] toIntArray(List<Integer> list){
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(super.getURL());
        sb.append("?command=predictions");
        sb.append("&a=" + getAgency());
        sb.append("&r=" + routeTag);
        sb.append("&d=" + dirTag);
        sb.append("&s=" + stopTag);
        return sb.toString();
    }

    @Override
    protected NextBusHandler getHandler() {
        return new NextBusHandler() {
            private String dir = "";

            public void handleElement(String tag, Attributes attributes) {
                if (tag.equals("body")) {
                    times.clear();
                } else if (tag.equals("prediction")) {
                    int minutes =
                        Integer.parseInt(attributes.getValue("minutes"));
                    times.add(minutes);
                }
            }
        };
    }
}
