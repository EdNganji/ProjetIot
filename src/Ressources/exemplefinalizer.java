package Ressources;

public class exemplefinalizer {

    // Un simple objet avec une méthode finalize
    public static class MonObjet {
        public void operation() {
            System.out.println("Opération effectuée par MonObjet");
        }

        @Override
        protected void finalize() throws Throwable {
            try {
                // Code pour libérer les ressources, fermer les connexions, etc.
                System.out.println("Finalize appelé pour MonObjet");

            } finally {
                // Appeler le finalize de la classe de base (Object)
                super.finalize();
            }
        }
    }

    public static void main(String[] args) {
        // Utilisation de l'objet avec la méthode finalize
        MonObjet obj = new MonObjet();
        obj.operation();

        // Affecter null à la référence obj pour rendre l'objet éligible à la collecte
        obj = null;

        // Appel explicite du garbage collector (à des fins de démonstration, généralement non recommandé)
        System.gc();

        // Attendre un moment pour permettre au garbage collector de s'exécuter
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
