package Code;

import java.util.InputMismatchException;
import java.util.Scanner;
import Ressources.*;

import Data.Database;

public class App {
    public static void main(String[] args) throws Exception {
        
       

        Database datab = new Database();

         datab.CheckDatabase();

         

         Scanner scanner = new Scanner(System.in);

         Appareil appareil = new Appareil(datab);
        
            try{

            while (true) {

                System.out.println("Menu:");
                System.out.println("1. Gestion des appareils");
                System.out.println("2. Gestion des channels");
                System.out.println("3. Lectures");
                System.out.println("4. Quitter");

                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1:

                        System.out.println("Gestion des appareils");
                        System.out.println("1. Ajouter un appareil");
                        System.out.println("2. Afficher tous les appareils");
                        System.out.println("3. modifier un appareil");
                        System.out.println("4. Supprimer un appareil");
                        System.out.println("5. Quitter");
            
                        switch (choice) {
                            case 1:
                                appareil.addAppareil( scanner, appareil);
                                
                                break;

                            case 2:
                                appareil.listAppareil();
                                break;

                            case 3:

                            System.out.println("Modifier Un appareil");
                            System.out.println("1. Modifier les informations");
                            System.out.println("2. Gestion des Capteurs");
                            System.out.println("3. Gestion des Actionneurs");
                            System.out.println("4. Quitter");
                
                            switch (choice) {
                                case 1:
                                    
                                    
                                    break;
    
                                case 2:
                                    
                                    break;
    
                                case 3:
                                    
                                    System.out.println("Gestion des Capteurs");
                                    System.out.println("1. Ajouter un Capteur");
                                    System.out.println("2. Afficher tous les capteurs");
                                    System.out.println("3. modifier les informations d'un capteur");
                                    System.out.println("4. Supprimer un capteur");
                                    System.out.println("5. Quitter");

                                    switch (choice) {
                                        case 1:
                                            
                                            
                                            break;

                                        case 2:
                                            
                                            break;

                                        case 3:
                                            

                                        case 4:
                                        
                                            break;

                                        case 5:
                                            
                                            break;
                                            
                                        default:
                                            System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                            
                                    }
                                
                                    break;
    
                                case 4:
                                    
                                    System.out.println("Gestion des Actionneurs");
                                    System.out.println("1. Ajouter un Actionneur");
                                    System.out.println("2. Afficher tous les Actionneurs");
                                    System.out.println("3. modifier les informations d'un Actionneur");
                                    System.out.println("4. Supprimer un Actionneur");
                                    System.out.println("5. Quitter");

                                    switch (choice) {
                                        case 1:
                                            
                                            
                                            break;

                                        case 2:
                                            
                                            break;

                                        case 3:
                                            

                                        case 4:
                                        
                                            break;

                                        case 5:
                                            
                                            break;
                                            
                                        default:
                                            System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                            
                                    }
                                    break;
                                    
                                default:
                                    System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                    
                            }
                                break;

                            case 4:
                            appareil.dropAppareil(scanner);
                                break;

                            case 5:
                                break;
                                
                            default:
                                System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                
                        }
                        
                        break;

                    case 2:
                        
                        System.out.println("Gestion des channels");
                        System.out.println("1. Ajouter un channel");
                        System.out.println("2. Afficher tous les channels");
                        System.out.println("3. modifier les informations d'un channel");
                        System.out.println("4. Supprimer un channel");
                        System.out.println("5. Quitter");
            
                    
                        switch (choice) {
                            case 1:
                                
                                
                                break;

                            case 2:
                                
                                break;

                            case 3:
                                

                            case 4:
                            
                                break;

                            case 5:
                                
                                break;
                                
                            default:
                                System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                
                        }
                       break;

                    case 3:

                        System.out.println("Gestion des Lectures");
                        System.out.println("1. Consulter les lectures");
                        System.out.println("2. Quitter");
            
                        switch (choice) {
                            case 1:
                            System.out.println(" Consulter les lectures");
                                break;
        
                            case 2:
                                System.out.println("Au revoir");
                                System.exit(0);
                                break;
                           
                            default:
                                System.out.println("Choix non valide. Veuillez choisir une option valide.");
                                
                        }
                        break;

                    case 4:
                        System.out.println("Au revoir");
                        System.exit(0);
                        break;
                        
                        
                    default:
                        System.out.println("Choix non valide. Veuillez choisir une option valide.");
                        
                }




                System.out.println("1. Ajouter un appareil");
                System.out.println("2. Afficher tous les appareils");
                System.out.println("3. modifier les informations d'un appareil");
                System.out.println("4. Supprimer un appareil");
                System.out.println("5. Quitter");
    
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1:
                        appareil.addAppareil( scanner, appareil);
                        
                        break;

                    case 2:
                        appareil.listAppareil();
                        break;

                    case 3:
                        appareil.updateAppareil(scanner, appareil);
                        break;

                    case 4:
                       appareil.dropAppareil(scanner);
                        break;

                    case 5:
                        System.out.println("Au revoir");
                        System.exit(0);
                        break;
                        
                    default:
                        System.out.println("Choix non valide. Veuillez choisir une option valide.");
                        
                }

               
            }

        }catch (InputMismatchException e) {
            System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer un entier.");
        }
    }

    
}
