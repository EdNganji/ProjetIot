package Code;

import java.util.ArrayList;
import java.util.List;
import Ressources.*;

public class ListeChannels {

    private List<Channel> channels;

    public ListeChannels() {
        channels = new ArrayList<>();
    }

    // ajouter un Channel à la liste
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    // supprimer un Channel de la liste
    public void dropChannel(Channel channel) {
        channels.remove(channel);
    }

    // récupérer tous les Channels de la liste
    public List<Channel> getChannels() {
        return channels;
    }

    // afficher les noms de tous les Channels de la liste
    public void afficherNomsChannels() {
        for (Channel Channel : channels) {
            System.out.println(Channel);
        }
    }

    
    
}
