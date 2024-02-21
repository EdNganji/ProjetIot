package Ressources;

public class ObjetConnecte {

    private String nom;
    private String adresseIP;
    private String idAppareil;

    public ObjetConnecte(String nom, String adresseIP) {
        this.nom = nom;
        this.adresseIP = adresseIP;
    }

    public void seConnecter() {
        System.out.println("Connexion à " + nom + " via l'adresse IP " + adresseIP);
    }

    // Méthodes communes à tous les objets connectés
    
}
