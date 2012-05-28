class PredictionsRequest {
    String stopTag;
    String routeTag;
    String dirTag;

    PredictionsRequest(String stopTag, String routeTag, String dirTag) {
        this.stopTag = stopTag;
        this.routeTag = routeTag;
        this.dirTag = dirTag;
    }
}
