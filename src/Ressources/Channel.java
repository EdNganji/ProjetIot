package Ressources;

import java.util.Scanner;
import java.util.InputMismatchException;

import Code.Validation;



public class Channel {

    public  String name;
    public int id;
    public String unit;

    // Constructeur par défaut
    public Channel(String name, int id, String unit){
        this.name = name;
        this.id = id;
        this.unit = unit;
    }

    public Channel ChannelInfos(Scanner scanner, Channel channel){

        

        try{
            boolean  tag = false;
    
            while (tag == false) {
                System.out.print("Nom du channel : ");
                channel.name = scanner.next();
                tag = Validation.validText(name, 25);
            }
            
            tag = false;
    
            while (tag == false) {
                System.out.print("Unité : ");
                channel.unit = scanner.next();
                tag = Validation.validText(unit, 10);
            }
            
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer une valeur adequate");
        }

        return channel; 
    
    }


    

        

    
}
