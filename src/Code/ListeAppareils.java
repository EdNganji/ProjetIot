package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import Data.Database;
import Ressources.*;

public class ListeAppareils {
    public List<Appareil> appareils;

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

    // ajouter un appareil à la liste
    public void add(Appareil appareil) {

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

        boolean tag = false;
		
		String type = "";
        char etat = ' ';

		

        try{
        while (tag == false) {
			System.out.println("Entrez le nouveau type d'appareil'");
		    type = scanner.next();
			tag = Validation.validText(type, 25);
		}
		
		tag = false;

		while (tag == false) {

			System.out.print("Etat de fonctionnement( F pour fonctionnel ou N pour non ): ");
		    etat = scanner.next().charAt(0);
			tag = Validation.validetat(etat);

		}
        
		appareil.type = type;
        appareil.etatFonct = etat;
        
		
        datab.updateValuesApp(appareil);
        
		appareils.set(position, appareil);
        
		System.out.println("Valeurs modifiées");
        
		
    } catch (InputMismatchException e) {
        System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
    }
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