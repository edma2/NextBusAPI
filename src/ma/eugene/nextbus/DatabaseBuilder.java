package ma.eugene.nextbus;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Builds a database containing route, direction, and stop information for an
 * agency. If the database for the agency already exists, all tables are
 * dropped. The database is written to <AGENCYTAG>.db in the current working
 * directory.
 *
 * Use this class to create databases off-line. You can then select routes,
 * directions, and stops for making predictions without any network traffic.
 * Having all the stops locally preloaded also allows us to make richer
 * queries.
 */
public class DatabaseBuilder {
    /**
     * Each agency has its own database.
     * This field sets the name of the database.
     */
    private Agency agency;

    private Connection conn;

    /**
     * Routes (bus-lines) in use by this agency.
     * @param tag the unique tag of the route
     * @param title the human-readable title of the route
     */
    private static final String ROUTE_TABLE = "Route";
    private static final String CREATE_ROUTE_TABLE =
        "CREATE TABLE " + ROUTE_TABLE + "(" +
            "tag TEXT PRIMARY KEY," +
            "title TEXT);";

    /**
     * Directions in use by this agency.
     * @param tag the unique tag of the direction
     * @param title the human-readable title of the direction
     */
    private static final String DIRECTION_TABLE = "Direction";
    private static final String CREATE_DIRECTION_TABLE =
        "CREATE TABLE " + DIRECTION_TABLE + "(" +
            "tag TEXT PRIMARY KEY," +
            "title TEXT);";

    /**
     * Stops in use by this agency.
     * @param tag the unique tag of the stop
     * @param title the human-readable title of the stop
     */
    private static final String STOP_TABLE = "Stop";
    private static final String CREATE_STOP_TABLE =
        "CREATE TABLE " + STOP_TABLE + "(" +
            "tag TEXT PRIMARY KEY," +
            "title TEXT," +
            "latitude REAL," +
            "longitude REAL);";

    /**
     * Valid prediction requests.
     * A prediction request consists of three paramters: route, direction, and
     * stop. This table stores additional information. First, the stop number
     * for the given route and direction. Second, a field meant to be used as a
     * timestamp for the last time the prediction was made.
     */
    private static final String REQUEST_TABLE = "Request";
    private static final String CREATE_REQUEST_TABLE =
        "CREATE TABLE " + REQUEST_TABLE + "(" +
          "route_tag TEXT," +
          "dir_tag TEXT," +
          "stop_tag TEXT," +
          "stop_num INT," +
          "updated_at INTEGER," +
          "FOREIGN KEY (route_tag) REFERENCES " + ROUTE_TABLE + "(tag)" +
          "FOREIGN KEY (dir_tag) REFERENCES " + DIRECTION_TABLE + "(tag)" +
          "FOREIGN KEY (stop_tag) REFERENCES " + STOP_TABLE + "(tag)" +
          "PRIMARY KEY (route_tag, dir_tag, stop_tag));";

    /**
     * Default constructor.
     * Does not open any database connections.
     * @param agency the selected agency to use
     */
    public DatabaseBuilder(Agency agency) {
        this.agency = agency;
    }

    /**
     * The file name of this database.
     */
    private String dbName() {
        return agency.tag + ".db";
    }

    /**
     * The database URL as specified by the JDBC format.
     */
    private String getDatabaseURL() {
        String protocol = "jdbc";
        String subprotocol = "sqlite";
        String subname = dbName();
        return protocol + ":" + subprotocol + ":" + subname;
    }

    /**
     * Connect to the database.
     * This should be called before you do anything.
     */
    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(getDatabaseURL());
    }

    /**
     * Close the database connection.
     * This should be called when done.
     */
    private void disconnect() throws SQLException {
        conn.close();
    }

    /**
     * Creates necessary tables.
     * Drops old tables if they exist.
     */
    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS " + ROUTE_TABLE);
        stmt.executeUpdate("DROP TABLE IF EXISTS " + DIRECTION_TABLE);
        stmt.executeUpdate("DROP TABLE IF EXISTS " + STOP_TABLE);
        stmt.executeUpdate("DROP TABLE IF EXISTS " + REQUEST_TABLE);
        stmt.executeUpdate(CREATE_ROUTE_TABLE);
        stmt.executeUpdate(CREATE_DIRECTION_TABLE);
        stmt.executeUpdate(CREATE_STOP_TABLE);
        stmt.executeUpdate(CREATE_REQUEST_TABLE);
    }

    /**
     * Fetch information from NextBus and populate tables.
     * We force a timeout between each route to make sure NextBus doesn't
     * ban/block us.
     */
    private void populateTables() throws SQLException {
        /* This speeds up commits.
         * See: http://www.zentus.com/sqlitejdbc/usage.html */ 
        conn.setAutoCommit(false);
        RouteList rl = new RouteList(agency);
        rl.execute();
        for (Route route : rl.getRoutes()) {
            save(route);
            RouteConfig rc = new RouteConfig(agency, route);
            rc.execute();
            for (Direction dir : rc.getDirections()) {
                save(dir);
                int stop_num = 0;
                for (Stop stop : rc.getStops(dir)) {
                    save(stop);
                    Request req = new Request(route, dir, stop);
                    save(req, stop_num++);
                }
            }
            System.out.println("route: " + route.title); 
            /* Be nice to NextBus */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        conn.commit();
    }

    /**
     * Save the route into the database.
     * @param r the Route to save
     */
    private void save(Route r) throws SQLException {
        String q = "INSERT OR REPLACE INTO " + ROUTE_TABLE +
                    " VALUES(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setString(1, r.tag);
        stmt.setString(2, r.title);
        stmt.execute();
    }
    
    /**
     * Save the direction into the database.
     * @param d the Direction to save
     */
    private void save(Direction d) throws SQLException {
        String q = "INSERT OR REPLACE INTO " + DIRECTION_TABLE +
                    " VALUES(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setString(1, d.tag);
        stmt.setString(2, d.title);
        stmt.execute();
    }

    /**
     * Save the Stop into the database.
     * @param s the Stop to save
     */
    private void save(Stop s) throws SQLException {
        String q = "INSERT OR REPLACE INTO " + STOP_TABLE +
                    " VALUES(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setString(1, s.tag);
        stmt.setString(2, s.title);
        stmt.setDouble(3, s.latitude);
        stmt.setDouble(4, s.longitude);
        stmt.execute();
    }

    /**
     * Save the prediction request.
     * @param r the Request
     * @param n the stop number
     */
    private void save(Request r, int n) throws SQLException {
        String q = "INSERT OR REPLACE INTO " + REQUEST_TABLE +
                    " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setString(1, r.route.tag);
        stmt.setString(2, r.dir.tag);
        stmt.setString(3, r.stop.tag);
        stmt.setInt(4, n);
        stmt.setInt(5, 0);
        stmt.execute();
    }

    /**
     * Create a new database.
     * This is top-level method and the only public method exposed.
     * @throws SQLException bad SQL command
     * @throws ClassNotFoundException JDBC driver not found
     */
    public void run() throws SQLException, ClassNotFoundException{
        connect();
        createTables();
        populateTables();
        disconnect();
    }

    public static void main(String[] args) {
        Agency actransit = new Agency("actransit", "AC Transit");
        DatabaseBuilder builder = new DatabaseBuilder(actransit);
        try {
            builder.run();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex1) {
            ex1.printStackTrace();
        }
    }
}
