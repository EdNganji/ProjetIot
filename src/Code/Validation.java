package Code;
public class Validation {
    

    public static  boolean validText ( String text, int longeur ) {

      

        // Vérifie que le texte n'est pas vide
        if (text.isEmpty() || text.isEmpty()) {
            return false;
        }

        
        // Vérifie que le texte ne contient pas de caractères spéciaux
        for (char c : text.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '-' && c != '_') {
                return false;
            }
        }

        // Vérifie que l'ID contient bien 10 caractères
        if ( text.length() > longeur) {
            
            return false;
        } 

        
        return true;
    }

    public static boolean validID ( String Id ) {

        
        // Vérifie que l'ID' ne contient que des lettres et des chiffres
        for (char c : Id.toCharArray()) {
            if (!Character.isLetterOrDigit(c) ) {
                return false;
            }
        }

        // Vérifie que l'ID contient bien 10 caractères
        if ( Id.length() != 10 ) {
            
            return false;
        } 

        return true;

    }

    public static boolean validnbr ( int nbre, int max) {

        
       

        // Vérifie que l'ID contient bien 10 caractères
        if ( nbre >= max) {
            
            return false;
        } 

        if ( nbre <= 0) {
            
            return false;
        } 

        return true;

    }

}
