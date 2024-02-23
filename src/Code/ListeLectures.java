package Code;

import java.util.ArrayList;
import java.util.List;
import Ressources.*;

public class ListeLectures {

    private List<Lecture> lectures;

    public ListeLectures() {
        lectures = new ArrayList<>();
    }

    // ajouter un Lectures à la liste
    public void addLectures(Lecture lecture) {
        lectures.add(lecture);
    }

    // supprimer un lectures de la liste
    public void droplectures(Lecture lecture) {
        lectures.remove(lecture);
    }

    // récupérer tous les lectures de la liste
    public List<Lecture> getlectures() {
        return lectures;
    }

    // afficher les noms de tous les lectures de la liste
    public void afficherNomslectures() {
        for (Lecture lecture : lectures) {
            System.out.println(lecture);
        }
    }
    
}
