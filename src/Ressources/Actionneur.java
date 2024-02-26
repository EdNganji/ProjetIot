package Ressources;

import java.util.InputMismatchException;
import java.util.Scanner;

import Code.Validation;

// Classe représentant un actionneur
public class Actionneur extends Composante {
    public String typeAction;
    public int puissance;

    public Actionneur(String nom,  String typeAction) {
        super(nom);
        this.typeAction = typeAction;
    }

    public void infos() {
        System.out.println("L'Actionneur " + toString() + " effectue une action de type " + typeAction);
    }

    public void actionner(int value) {
        this.puissance = value;
        System.out.println("L' Actionneur " + toString() + " a recu la commande " + value);
    }


    public Actionneur ActionneurInfo(Scanner scanner, Actionneur actionneur){

		try{
		boolean  tag = false;
		
		while (tag == false) {
			System.out.print("Nom du actionneur : ");
			actionneur.name = scanner.next();
			tag = Validation.validText(actionneur.name, 25);
		}
		
		tag = false;

		while (tag == false) {
			System.out.print("Type d'Action' : ");
			actionneur.typeAction = scanner.next();
			tag = Validation.validText(actionneur.typeAction, 25);
		}
		
		

	} catch (InputMismatchException e) {
		System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
	}

		return actionneur;

	}


    
}
