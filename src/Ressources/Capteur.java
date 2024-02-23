package Ressources;

import java.util.InputMismatchException;
import java.util.Scanner;

import Code.capteur;
import Code.Appareil;
import Code.Validation;

// Classe représentant un capteur
public class Capteur extends Composante {
    public String typeMesure;
    public int nbreChannel;

    public Capteur(String name, String typeMesure, int nbre) {
        super(name);
        this.typeMesure = typeMesure;
        this.nbreChannel = nbre; 
    }

    public void mesurer() {
        System.out.println("Le Capteur " + toString() + " mesure " + typeMesure);
    }

    // Méthodes spécifiques aux capteurs

    public Capteur capteurInfo(Scanner scanner, Capteur capteur){

		try{
		boolean  tag = false;
		
		while (tag == false) {
			System.out.print("Nom du capteur : ");
			capteur.name = scanner.next();
			tag = Validation.validText(capteur.name, 25);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Type de Mesure : ");
			capteur.typeMesure = scanner.next();
			tag = Validation.validText(capteur.typeMesure, 25);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Nombre de valeurs lues : ");
			capteur.nbreChannel = scanner.nextInt();
			tag = Validation.validnbr(capteur.nbreChannel, 5);
		}

	} catch (InputMismatchException e) {
		System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
	}

		return capteur;

	}
	
	public void addcapteur( Scanner scanner, Capteur capteur) {

		capteur = capteurInfo(scanner, capteur);
  
		// datab.insertvalues( capteur);
	}

    public void listCapteur() {
		System.out.println("\n------------------------------");

		// datab.getAppareil();
		
	}
	
	public void updateCapteur( Scanner scanner, Capteur capteur) {
		System.out.println("\n------------------------------");

		
		// datab.getAppareil();
		System.out.println("Entrez l'id du capteur à modifier");
		int id = Integer.parseInt(scanner.next());
		System.out.println("Entrez le nouvel etat de fonctionnement");
		String etat = scanner.next();

		// datab.updateValues(id, etat);
		
	}
	
	public void dropAppareil( Scanner scanner) {
		
		System.out.println("\n------------------------------");

		clearConsole();
		datab.getAppareil();
		System.out.println("Entrez l'id de l'appareil à supprimer");
		int id = Integer.parseInt(scanner.next());
		datab.dropAppareil(id);

	}
	

}

