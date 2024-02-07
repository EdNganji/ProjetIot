package Ressources;

// Classe représentant un capteur
class Capteur extends ObjetConnecte {
    private String typeMesure;

    public Capteur(String nom, String adresseIP, String typeMesure) {
        super(nom, adresseIP);
        this.typeMesure = typeMesure;
    }

    public void mesurer() {
        System.out.println("Capteur " + getNom() + " mesure " + typeMesure);
    }

    // Méthodes spécifiques aux capteurs
}

