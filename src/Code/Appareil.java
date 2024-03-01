package Code;

import java.util.ArrayList;
import java.util.List;
import Ressources.Capteur;
import Ressources.Channel;
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
    public char etatFonct;
	

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
			System.out.print("Etat de fonctionnement( F pour fonctionnel ou N pour non ): ");
			appareil.etatFonct = scanner.next().charAt(0);
			tag = Validation.validetat(appareil.etatFonct);
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
    public void addCapteur(Scanner scanner, Capteur capteur, Database datab, int idApp) {

		capteur = capteur.capteurInfo(scanner, capteur);
  
		 long id = datab.insertvaluesCapteur(capteur, idApp);

		 capteur.id = (int) id; 

        listCapteurs.add(capteur);
    }

	public void addcapteur( ) {

		

	}


	
	// show les noms de tous les capteurs de la liste
	public void showCapteurs() {
		for (Capteur capteur : listCapteurs) {
			System.out.println(capteur.id + "  - " + capteur.name);
        }
		
        if  (listCapteurs.isEmpty()) {
			System.out.println("Aucun appareil dans cette liste");
		}
    }
	
    // Selectionner un capteur de la liste
    public Capteur SelectCapteur(Scanner scanner){
		
		showCapteurs();
		
        System.out.println("Entrez le nom du capteur à sélectionner");
		String name = scanner.next(); 
        for (Capteur capteur : listCapteurs) {
			if (capteur.name == name){
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

		try{
			boolean  tag = false;
			String type = "";
			int nbChan = 1;

			
	
			while (tag == false) {
				System.out.println("Entrez le nouveau type de Mesure");
				type = scanner.next();
				tag = Validation.validText(type, 25);
			}
			
			tag = false;
	
			while (tag == false) {
				System.out.print("Entrez le nouveau Nombre de valeurs lues : ");
				nbChan = scanner.nextInt();
				tag = Validation.validnbr(nbChan, 5);
			}
			
			capteur.typeMesure = type;
			capteur.nbreChannel  = nbChan;
			
			datab.updateValuesCapteur(capteur);
			
			listCapteurs.set(position, capteur);
			
			System.out.println("Valeurs modifiées");
			
		} catch (InputMismatchException e) {
			System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
		}
		
			
		}
	

				// Gestion liste Actionneurs



	// ajouter un Actionneur à la liste
    public void addActionneur(Scanner scanner, Actionneur actionneur, Database datab, int idApp) {

		actionneur = actionneur.ActionneurInfo(scanner, actionneur);
  
		 long id = datab.insertvaluesActionneur(actionneur, idApp);

		 actionneur.id = (int) id; 

        listActionneurs.add(actionneur);

    }

    
	 // show les noms de tous les Actionneurs de la liste
	public void showActionneurs() {
        for (Actionneur actionneur:  listActionneurs) {
            System.out.println(actionneur.id + "  - " + actionneur.name);
        }

        if  (listActionneurs.isEmpty()) {
            System.out.println("Aucun appareil dans cette liste");
            }
    }

    // Selectionner un actionneur de la liste

    public Actionneur SelectActionneur(Scanner scanner){
        
        showActionneurs();

        System.out.println("Entrez le nom de l'actionneur à sélectionner");
		String name = scanner.next(); 
        for (Actionneur actionneur: listActionneurs) {
            if (actionneur.name == name){
                return actionneur;
            }
        }

        return null; // si aucun appareil ne correspond
    }

	public int getPositionA(Actionneur actionneur){
        
        int position = listActionneurs.indexOf(actionneur);
        return position; 
    }

	//  supprimer un actionneur de la liste
    public void dropActionneur(Actionneur actionneur, Database datab) {

		datab.dropActionneur(actionneur);
        listActionneurs.remove(actionneur);
    }


	public void updateActionneur( Scanner scanner, Actionneur actionneur, int position, Database datab) {

			System.out.println("\n------------------------------");

			try{
				
				boolean  tag = false;
				String type = "";
		
				while (tag == false) {
					System.out.println("Entrez le nouveau type d'Action'");
					type = scanner.next();
					tag = Validation.validText(type, 25);
				}
				
				actionneur.typeAction = type;
				
				
				datab.updateValuesActionneur(actionneur);
				
				listActionneurs.set(position, actionneur);
				
				System.out.println("Valeurs modifiées");

			} catch (InputMismatchException e) {
				System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
			}

		}
		
		
		public void setID(int id){
			
        this.id = id;
    }

}


