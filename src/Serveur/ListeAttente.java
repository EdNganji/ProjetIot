package Serveur;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Code.*;
import Ressources.*;
import Data.*;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;




public class ListeAttente {

    Database datab = new Database();

    static class ApiHandler implements HttpHandler {
        Database datab = new Database();
        private final Queue<Data> queue = new LinkedList<>();
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Vérifier la méthode de la requête (doit être POST)
            if ("POST".equals(exchange.getRequestMethod())) {
                // Récupérer le corps de la requête
                InputStream requestBody = exchange.getRequestBody();
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Data data = gson.fromJson(reader, Data.class);

                // Ajouter l'objet data à la file d'attente
                synchronized (queue) {
                    queue.offer(data);
                }

                // Afficher les données de la requête
                System.out.println("Valeur reçue : " + data.idCapteur + " " + data.idChannel + " " + data.readValue);

                // Répondre au client avec un message de confirmation
                String response = "Données reçues avec succès !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }

                // Lancer l'envoi automatique des données à la base de données
                executorService.submit(this::sendToDatabase);
            } else {
                // Répondre avec un code 405 (Méthode non autorisée) si la méthode de la requête n'est pas POST
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private void sendToDatabase() {
            // Attendre un petit moment avant d'envoyer les données à la base de données
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Récupérer et retirer le premier élément de la file d'attente
            Data data;
            synchronized (queue) {
                data = queue.poll();
            }

            if (data != null) {

                datab.insertvaluesLecture(data);
                
                //System.out.println("Envoi de l'objet à la base de données : " + data.readValue);
            } else {
                System.out.println("File d'attente vide. Aucun envoi à la base de données.");
            }
        }
    }

    // Gestion des appareils  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    static class CreateAppHandler implements HttpHandler {
        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

                // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                long id = datab.insertvaluesAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil enregistré avec succès !"  + "\nID : "+id ;
               
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

    // Handler pour la récupération de la liste des appareils

    static class GetAppHandler implements HttpHandler {

        

        Database datab = new Database();
        ListeAppareils list = new ListeAppareils();
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                list = datab.getAppareils();
                // Initialiser Gson
                 Gson gson = new Gson();

