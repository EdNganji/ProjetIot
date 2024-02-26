package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Data.Database;
import Ressources.*;

public class ListeChannels {

    private List<Channel> channels;

    public ListeChannels() {
        channels = new ArrayList<>();
    }

    // ajouter un Channel à la liste
    public void addChannel(Scanner scanner, Channel channel, Database datab) {

        channel = channel.ChannelInfos(scanner, channel);
        long id = datab.insertvaluesChannel(channel);
        channel.id = (int)id;
        channels.add(channel);
    }

  

    // récupérer tous les Channels de la liste
    public List<Channel> getChannels() {
        return channels;
    }

    // afficher les noms de tous les Channels de la liste
    public void afficherNomsChannels() {
        for (Channel Channel : channels) {
            System.out.println(Channel.id + " - " + Channel.name);
        }

        if  (channels.isEmpty()) {
            System.out.println("Aucun channel.");
        }
    }

    // Selectionner un channel de la liste

    public Channel SelectChannel(int  id){
        for (Channel channel : channels) {
            if (channel.id == id){
                return channel;
            }
        }

        return null; // si aucun channel ne correspond
    }

      // supprimer un Channel de la liste
      public void dropChannel(Channel channel, Database datab) {

        datab.dropchannel(channel);

        channels.remove(channel);
    }

    
    
}
