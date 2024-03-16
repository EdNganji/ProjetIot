package Ressources;

import java.util.InputMismatchException;
import java.util.Scanner;


import Code.*;


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
	
	
	public void Infos() {

		System.out.println(this.name + "  "  + typeMesure + "  " + nbreChannel + "\n");
  
		
	}
	




}

