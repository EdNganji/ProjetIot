package Serveur;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Ressources.*;

public class ListeAttente {

    static class ApiHandler implements HttpHandler {
        private final Queue<Data> queue = new LinkedList<>();
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Vérifier la méthode de la requête (doit être POST)
            if ("POST".equals(exchange.getRequestMethod())) {
                // Récupérer le corps de la requête
                InputStream requestBody = exchange.getRequestBody();
                byte[] buffer = new byte[requestBody.available()];
                requestBody.read(buffer);

                // Convertir les données du corps de la requête en String
                String requestData = new String(buffer);

                // Convertir le String recu en objet Data
                Gson gson = new Gson();
                Data data = gson.fromJson(requestData, Data.class);

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
                // Simuler l'envoi de l'objet à la base de données
                System.out.println("Envoi de l'objet à la base de données : " + data.readValue);
            } else {
                System.out.println("File d'attente vide. Aucun envoi à la base de données.");
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
