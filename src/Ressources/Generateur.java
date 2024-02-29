package Ressources;

import java.util.Random;
import java.util.Iterator;
import listes.ArrayList;
import Code.*;
import Ressources.*;
import Data.*;


public class Generateur {

    public ArrayList<Appareil> listAppareil;

    public static final String[] NAMES = {"Esp32", "mkr1000", "mkr1010", "Arduino Uno", "Arduino Rp2040"};
    public static final String[] TYPES = {"Type1", "Type2", "Type3", "Type4", "Type5"};

    public Generateur(){

        this.listAppareil = new ArrayList<>();
    }

    public static ArrayList<Appareil> generateAppareils(Database datab, ArrayList<Appareil> listAppareil  ) {
        
        Random random = new Random();

        for (int i = 0; i < 5; i++) {

            Appareil appareil = new Appareil();

            
            appareil.ipAdress = generateRandomIpAddress();
            appareil.name = NAMES[random.nextInt(NAMES.length)];
            appareil.type = TYPES[random.nextInt(TYPES.length)];
            appareil.etatFonct = "Fonctionnel"; // État fonctionnel par défaut
            


            long id = datab.insertvaluesAppareil( appareil);

		    appareil.id = (int) id;

		    // Ensuite on recupere l'id et on l'enregistre
            listAppareil.add(appareil);

        }

        return listAppareil;
    }

    public static String generateRandomIpAddress() {
        Random random = new Random();
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ipAddress.append(random.nextInt(256));
            if (i < 3) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }


    public static void generateData(Database datab  ) {
        
        Random random = new Random();

        for (int i = 0; i < 5; i++) {

            Data data = new Data();
            
            data.idCapteur = (random.nextInt(5) + 1); // ID du capteur aléatoire entre 1 et 5
            data.idChannel = (random.nextInt(5) + 1); // ID du capteur aléatoire entre 1 et 5
            data.readValue = (random.nextDouble() * 100); // Valeur de lecture aléatoire entre 0 et 100
            
            lectures[i] = lecture;
        }

        return lectures;
    }
            
            
            


            long id = datab.insertvaluesAppareil( appareil);

		    appareil.id = (int) id;

		    // Ensuite on recupere l'id et on l'enregistre
            listAppareil.add(appareil);

        }

        return listAppareil;
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
