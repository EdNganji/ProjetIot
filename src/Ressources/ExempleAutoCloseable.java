package Ressources;

public class ExempleAutoCloseable {

    // Une classe qui implémente l'interface AutoCloseable
    public static class Ressource implements AutoCloseable {
        // Méthode représentant une opération de la ressource
        public void operation() {
            System.out.println("Opération effectuée par la ressource");
        }

        // La méthode close() est appelée automatiquement lorsqu'elle est utilisée dans un bloc try-with-resources
        @Override
        public void close() throws Exception {
            System.out.println("Fermeture automatique de la ressource");
        }
    }

    public static void main(String[] args) {
        // Utilisation de la ressource avec un bloc try-with-resources
        try (Ressource ressource = new Ressource()) {
            ressource.operation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // La ressource est fermée automatiquement à la fin du bloc try
    }
}
