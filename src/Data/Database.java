package Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/*import java.sql.ResultSet;*/
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Code.*;
import Ressources.*;

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

        String SQL1 = "CREATE TABLE IF NOT EXISTS appareil"
                + "("
                +  "  id serial PRIMARY KEY,"
                +  "  adresseIp character(15) NOT NULL ,"
                +   " name character varying NOT NULL,"
                +   " type character varying NOT NULL,"
                +   " etatFonct varchar  NOT NULL,"
                +   " active boolean DEFAULT TRUE"
                + ")";
        

        String SQL2 = "CREATE TABLE IF NOT EXISTS capteur"
                + "("
                +  "  id serial PRIMARY KEY,"
                +   " name character varying NOT NULL,"
                +   " idApparreil integer NOT NULL,"
                +   " typeMesure character varying NOT NULL,"
                +   " nbreChannel integer  DEFAULT 1,"
                +   " active boolean DEFAULT TRUE"
                +   " CONSTRAINT idApparreil FOREIGN KEY (idApparreil)"
                +   " REFERENCES appareils (id)"
                + ")";        

        String SQL3 = "CREATE TABLE IF NOT EXISTS actionneur"
                + "("
                +  "  id serial PRIMARY KEY,"
                +   " name character varying NOT NULL,"
                +   " idApparreil integer NOT NULL,"
                +   " typeAction character varying NOT NULL,"
                +   " puissance integer  NOT NULL,"
                +   " active boolean DEFAULT TRUE"
                +   " CONSTRAINT idApparreil FOREIGN KEY (idApparreil)"
                +   " REFERENCES appareils (id)"
                + ")";
                

        String SQL4 = "CREATE TABLE IF NOT EXISTS channel"
                + "("
                +  "  id serial PRIMARY KEY,"
                +  "  name character varying NOT NULL,"
                +   " unit character varying NOT NULL,"
                +   " active boolean DEFAULT TRUE"
                + ")";


        String SQL5 = "CREATE TABLE IF NOT EXISTS attribution"
                + "("
                +  "  id serial PRIMARY KEY,"
                +  "  idCapteur integer NOT NULL,"
                +   " idChannel integer NOT NULL,"
                +   " active boolean DEFAULT TRUE,"
                +   " CONSTRAINT idCapteur FOREIGN KEY (idCapteur)"
                +   " REFERENCES capteur (id),"
                +   " CONSTRAINT idChannel FOREIGN KEY (idChannel)"
                +   " REFERENCES channel (id)"
                + ")";


        String SQL6 = "CREATE TABLE IF NOT EXISTS lecture"
                + "("
                +  "  id serial PRIMARY KEY,"
                +  "  idCapteur integer NOT NULL,"
                +  "  idChannel integer NOT NULL  ,"
                +  "  readValue numeric NOT NULL  ,"
                +   " date_envoi date NOT NULL DEFAULT CURRENT_DATE,"
                +   " heure_envoi time without time zone NOT NULL DEFAULT CURRENT_TIME,"
                +   " active boolean DEFAULT TRUE,"
                +   " CONSTRAINT idCapteur FOREIGN KEY (idCapteur)"
                +   " REFERENCES capteur (id),"
                +   " CONSTRAINT idChannel FOREIGN KEY (idChannel)"
                +   " REFERENCES channel (id)"
                + ")";
         
            
        this.Execute(conn, SQL1);
        this.Execute(conn, SQL2);
        this.Execute(conn, SQL3);
        this.Execute(conn, SQL4);
        this.Execute(conn, SQL5);
        this.Execute(conn, SQL6);
        
    }


        // Display Appareil list

    
                    //Gestion des Appareils

        // Enregistrer un appareil
        public long insertvaluesAppareil( Appareil appareil) {

           Connection conn = connect.connect2();

            String SQL = "INSERT INTO appareil ( \"name\", \"adresseip\" , \"type\", \"etatfonct\") "
                    + "VALUES(?,?,?,?)";
    
            long id = 0;

    
            try (
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {
    
                pstmt.setString(1, appareil.name);
                pstmt.setString(2, appareil.ipAdress);
                pstmt.setString(3, appareil.type);
                pstmt.setString(4, "" + appareil.etatFonct);
    
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

        //Liste d'appareils

        public ListeAppareils getAppareils() {

            Connection conn = connect.connect2();

            ListeAppareils list = new ListeAppareils();
    
    
            
    
            String SQL = "SELECT a.id, a.adresseip , a.name, a.type, a.etatfonct"
            +  "  FROM appareil a"
            +   " WHERE a.active = TRUE";
    
            try (
            Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(SQL)) {
                // display actor information
                //displayLectures(rs);
                list = recordAppareil(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }

        public ListeAppareils recordAppareil(ResultSet rs) throws SQLException {

            ListeAppareils list = new ListeAppareils() ;
            
            while (rs.next()) {
                
                        Appareil appareil = new Appareil();
                        appareil.id = rs.getInt("id") ;
                        appareil.ipAdress = rs.getString("adresseip") ;
                        appareil.name =  rs.getString("name") ;
                        appareil.type =  rs.getString("type") ;
                        appareil.etatFonct =  rs.getString("etatfonct").charAt(0); 

                    list.add(appareil);
            }

            return list;
        }
    
        
       

        //UPdate Data from an apparell
        public long updateValuesApp( Appareil appareil) {

            Connection conn = connect.connect2();
            
        
             String SQL = " UPDATE appareil SET  type = ? , etatfonct = ? WHERE id = ? ";
     
             long iid = 0;
     
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setString(1, appareil.type);
                 pstmt.setString(2, "" + appareil.etatFonct);
                 pstmt.setInt(3, appareil.id);
     
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

    public void dropAppareil( Appareil appareil) {

        Connection conn = connect.connect2();

        String SQL = "UPDATE appareil SET active = ? WHERE id = ?";

            

            try (
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setBoolean(1, false);
                pstmt.setInt(2, appareil.id);
            
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
    
    //Gestion des Capteurs

        // Enregistrer un capteur
        public long insertvaluesCapteur(Capteur capteur) {

            Connection conn = connect.connect2();
 
             String SQL = "INSERT INTO capteur ( \"name\", \"idappareil\" , \"typemesure\", \"nbrechannel\") "
                     + "VALUES(?,?,?,?)"; 
             long id = 0;

             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setString(1, capteur.name);
                 pstmt.setInt(2, capteur.idApparreil);
                 pstmt.setString(3, capteur.typeMesure);
                 pstmt.setInt(4, capteur.nbreChannel);
     
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

         

         //Liste de capteurs


        public List<Capteur> getCapteurs() {

            List<Capteur> list = new ArrayList<>();

            Connection conn = connect.connect2();
    
    
            
     
            String SQL = "SELECT id, name, idapparreil ,  typemesure, nbrechannel"
            +  "  FROM capteur "
            +   " WHERE active = true";
    
            try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
     
                
                // display actor information
                //displayLectures(rs);
                
                list = recordCapteur(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }

        public List<Capteur> recordCapteur(ResultSet rs) throws SQLException {

            List<Capteur> list = new ArrayList<>(); ;
            
            while (rs.next()) {
                
                        Capteur capteur = new Capteur(null, null, 0);
                        capteur.id = rs.getInt("id") ;
                        capteur.idApparreil = rs.getInt("idapparreil") ;
                        capteur.name =  rs.getString("name") ;
                        capteur.typeMesure =  rs.getString("typemesure") ;
                        capteur.nbreChannel =  rs.getInt("nbrechannel"); 

                    list.add(capteur);
            }

            return list;
        }
    
 
         //modifier un capteur 
         public long updateValuesCapteur( Capteur capteur) {
 
             Connection conn = connect.connect2();
             
         
              String SQL = " UPDATE capteur SET  typemesure = ? , nbrechannel = ? WHERE id = ? ";
      
              long iid = 0;
      
              try (
              PreparedStatement pstmt = conn.prepareStatement(SQL,
                      Statement.RETURN_GENERATED_KEYS)) {
      
                  pstmt.setString(1, capteur.typeMesure);
                  pstmt.setInt(2, capteur.nbreChannel);
                  pstmt.setInt(3, capteur.id);
      
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
  
     
             // Supprimer un capteur
 
     public void dropCapteur( Capteur capteur) {
 
         Connection conn = connect.connect2();
 
         String SQL = "UPDATE capteur SET active = ? WHERE id = ?";
 
             
 
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
 
                 pstmt.setBoolean(1, false);
                 pstmt.setInt(2, capteur.id);
             
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


        String SQL2 = "UPDATE lectures SET active = ? WHERE idcapteur = ?";
 
             
 
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL2,
                     Statement.RETURN_GENERATED_KEYS)) {
 
                 pstmt.setBoolean(1, false);
                 pstmt.setInt(2, capteur.id);
             
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


      //Gestion des Actionneurs

        // Enregistrer un actionneur
        public long insertvaluesActionneur(Actionneur actionneur) {

            Connection conn = connect.connect2();
 
             String SQL = "INSERT INTO actionneur ( \"name\", \"idappareil\" , \"typeaction\", \"puissance\") "
                     + "VALUES(?,?,?,?)"; 
             long id = 0;

             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setString(1, actionneur.name);
                 pstmt.setInt(2, actionneur.idApparreil);
                 pstmt.setString(3, actionneur.typeAction);
                 pstmt.setInt(4, 0);
     
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

          //Liste de Actionneurs


        public List<Actionneur> getActionneurs() {

            List<Actionneur> list =  new ArrayList<>();

            Connection conn = connect.connect2();
    
    
            
     
            String SQL = "SELECT a.id, a.name, a.idappareil ,  a.typeaction, a.puissance"
            +  "  FROM actionneur a"
            +   " WHERE a.active = TRUE";
    
            try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL)) {
     
                
                // display actor information
                //displayLectures(rs);
                
                list = recordActionneur(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }

        public List<Actionneur> recordActionneur(ResultSet rs) throws SQLException {

            List<Actionneur> list = new ArrayList<>(); ;
            
            while (rs.next()) {
                
                        Actionneur actionneur = new Actionneur(null, null);
                        actionneur.id = rs.getInt("id") ;
                        actionneur.idApparreil = rs.getInt("idappareil") ;
                        actionneur.name =  rs.getString("name") ;
                        actionneur.typeAction =  rs.getString("typeaction") ;
                        actionneur.puissance =  rs.getInt("puissance"); 

                    list.add(actionneur);
            }

            return list;
        }
 
         //modifier un actionneur 
         public long updateValuesActionneur(Actionneur actionneur) {
 
             Connection conn = connect.connect2();
             
         
              String SQL = " UPDATE actionneur SET  typeaction = ?  WHERE id = ? ";
      
              long iid = 0;
      
              try (
              PreparedStatement pstmt = conn.prepareStatement(SQL,
                      Statement.RETURN_GENERATED_KEYS)) {
      
                  pstmt.setString(1, actionneur.typeAction);
                  pstmt.setInt(2, actionneur.id);
      
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
  
     
             // Supprimer un actionneur
     public void dropActionneur ( Actionneur actionneur ) {
 
         Connection conn = connect.connect2();
 
         String SQL = "UPDATE actionneur SET active = ? WHERE id = ?";
 
             
 
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
 
                 pstmt.setBoolean(1, false);
                 pstmt.setInt(2, actionneur.id);
             
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


     //Gestion des Channels

        // Enregistrer un channel
        public long insertvaluesChannel(Channel channel ) {

            Connection conn = connect.connect2();
 
             String SQL = "INSERT INTO channel ( \"name\", \"unit\") "
                     + "VALUES(?,?)"; 
             long id = 0;

             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setString(1, channel.name);
                 pstmt.setString(2, channel.unit);
                
     
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

          //Liste de Channels

        public ListeChannels getChannels() {

            ListeChannels list = new ListeChannels();

            Connection conn = connect.connect2();
    
    
            
    
            String SQL = "SELECT id, name, unit"
            +  "  FROM channel "
            +   " WHERE active = TRUE";
    
            try (
            Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(SQL)) {
                // display actor information
                //displayLectures(rs);
                list = recordChannel(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }

        public ListeChannels recordChannel(ResultSet rs) throws SQLException {

            ListeChannels list = new ListeChannels() ;
            
            while (rs.next()) {
                
                        Channel channel = new Channel(null, 0, null);
                        channel.id = rs.getInt("id") ;
                        channel.name =  rs.getString("name") ;
                        channel.unit =  rs.getString("unit") ;

                        list.add(channel);
            }

            return list;
        }
 
         //modifier un channel 
         public long updateValuesChannel(Channel channel) {
 
             Connection conn = connect.connect2();
             
         
              String SQL = " UPDATE channel SET  unit = ?  WHERE id = ? ";
      
              long iid = 0;
      
              try (
              PreparedStatement pstmt = conn.prepareStatement(SQL,
                      Statement.RETURN_GENERATED_KEYS)) {
      
                  pstmt.setString(1, channel.unit);
                  pstmt.setInt(2, channel.id);
      
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
  
     
             // Supprimer un channel
 
     public void dropchannel ( Channel channel) {
 
         Connection conn = connect.connect2();
 
         String SQL = "UPDATE channel SET active = ? WHERE id = ?";
 
             
 
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
 
                 pstmt.setBoolean(1, false);
                 pstmt.setInt(2, channel.id);
             
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


        String SQL2 = "UPDATE lectures SET active = ? WHERE idchannel = ?";
 
             
 
             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL2,
                     Statement.RETURN_GENERATED_KEYS)) {
 
                 pstmt.setBoolean(1, false);
                 pstmt.setInt(2, channel.id);
             
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

     // Lier un channel
     public long linkChannel(Channel channel, Capteur capteur ) {

        Connection conn = connect.connect2();

         String SQL = "INSERT INTO attributions ( \"idCapteur\", \"idChannel\") "
                 + "VALUES(?,?)"; 
         long id = 0;

         try (
         PreparedStatement pstmt = conn.prepareStatement(SQL,
                 Statement.RETURN_GENERATED_KEYS)) {
 
             pstmt.setInt(1, capteur.id);
             pstmt.setInt(2, channel.id);
            
 
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



       //Enregistrer une Lecture
         public long insertvaluesLecture(Data data) {

            Connection conn = connect.connect2();
 
             String SQL = "INSERT INTO Lectures ( \"idcapteur\", \"idchannel\" , \"readvalue\") "
                             + "VALUES(?,?,?)"; 
             long id = 0;

             try (
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
     
                 pstmt.setInt(1, data.idCapteur);
                 pstmt.setInt(2, data.idChannel);
                 pstmt.setDouble(3, data.readValue);
     
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


         public ListeLectures getLectures() {

            ListeLectures list = new ListeLectures();

            Connection conn = connect.connect2();
    
    
            
    
            String SQL = "SELECT a.id, a.idcapteur , b.name Capteur, c.name Channel, a.readvalue, a.date_envoi, a.heure_envoi"
            +  "  FROM Lectures a"
            +  "  INNER JOIN capteur b"
            +  "  ON b.id = a.idcapteur"
            +   " INNER JOIN channel c"
            +   " ON c.id = a.idchannel"
            +   " WHERE a.active = TRUE";
    
            try (
            Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(SQL)) {
                // display actor information
                //displayLectures(rs);
                list = recordLectures(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }

        public ListeLectures getLecturesShort() {

            ListeLectures list = new ListeLectures();

            Connection conn = connect.connect2();
    
    
            
    
            String SQL = "SELECT a.id, a.idcapteur , b.name Capteur, c.name Channel, a.readvalue, a.date_envoi, a.heure_envoi"
            +  "  FROM Lectures a"
            +  "  INNER JOIN capteur b"
            +  "  ON b.id = a.idcapteur"
            +   " INNER JOIN channel c"
            +   " ON c.id = a.idchannel"
            + " WHERE a.active = TRUE"
            + " ORDER BY a.date_envoi DESC, a.heure_envoi DESC"
            + " LIMIT 20";
    
            try (
            Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(SQL)) {
                // display actor information
                //displayLectures(rs);
                list = recordLectures(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return list;
        }
    
        
        public void displayLectures(ResultSet rs) throws SQLException {
            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t"
                        + rs.getString("id capteur") + "\t"
                        + rs.getString("nom capteur") + "\t"
                        + rs.getString("nom channel") + "\t"
                        + rs.getString("Valeur") + "\t"
                        + rs.getString("date d'envoi") + "\t"
                        + rs.getString("heure d'envoi"));
    
            }
        }

        public ListeLectures recordLectures(ResultSet rs) throws SQLException {

            ListeLectures list = new ListeLectures() ;
            
            while (rs.next()) {
                
                        Lecture lecture = new Lecture();
                        lecture.id = rs.getInt("id") ;
                        lecture.nameCap = rs.getString("Capteur") ;
                        lecture.nameCha =  rs.getString("Channel") ;
                        lecture.readValue =  rs.getDouble("readvalue") ;
                        lecture.dateEnvoi =  rs.getDate("date_envoi") ;
                        lecture.heureEnvoi =  rs.getTime("heure_envoi");

                    list.addLectures(lecture);
            }

            return list;
        }
 
 
        

    public static void main(String[] args) {
        Database main = new Database();

        ListeChannels list = new ListeChannels();

        list = main.getChannels();

        for (Channel channel : list.channels) {
             channel.Infos();
        }


        // main.getAppareil();

    }

        


        
    

    
}





