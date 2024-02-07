
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/*import java.sql.ResultSet;*/
import java.sql.Statement;

/**
 *
 * @author postgresqltutorial.com
 */
public class Database{

    

    public  Database () {

       
    }

    

        // Check if Database Exist

    public  void CheckDatabase() {

        try (Connection conn = connect.connect1())  {

                // Check if the database exists
                if (databaseExists(conn , "sysapp")) {

                    System.out.println("The database already exists.");

                } else {
                    
                    createDatabase(connect.connect1());
                    System.out.println("Created sysApp database Successfully!");
                    createTable();

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
          
    }

    public void Execute(Connection conn,  String SQL) {
      
        try (
         PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

             pstmt.executeQuery();
            // check the affected rows 
            
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    public boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        // Use a prepared statement to query for the existence of the database
        String query = "SELECT 1 FROM pg_database WHERE datname = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, databaseName);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the database exists, false otherwise
            }
        }
    }

    public void createDatabase(Connection conn) {
        String SQL = "CREATE DATABASE sysApp";
               
        
                try ( 
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.executeQuery();
            // check the affected rows 
            
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

        


    

    public void createTable() {

        Connection conn = connect.connect2();

        String SQL = "CREATE TABLE IF NOT EXISTS Appareils"
                + "("
                +  "  id serial ,"
                +  "  serNum varchar(10) NOT NULL PRIMARY KEY,"
                +   " name varchar NOT NULL,"
                +   " type varchar NOT NULL,"
                +   " etatFonct varchar  NOT NULL"
                + ")";

        String SQL2 = "CREATE TABLE IF NOT EXISTS lectures"
                + "("
                +  "  id serial PRIMARY KEY,"
                +  "  serNum  varchar(10) NOT NULL,"
                +   " date_envoi date NOT NULL DEFAULT CURRENT_DATE,"
                +   " lecture1 numeric(5, 1) NOT NULL,"
                +   " lecture2 integer NOT NULL,"
                +   " CONSTRAINT serNum FOREIGN KEY (serNum)"
                +   " REFERENCES Appareils (serNum)"
                + ")";

        String SQL3 = "CREATE TABLE IF NOT EXISTS Utilisateurs"
                + "("
                +  "  name character varying NOT NULL,"
                +  "  password character varying NOT NULL,"
                +   " PRIMARY KEY (name, password)"
                + ")";

                      
            
        this.Execute(conn, SQL);
        this.Execute(conn, SQL2);
        this.Execute(conn, SQL3);
        
    }


        // Display Appareil list

    public void getAppareil() {

        Connection conn = connect.connect2();


        

        String SQL = "SELECT * FROM Appareils";

        try (
        Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
            // display actor information
            displayAppareil(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Display actor
     *
     * @param rs
     * @throws SQLException
     */
    private void displayAppareil(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("id") + "\t"
                    + rs.getString("sernum") + "\t"
                    + rs.getString("name") + "\t"
                    + rs.getString("type") + "\t"
                    + rs.getString("etatfonct"));

        }
    }

        // Insert Appareil Data

        public long insertvalues( Appareil appareil) {

           Connection conn = connect.connect2();

            String SQL = "INSERT INTO Appareils (\"sernum\" , \"name\"  , \"type\", \"etatfonct\") "
                    + "VALUES(?,?,?,?)";
    
            long id = 0;
    
            try (
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {
    
                pstmt.setString(1, appareil.serNum);
                pstmt.setString(2, appareil.name);
                pstmt.setString(3, appareil.type);
                pstmt.setString(4, appareil.etatFonct);
    
                int affectedRows = pstmt.executeUpdate();
                // check the affected rows 
                if (affectedRows > 0) {
                    // get the ID back
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return id;
        }

        //UPdate Data from an apparell

        public long updateValues( int id, String etat) {

            Connection conn = connect.connect2();
            
        
             String SQL = " UPDATE Appareils SET  etatfonct = ? WHERE id = ? ";
     
             long iid = 0;
     
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setString(1, etat);
                 pstmt.setInt(2, id);
     
                 int affectedRows = pstmt.executeUpdate();
                 // check the affected rows 
                 if (affectedRows > 0) {
                     // get the iid back
                     try (ResultSet rs = pstmt.getGeneratedKeys()) {
                         if (rs.next()) {
                             iid = rs.getLong(1);
                         }
                     } catch (SQLException ex) {
                         System.out.println(ex.getMessage());
                     }
                 }
             } catch (SQLException ex) {
                 System.out.println(ex.getMessage());
             }
             return iid;
         }
 
    
            // Drop an Apparell

    public void dropAppareil( int id) {

        Connection conn = connect.connect2();

        String SQL = "DELETE FROM Appareils WHERE id = ? ";;

            

            try (
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setInt(1, id);
            
                 pstmt.executeUpdate();
                 
                 // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                
            }
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }


    public static void main(String[] args) {
        Database main = new Database();

         main.getAppareil();

        
    }

    
}





