

import java.util.Scanner;

import java.util.InputMismatchException;

public class Appareil {
	
    public String serNum;
    public String name;
    public String type;
    public String etatFonct;
	public Database datab;

	public Appareil(Database database){

		this.datab = database;
	}
    
	public Appareil appareilInfo(Scanner scanner, Appareil appareil){

		try{
		boolean  tag = false;
		while (tag == false) {
			System.out.print("Identifiant de l'appareil : ");
			appareil.serNum = scanner.next();
			tag = Validation.validID(appareil.serNum);
		}

		tag = false;

		while (tag == false) {
			System.out.print("Nom de l'appareil : ");
			appareil.name = scanner.next();
			tag = Validation.validText(appareil.name, 25);
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

}


