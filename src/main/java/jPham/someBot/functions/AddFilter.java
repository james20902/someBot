package jPham.someBot.functions;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class AddFilter implements CommandInterface{
    public void command(String newWord, MessageChannel channel){
        String newWordConvert = newWord.replaceAll("\\W","").toLowerCase();
        if (newWord.isEmpty()){
            channel.sendMessage("Usage: " + Constants.prefix + "filter <word>");
        } else if (Constants.filter.contains(newWordConvert)){
            channel.sendMessage("This word already exists within the filter!").queue();
        } else {
            BufferedWriter bw = null;
            try {
                // APPEND MODE SET HERE
                bw = new BufferedWriter(new FileWriter("filter.txt", true));
                bw.newLine();
                bw.write(newWordConvert);
                bw.flush();
                Constants.updateFilter();
                channel.sendMessage("Word added!").queue();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {                       // always close the file
                if (bw != null) try {
                    bw.close();
                } catch (Exception e) {
                    // just ignore it
                }
            }
        }


    }
}
