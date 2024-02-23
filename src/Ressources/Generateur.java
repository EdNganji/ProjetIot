package Ressources;


import java.util.Iterator;
import listes.ArrayList;


public class Generateur {
    private ArrayList<Capteur> listCapt;

    public Generateur(){

        this.listCapt = new ArrayList<>();
    }
        
    public void   ajouterCapteur(String type, String nom) throws Exception{
       if (type == null || nom==null){
        throw  new Exception("Type ou Nom ne peut pas etre vide");
        }
       Capteur cpt=new Capteur(type,nom);
       int i=0;
       while(i <this.listCapt.getTaille() && !this.listCapt.isEmpty())
           if(this.listCapt.getElement(i).getNom().equalsIgnoreCase(nom))
           throw new Exception("Ce capteur existe deja");
           else i++;
       this.listCapt.ajouteElement(cpt);
    }

    public Iterator<Capteur> getItCapteurs() {
        return this.listCapt.createIterator();
    }
    //permet d'obtenir le nombre total de capteurs dans la
    //liste des capteurs du generateur
    public int getTotalCapteurs() {
        return this.listCapt.getTaille();
    }
    
    /*
     * La methode suivante permet de supprimer un capteur a partir de son nom.
     * Si le capteur n'existe pas, une exception est lancée
     */


     






}
