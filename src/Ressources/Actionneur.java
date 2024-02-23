package Ressources;

// Classe représentant un actionneur
public class Actionneur extends Composante {
    public String typeAction;
    public int puissance;

    public Actionneur(String nom, String adresseIP, String typeAction) {
        super(nom, adresseIP);
        this.typeAction = typeAction;
    }

    public void infos() {
        System.out.println("L'Actionneur " + toString() + " effectue une action de type " + typeAction);
    }

    public void actionner(int value) {
        this.puissance = value;
        System.out.println("L' Actionneur " + toString() + " a recu la commande " + value);
    }


    // Méthodes spécifiques aux actionneurs
}
