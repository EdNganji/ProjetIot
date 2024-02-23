package Code;

import java.util.ArrayList;
import java.util.List;

public class ListeAppareils {
    private List<Appareil> appareils;

    public ListeAppareils() {
        appareils = new ArrayList<>();
    }

    // ajouter un appareil à la liste
    public void addAppareil(Appareil appareil) {
        appareils.add(appareil);
    }

    // supprimer un appareil de la liste
    public void dropAppareil(Appareil appareil) {
        appareils.remove(appareil);
    }

    // récupérer tous les appareils de la liste
    public List<Appareil> getAppareils() {
        return appareils;
    }

    // afficher les noms de tous les appareils de la liste
    public void afficherNomsAppareils() {
        for (Appareil appareil : appareils) {
            System.out.println(appareil);
        }
    }

    
}