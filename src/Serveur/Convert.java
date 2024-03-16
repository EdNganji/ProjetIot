package Serveur;

import java.net.InetSocketAddress;

import com.google.gson.Gson;

import Ressources.Data;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.sun.net.httpserver.Headers;





public class Convert {
    public Convert() {
      
    }
   static String jsonString = "{\"Temperature\":\"45\",\"Humidite\":\"50\"}";

    public static Data Conversion(String Json) {

        

        // Create a Gson instance
        Gson gson = new Gson();

        // Convert the JSON string to a Person object
        Data data = gson.fromJson(Json, Data.class);

        // Access the fields of the Person object
        //System.out.println("Temperature: " + capteur.Temperature );
        //System.out.println("Humidité: " + capteur.Humidite );
        
        return data;
    }

    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {// Allow pre-flight requests

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                Headers headers = exchange.getResponseHeaders();
                headers.add("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
                headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                headers.add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                exchange.sendResponseHeaders(204, -1);
            } else {
                // Set CORS headers for regular requests
                Headers headers = exchange.getResponseHeaders();
                headers.add("Access-Control-Allow-Origin", "*"); // Allow requests from any origin
                headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                headers.add("Access-Control-Allow-Headers", "Content-Type,Authorization");


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
               // JSONObject jsonObject = new JSONObject(requestData);
    
                // Print the values
                // System.out.println("Temperature: " + jsonObject.getDouble("temperature"));
 
                // Create a Gson instance
                Gson gson = new Gson();
                Data data = gson.fromJson(requestData, Data.class);

                System.out.println("readvalue: " + data.readValue);



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

    public static void main(String[] args) throws Exception {
        // Créer un serveur HTTP sur le port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Définir le gestionnaire pour l'endpoint "/api"
        server.createContext("/api", new ApiHandler());

        // Démarrer le serveur
        server.start();
        System.out.println("Serveur démarré sur le port 8000...");
    }
}


