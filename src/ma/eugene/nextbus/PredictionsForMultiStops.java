package ma.eugene.nextbus;

import java.util.List;
import java.util.LinkedList;

public class PredictionsForMultiStops extends Predictions {
    private List<Request> requests = new LinkedList<Request>();

    private class Request {
        Route route;
        Direction dir;
        Stop stop;

        Request(Route route, Direction dir, Stop stop) {
            this.route = route;
            this.dir = dir;
            this.stop = stop;
        }
    }

    public PredictionsForMultiStops(Agency agency) {
        super(agency, 0); // dummy stopId
    }

    public void addRequest(Route route, Direction dir, Stop stop) {
        Request r = new Request(route, dir, stop);
        requests.add(r);
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=predictionsForMultiStops");
        sb.append("&a=" + getAgency().tag);
        for (Request r : requests) {
            sb.append("&stops=");
            sb.append(r.route.tag + "|");
            sb.append(r.dir.tag + "|");
            sb.append(r.stop.tag);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Agency agency = new Agency("actransit", "AC Transit");
        PredictionsForMultiStops command =
            new PredictionsForMultiStops(agency);
        command.addRequest(new Route("1", "1"), new Direction("null", "?"),
                new Stop("1008290", "A + B", 0, 0));
        command.addRequest(new Route("1", "1"), new Direction("null", "?"),
                new Stop("0301545", "C + D", 0, 0));
        command.execute();
        for (Prediction p : command.getPredictions()) {
            for (int i : p.getArrivalTimes())
                System.out.println(i);
        }
    }
}
