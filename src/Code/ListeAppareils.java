package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Data.Database;
import Ressources.*;

public class ListeAppareils {
    private List<Appareil> appareils;

    public ListeAppareils() {
        appareils = new ArrayList<>();
    }

    // ajouter un appareil à la liste
    public void addAppareil(Scanner scanner, Appareil appareil, Database datab) {

        appareil = appareil.appareilInfo(scanner, appareil);
  
		long id = datab.insertvaluesAppareil( appareil);

		appareil.id = (int) id;

		// Ensuite on recupere l'id et on l'enregistre
        appareils.add(appareil);
    }

    

    // récupérer tous les appareils de la liste
    public List<Appareil> getAppareils() {
        return appareils;
    }

    // afficher les noms de tous les appareils de la liste
    public void afficherAppareils() {
        for (Appareil appareil : appareils) {
            System.out.println(appareil.id + "  - " + appareil.name);
        }

        if  (appareils.isEmpty()) {
            System.out.println("Aucun appareil dans cette liste");
            }
    }

    // Selectionner un appareil de la liste

    public Appareil SelectAppareil(Scanner scanner){
        
        afficherAppareils();

        System.out.println("Entrez le nom de l'appareil sélectionné");
		String name = scanner.next(); 
        for (Appareil appareil : appareils) {
            if (appareil.name == name){
                return appareil;
            }
        }

        return null; // si aucun appareil ne correspond
    }

    public int getPosition(Appareil appareil){
        
        int position = appareils.indexOf(appareil);
        return position; // si aucun appareil ne correspond
    }

    public void updateAppareil( Scanner scanner, Appareil appareil, int position, Database datab) {
		System.out.println("\n------------------------------");

        appareil.Infos();

		
		// datab.getAppareil();
		
		System.out.println("Entrez le nouveau type d'appareil'");
		String type = scanner.next();
		System.out.println("Entrez le nouveau etat de fonctionnement");
		String etat = scanner.next();

		appareil.type = type;
        appareil.etatFonct = etat;
        
		
		 datab.updateValuesApp(appareil);

		appareils.set(position, appareil);

		System.out.println("Valeurs modifiées");

		
	}


      // supprimer un appareil de la liste
    public void dropAppareil(Appareil appareil, Database datab) {
        
		datab.dropAppareil(appareil);

        for (Capteur capteur : appareil.listCapteurs) {
            datab.dropCapteur(capteur);
        }

        appareils.remove(appareil);
    }
    

    
}