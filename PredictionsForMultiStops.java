import java.util.List;
import java.util.LinkedList;

public class PredictionsForMultiStops extends Predictions {
    private List<Request> requests = new LinkedList<Request>();

    private class Request {
        String routeTag;
        String dirTag;
        String stopTag;

        Request(String routeTag, String dirTag, String stopTag) {
            this.routeTag = routeTag;
            this.dirTag = dirTag;
            this.stopTag = stopTag;
        }
    }

    public PredictionsForMultiStops(String agency) {
        super(agency, 0); // dummy stopId
    }

    public void addRequest(String routeTag, String dirTag, String stopTag) {
        Request r = new Request(routeTag, dirTag, stopTag);
        requests.add(r);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=predictionsForMultiStops");
        sb.append("&a=" + getAgency());
        for (Request r : requests) {
            sb.append("&stops=");
            sb.append(r.routeTag + "|");
            sb.append(r.dirTag + "|");
            sb.append(r.stopTag);
        }
        return sb.toString();
    }
}
