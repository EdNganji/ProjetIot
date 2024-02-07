package Ressources;

// Classe de base pour les objets connectés
class ObjetConnecte {
    private String nom;
    private String adresseIP;

    public ObjetConnecte(String nom, String adresseIP) {
        this.nom = nom;
        this.adresseIP = adresseIP;
    }

    public void seConnecter() {
        System.out.println("Connexion à " + nom + " via l'adresse IP " + adresseIP);
    }

    // Méthodes communes à tous les objets connectés
}

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

// Classe principale
public class GestionObjetsIntelligents {
    public static void main(String[] args) {
        Capteur capteurTemperature = new Capteur("CapteurTemp", "192.168.1.1", "Température");
        capteurTemperature.seConnecter();
        capteurTemperature.mesurer();

        Actionneur actionneurEclairage = new Actionneur("ActionneurEclairage", "192.168.1.2", "Contrôle d'éclairage");
        actionneurEclairage.seConnecter();
        actionneurEclairage.actionner();
    }
}
