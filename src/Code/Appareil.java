package Code;

import java.util.ArrayList;
import java.util.List;
import Ressources.Capteur;
import Ressources.Actionneur;


import java.util.Scanner;

import Data.Database;

import java.util.InputMismatchException;

public class Appareil {
	
	public List<Capteur> listCapteurs;
	public List<Actionneur> listActionneurs;

    public int id;
	public String ipAdress;
    public String name;
    public String type;
    public String etatFonct;
	

	public Appareil(){

		listCapteurs = new ArrayList<>();
		listActionneurs = new ArrayList<>();
	}
    
	public Appareil appareilInfo(Scanner scanner, Appareil appareil){

		try{
		boolean  tag = false;
		

		while (tag == false) {
			System.out.print("Nom de l'appareil : ");
			appareil.name = scanner.next();
			tag = Validation.validText(appareil.name, 25);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Adresse IP : ");
			appareil.type = scanner.next();
			tag = Validation.validText(appareil.type, 15);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Type d'appareil : ");
			appareil.type = scanner.next();
			tag = Validation.validText(appareil.type, 25);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Etat de fonctionnement : ");
			appareil.etatFonct = scanner.next();
			tag = Validation.validText(appareil.etatFonct, 25);
		}
	} catch (InputMismatchException e) {
		System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer un entier.");
	}

		return appareil;

	}
	
	

	public void Infos() {

		System.out.println(name + "   " + ipAdress + "      " + type + "   " + etatFonct + "\n");
  
		
	}
	
	
	
	public void rhema() {
		throw new ArithmeticException("Message d'erreur / par 0 impossible!");
	}

	public String toString() {
		return (name);
	}

	public static void clearConsole() {
        try {
            // Exécute la commande "cmd /c cls" pour effacer la console
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Gère les exceptions liées à l'exécution de la commande
            e.printStackTrace();

		}
        
    }

					//Gestion liste Capteurs


	// ajouter un capteur à la liste
    public void addCapteur(Scanner scanner, Capteur capteur, Database datab) {

		capteur = capteur.capteurInfo(scanner, capteur);
  
		 long id = datab.insertvaluesCapteur(capteur,);

		 capteur.id = (int) id; 

        listCapteurs.add(capteur);
    }

	public void addcapteur( ) {

		

	}


	
	// afficher les noms de tous les capteurs de la liste
	public void afficherCapteurs() {
		for (Capteur capteur : listCapteurs) {
			System.out.println(capteur.id + "  - " + capteur.name);
        }
		
        if  (listCapteurs.isEmpty()) {
			System.out.println("Aucun appareil dans cette liste");
		}
    }
	
    // Selectionner un capteur de la liste
    public Capteur SelectCapteur(Scanner scanner){
		
		afficherCapteurs();
		
        System.out.println("Entrez l'id du capteur à sélectionner");
		int id = Integer.parseInt(scanner.next()); 
        for (Capteur capteur : listCapteurs) {
			if (capteur.id == id){
				return capteur;
            }
        }
		
        return null; // si aucun appareil ne correspond
    }
	
	public int getPosition(Capteur capteur){
		
		int position = listCapteurs.indexOf(capteur);
        return position; 
    }
	
	//  supprimer un capteur de la liste
	public void dropCapteur(Capteur capteur, Database datab) {

		datab.dropCapteur(capteur);

		listCapteurs.remove(capteur);
	}

	public void updateCapteur( Scanner scanner, Capteur capteur, int position, Database datab) {
		System.out.println("\n------------------------------");

		capteur.Infos();

		// datab.getAppareil();
		
		System.out.println("Entrez le nouveau type de Mesure");
		String type = scanner.next();
		System.out.println("Entrez le nouveau nombre de channels");
		int nbChan = scanner.nextInt();

		capteur.typeMesure = type;
		capteur.nbreChannel  = nbChan;
		
		datab.updateValuesCapteur(capteur);

		listCapteurs.set(position, capteur);

		System.out.println("Valeurs modifiées");

		
	}

	public void linkChannel( Scanner scanner, Capteur capteur, int position, ListeAppareils list) {
		
		
		System.out.println("Entrez l'id du channel à lier'");
		int id = scanner.nextInt();

		// datab.link(capteur.id, id);

		// Verifier le nombre de channels 

		System.out.println("Entrez le nouveau nombre de channels");
		
  
		// datab.insertvalues( capteur);
	}
	

				// Gestion liste Actionneurs



	// ajouter un Actionneur à la liste
    public void addActionneur(Scanner scanner, Actionneur actionneur, Database datab) {

		actionneur = actionneur.ActionneurInfo(scanner, actionneur);
  
		 long id = datab.insertvaluesActionneur(actionneur, this.id);

		 actionneur.id = (int) id; 

        listActionneurs.add(actionneur);

    }

    
	 // afficher les noms de tous les Actionneurs de la liste
	public void afficherActionneurs() {
        for (Actionneur actionneur:  listActionneurs) {
            System.out.println(actionneur.id + "  - " + actionneur.name);
        }

        if  (listActionneurs.isEmpty()) {
            System.out.println("Aucun appareil dans cette liste");
            }
    }

    // Selectionner un actionneur de la liste

    public Actionneur SelectActionneur(Scanner scanner){
        
        afficherActionneurs();

        System.out.println("Entrez l'id du actionneur à sélectionner");
		int id = Integer.parseInt(scanner.next()); 
        for (Actionneur actionneur: listActionneurs) {
            if (actionneur.id == id){
                return actionneur;
            }
        }

        return null; // si aucun appareil ne correspond
    }

	public int getPositionA(Actionneur actionneur){
        
        int position = listActionneurs.indexOf(actionneur);
        return position; 
    }

	//  supprimer un capteur de la liste
    public void dropAppareil(Actionneur actionneur, Database datab) {

		datab.dropActionneur(actionneur);
        listActionneurs.remove(actionneur);
    }


	public void updateActionneur( Scanner scanner, Actionneur actionneur, int position, Database datab) {
		System.out.println("\n------------------------------");

		
		// datab.getAppareil();
		
		System.out.println("Entrez le nouveau type d'Action'");
		String type = scanner.next();
		

		actionneur.typeAction = type;
		
		
		datab.updateValuesActionneur(actionneur);

		listActionneurs.set(position, actionneur);

		System.out.println("Valeurs modifiées");
	}

	public void setID(int id){

        this.id = id;
    }

}


