public class PredictionsForMultiStops extends Predictions {
    private PredictionsRequest[] requests;

    public PredictionsForMultiStops(String agency,
                    PredictionsRequest[] requests) {
        super(agency, 0); // dummy stopId
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
}