                 list = datab.getAppareils();

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list.appareils);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
        }
    }

    // Handler pour la mise à jour d'un appareil
    static class UpdateAppHandler implements HttpHandler {

        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                long id = datab.updateValuesApp( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil Modifié avec succès !"  + "\nID : "+id ;
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
    }
    }

}

    // Handler pour la suppression d'un appareil par son ID
    static class DropAppHandler implements HttpHandler {

        Database datab = new Database();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                datab.dropAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Supprimé avec Succès  !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
        }
    }
    }


    // Gestion des Capteurs  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    static class CreateCapHandler implements HttpHandler {
        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Capteur   capteur = gson.fromJson(reader, Capteur.class);

            
                long id = datab.insertvaluesCapteur( capteur);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil enregistré avec succès !"  + "\nID : "+id ;
               
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

    // Handler pour la récupération de la liste des capteurs

    static class GetCapHandler implements HttpHandler {

        Database datab = new Database();
        List<Capteur> list = new ArrayList<>();
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        
                // Récupérer la valeur du paramètre "id"
                 int idappareil = Integer.parseInt(params.get("id"));

                
                // Initialiser Gson
                 Gson gson = new Gson();

                 list = datab.getCapteurs(idappareil);

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
        }

        // Méthode utilitaire pour convertir les paramètres de requête en Map
    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new java.util.HashMap<>();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length >= 2) {
                    String key = pair[0];
                    String value = pair[1];
                    result.put(key, value);
                }
            }
        }
        return result;
    }
    }

    // Handler pour la mise à jour d'un capteur
    static class UpdateCapHandler implements HttpHandler {

        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                long id = datab.updateValuesApp( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil Modifié avec succès !"  + "\nID : "+id ;
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
    }
    }

    }

    // Handler pour la suppression d'un Capteur par son ID
    static class DropCapHandler implements HttpHandler {

        Database datab = new Database();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                datab.dropAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Supprimé avec Succès  !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
        }
    }
    }


    // Gestion des Actionneurs  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    static class CreateActHandler implements HttpHandler {
        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                long id = datab.insertvaluesAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil enregistré avec succès !"  + "\nID : "+id ;
               
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

    // Handler pour la récupération de la liste des actionneurs

    static class GetActHandler implements HttpHandler {

        Database datab = new Database();
        List<Actionneur> list = new ArrayList<>(); ;
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        
                // Récupérer la valeur du paramètre "id"
                 int idappareil = Integer.parseInt(params.get("id"));

                 // Initialiser Gson
                 Gson gson = new Gson();
                 
                 list = datab.getActionneurs(idappareil);
                 

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
        }

         // Méthode utilitaire pour convertir les paramètres de requête en Map
    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new java.util.HashMap<>();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length >= 2) {
                    String key = pair[0];
                    String value = pair[1];
                    result.put(key, value);
                }
            }
        }
        return result;
    }
    }

    // Handler pour la mise à jour d'un Actionneur
    static class UpdateActHandler implements HttpHandler {

        Database datab = new Database();

            @Override
            public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                    //byte[] buffer = new byte[requestBody.available()];
                    //requestBody.read(buffer);

                    
                    InputStreamReader reader = new InputStreamReader(requestBody);
                    Gson gson = new Gson();
                    // Convertir les données du corps de la requête en objet Data
                    Appareil   appareil = gson.fromJson(reader, Appareil.class);

                
                    long id = datab.updateValuesApp( appareil);
                    

                    // Répondre au client avec un message de confirmation
                    String response = "Appareil Modifié avec succès !"  + "\nID : "+id ;
                    exchange.sendResponseHeaders(200, response.length());
                    try (OutputStream responseBody = exchange.getResponseBody()) {
                        responseBody.write(response.getBytes());
                    }
            }
            }

    }
}

    // Handler pour la suppression d'un actionneur par son ID
    static class DropActHandler implements HttpHandler {

        Database datab = new Database();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                datab.dropAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Supprimé avec Succès  !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
        }
    }
    }


    // Gestion des Channels  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    static class CreateChaHandler implements HttpHandler {
        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Channel   channel = gson.fromJson(reader, Channel.class);

            
                long id = datab.insertvaluesChannel( channel);
                

                // Répondre au client avec un message de confirmation
                String response = "Channel enregistré avec succès !"  + "\nID : "+id ;
               
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

    // Handler pour la récupération de la liste des channels

    static class GetChaHandler implements HttpHandler {

        Database datab = new Database();
        ListeChannels list = new ListeChannels();
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                list = datab.getChannels();
                // Initialiser Gson
                 Gson gson = new Gson();

                 list = datab.getChannels();

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list.channels);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    }

    // Handler pour la mise à jour d'un channel
    static class UpdateChaHandler implements HttpHandler {

        Database datab = new Database();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                long id = datab.updateValuesApp( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Appareil Modifié avec succès !"  + "\nID : "+id ;
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
    }
    }
    }



    // Handler pour la suppression d'un channel par son ID
    static class DropChaHandler implements HttpHandler {

        Database datab = new Database();
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {


            // Allow pre-flight requests
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
                //byte[] buffer = new byte[requestBody.available()];
                //requestBody.read(buffer);

                
                InputStreamReader reader = new InputStreamReader(requestBody);
                Gson gson = new Gson();
                // Convertir les données du corps de la requête en objet Data
                Appareil   appareil = gson.fromJson(reader, Appareil.class);

            
                datab.dropAppareil( appareil);
                

                // Répondre au client avec un message de confirmation
                String response = "Supprimé avec Succès  !";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(response.getBytes());
                }
        }
        }
        }
    }


    // Handler pour la récupération de la liste des Lectures

    static class GetDataHandler implements HttpHandler {

        Database datab = new Database();
        ListeLectures list = new ListeLectures();
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                
                // Initialiser Gson
                 Gson gson = new Gson();

                 list = datab.getLectures();

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list.lectures);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
        }
    }
    
    // Handler pour la récupération de la liste des Lectures

    static class GetDataHandlerList implements HttpHandler {

        Database datab = new Database();
        ListeLectures list = new ListeLectures();
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Allow pre-flight requests
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


            if ("GET".equals(exchange.getRequestMethod())) {
                        // Get query parameters if needed
                //String query = exchange.getRequestURI().getQuery();

                
                // Initialiser Gson
                 Gson gson = new Gson();

                 list = datab.getLecturesShort();

                // Convertir l'ArrayList en JSON
                String json = gson.toJson(list.lectures);

                
                // Respond with a simple message
                //String response = "GET request received";
                String response = json;

                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
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
        server.createContext("/api/appareil", new CreateAppHandler());
        server.createContext("/api/appareil/get", new GetAppHandler());
        server.createContext("/api/appareil/update", new UpdateAppHandler());
        server.createContext("/api/appareil/drop", new DropAppHandler());
        server.createContext("/api/capteur", new CreateCapHandler());
        server.createContext("/api/capteur/get", new GetCapHandler());
        server.createContext("/api/capteur/update", new UpdateCapHandler());
        server.createContext("/api/capteur/drop", new DropCapHandler());
        server.createContext("/api/actuateur", new CreateActHandler());
        server.createContext("/api/actuateur/get", new GetActHandler());
        server.createContext("/api/actuateur/update", new UpdateActHandler());
        server.createContext("/api/actuateur/drop", new DropActHandler());
        server.createContext("/api/channel", new CreateChaHandler());
        server.createContext("/api/channel/get", new GetChaHandler());
        server.createContext("/api/channel/update", new UpdateChaHandler());
        server.createContext("/api/channel/drop", new DropChaHandler());
        server.createContext("/api/data", new GetDataHandler());
        server.createContext("/api/data/list", new GetDataHandlerList());

        // Démarrer le serveur
        server.start();
        System.out.println("Serveur démarré sur le port 8000...");
    }
}


