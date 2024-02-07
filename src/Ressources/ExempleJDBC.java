package Ressources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcExample {

    // Les détails de connexion à la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ma_base_de_donnees";
    private static final String DB_USER = "mon_utilisateur";
    private static final String DB_PASSWORD = "mon_mot_de_passe";

    public static void main(String[] args) {
        // Établir la connexion à la base de données
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connexion à la base de données réussie.");

            // Exemple d'insertion de données
            insertData(connection, "John Doe", 25);

            // Exemple de sélection de données
            selectData(connection);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    private static void insertData(Connection connection, String name, int age) {
        try {
            // Préparation de la requête SQL pour l'insertion de données
            String sql = "INSERT INTO utilisateur (nom, age) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Remplacement des paramètres dans la requête
                statement.setString(1, name);
                statement.setInt(2, age);

                // Exécution de la requête
                int rowsAffected = statement.executeUpdate();
                System.out.println(rowsAffected + " ligne(s) insérée(s) avec succès.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de données : " + e.getMessage());
        }
    }

    private static void selectData(Connection connection) {
        try {
            // Exécution d'une requête de sélection
            String sql = "SELECT * FROM utilisateur";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                // Traitement des résultats de la requête
                while (resultSet.next()) {
                    String name = resultSet.getString("nom");
                    int age = resultSet.getInt("age");
                    System.out.println("Utilisateur : " + name + ", Âge : " + age);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sélection de données : " + e.getMessage());
        }
    }
}
