
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author postgresqltutorial.com
 */

public class test {
    
    private final String url = "jdbc:postgresql://localhost/dvdrental";
    private final String user = "postgres";
    private final String password = "admin";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     * @throws java.sql.SQLException
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    /**
     * Get all rows in the actor table
     */
    public void getActors() {

        String SQL = "SELECT actor_id,first_name, last_name FROM actor";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            // display actor information
            displayActor(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Get actors count
     * @return 
     */
    public int getActorCount() {
        String SQL = "SELECT count(*) FROM actor";
        int count = 0;

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }

    /**
     * Display actor
     *
     * @param rs
     * @throws SQLException
     */
    private void displayActor(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("actor_id") + "\t"
                    + rs.getString("first_name") + "\t"
                    + rs.getString("last_name"));

        }
    }

    /**
     * Find actor by his/her ID
     *
     * @param actorID
     */
    public void findActorByID(int actorID) {
        String SQL = "SELECT actor_id,first_name,last_name "
                + "FROM actor "
                + "WHERE actor_id = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, actorID);
            ResultSet rs = pstmt.executeQuery();
            displayActor(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test main = new test();
        main.findActorByID(200);
        // main.getActors();
    }

}
