package Code;

import java.util.ArrayList;
import java.util.List;
import Ressources.Capteur;
import Ressources.Actionneur;


import java.util.Scanner;

import Data.Database;

import java.util.InputMismatchException;

public class Appareil {
	
	private List<Capteur> listCapteurs;
	private List<Actionneur> listActionneurs;

    public int id;
	public String ipAdress;
    public String name;
    public String type;
    public String etatFonct;
	public Database datab;

	public Appareil(Database database){

		this.datab = database;
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
	
	public void addAppareil( Scanner scanner, Appareil appareil) {

		appareil = appareilInfo(scanner, appareil);
  
		datab.insertvalues( appareil);
	}
	
	public void listAppareil() {
		System.out.println("\n------------------------------");

		clearConsole();
		datab.getAppareil();
		
	}
	
	public void updateAppareil( Scanner scanner, Appareil appareil) {
		System.out.println("\n------------------------------");

		clearConsole();
		datab.getAppareil();
		System.out.println("Entrez l'id de l'appareil à modifier");
		int id = Integer.parseInt(scanner.next());
		System.out.println("Entrez le nouvel etat de fonctionnement");
		String etat = scanner.next();

		datab.updateValues(id, etat);
		
	}
	
	public void dropAppareil( Scanner scanner) {
		
		System.out.println("\n------------------------------");

		clearConsole();
		datab.getAppareil();
		System.out.println("Entrez l'id de l'appareil à supprimer");
		int id = Integer.parseInt(scanner.next());
		datab.dropAppareil(id);

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
    public void addCapteur(Capteur capteur) {
        listCapteurs.add(capteur);
    }

    //  supprimer un capteur de la liste
    public void dropCapteur(Capteur capteur) {
        listCapteurs.remove(capteur);
    }

    // récupérer tous les capteurs de la liste
    public List<Capteur> getCapteur() {
        return listCapteurs;
    }

	// Gestion liste Actionneurs

	// ajouter un capteur à la liste
    public void addActionneur(Actionneur actionneur) {
        listActionneurs.add(actionneur);
    }

    //  supprimer un capteur de la liste
    public void supprimerAppareil(Actionneur actionneur) {
        listActionneurs.remove(actionneur);
    }

    // récupérer tous les capteurs de la liste
    public List<Actionneur> getAppareils() {
        return listActionneurs;
    }

	public void setID(int id){

        this.id = id;
    }

}


