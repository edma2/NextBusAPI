public class PredictionsForMultiStops extends Predictions {
    private PredictionsRequest[] requests;

    public PredictionsForMultiStops(String agency,
                    PredictionsRequest[] requests) {
        super(agency, 0);
        this.requests = requests;
    }

    @Override
    protected String getURL() {
        StringBuilder sb = new StringBuilder(baseURL());
        sb.append("?command=predictionsForMultiStops");
        sb.append("&a=" + getAgency());
        for (PredictionsRequest request : requests) {
            sb.append("&stops=");
            sb.append(request.routeTag + "|");
            sb.append(request.dirTag + "|");
            sb.append(request.stopTag);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        PredictionsRequest r1 =
                new PredictionsRequest("0301545", "1", "null");
        PredictionsRequest r2 =
                new PredictionsRequest("1008290", "1", "null");
        PredictionsRequest[] requests = {r1, r2};

        PredictionsForMultiStops multi =
            new PredictionsForMultiStops("actransit", requests);
        multi.execute();
        for (Prediction p : multi.predictions) {
            System.out.println("new Prediction!");
            System.out.println(p.routeTag);
            System.out.println(p.stopTag);
            System.out.println(p.dirTag);
            System.out.println(p.arrivalTimes);
        }
    }
}
