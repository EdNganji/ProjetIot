package Serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.json.JSONObject;

public class HttpServeur {
    public static void main(String[] args) throws Exception {
        // Créer un serveur HTTP sur le port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Définir le gestionnaire pour l'endpoint "/api"
        server.createContext("/api", new ApiHandler());

        // Démarrer le serveur
        server.start();
        System.out.println("Serveur démarré sur le port 8000...");
    }

    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Vérifier la méthode de la requête (doit être POST)
            if ("POST".equals(exchange.getRequestMethod())) {
                // Récupérer le corps de la requête
                InputStream requestBody = exchange.getRequestBody();
                byte[] buffer = new byte[requestBody.available()];
                requestBody.read(buffer);

                // Convertir les données du corps de la requête en chaîne de caractères
                String requestData = new String(buffer);

                // Afficher les données de la requête
                System.out.println("Données de la requête POST : " + requestData);
                JSONObject jsonObject = new JSONObject(requestData);
    
                // Print the values
                System.out.println("Temperature: " + jsonObject.getDouble("temperature"));
                


                // Répondre au client avec un message de confirmation
                String response = "Données reçues avec succès !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
            } else {
                // Répondre avec un code 405 (Méthode non autorisée) si la méthode de la requête n'est pas POST
                exchange.sendResponseHeaders(405, -1);
            }
        }

        
    }
}
