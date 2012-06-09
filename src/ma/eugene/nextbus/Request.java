package ma.eugene.nextbus;

public class Request {
    public Route route;
    public Direction dir;
    public Stop stop;

    public Request(Route route, Direction dir, Stop stop) {
        this.route = route;
        this.dir = dir;
        this.stop = stop;
    }
}
