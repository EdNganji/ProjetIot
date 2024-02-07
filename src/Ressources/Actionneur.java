package Ressources;

// Classe représentant un actionneur
class Actionneur extends ObjetConnecte {
    private String typeAction;

    public Actionneur(String nom, String adresseIP, String typeAction) {
        super(nom, adresseIP);
        this.typeAction = typeAction;
    }

    public void actionner() {
        System.out.println("Actionneur " + getNom() + " effectue une action de type " + typeAction);
    }

    // Méthodes spécifiques aux actionneurs
}
