package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import Data.Database;
import Ressources.*;


public class ListeChannels {

    public List<Channel> channels;

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

    // ajouter un channel à la liste
    public void add(Channel channel) {

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


    public Channel SelectChannel(Scanner scanner){

        afficherNomsChannels();

        System.out.println("Entrez le nom du channel sélectionné");

		String name = scanner.next(); 
        for (Channel channel: channels) {
            if (channel.name == name){
                return channel;
            }
        }

        return null; // si aucun appareil ne correspond
    }
    
    public int getPosition(Channel channel){
        
        int position = channels.indexOf(channel);
        return position; // si aucun appareil ne correspond
    }

    public void updateChannel( Scanner scanner, Channel channel, int position, Database datab) {
		System.out.println("\n------------------------------");

        channel.Infos();

		
		try{
		boolean  tag = false;

		String unit = scanner.next();
        
        while (tag == false) {

                System.out.println("Entrez la nouvelle unité'");
                unit = scanner.next();
                tag = Validation.validText(unit, 10);
            }
            
		

		channel.unit = unit;
        
        
		
		 datab.updateValuesChannel(channel);

		channels.set(position, channel);

		System.out.println("Valeurs modifiées");

    } catch (InputMismatchException e) {
        System.out.println("Erreur : Entrée invalide. Assurez-vous d'entrer de bonnes valeurs.");
    }

	}

      // supprimer un Channel de la liste
      public void dropChannel(Channel channel, Database datab) {

        datab.dropchannel(channel);

        channels.remove(channel);
    }

    
    
}
