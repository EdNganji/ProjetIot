
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
       

        Database datab = new Database();

         datab.CheckDatabase();

         

         Scanner scanner = new Scanner(System.in);

         Appareil appareil = new Appareil(datab);
            try{
            while (true) {
                System.out.println("Menu:");
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
