package jPham.someBot.functions;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class RemoveFilter implements CommandInterface {
    public void command(String oldWord, MessageChannel channel){
        String oldWordConvert = oldWord.replaceAll("\\W","").toLowerCase();
        if (oldWordConvert.isEmpty()){
            channel.sendMessage("Usage: " + Constants.prefix + "unfilter <word>");
        } else if (Constants.filter.contains(oldWordConvert)) {
            try {
                BufferedReader file = new BufferedReader(new FileReader("filter.txt"));
                String line;
                String input = "";
                while ((line = file.readLine()) != null) {
                    //System.out.println(line);
                    if (line.contains(oldWordConvert)) {
                        line = "";
                    }
                    input += line;
                }
                FileOutputStream File = new FileOutputStream("filter.txt");
                File.write(input.getBytes());
                file.close();
                File.close();
                Constants.updateFilter();
                channel.sendMessage("Word Removed!").queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            channel.sendMessage("Word not found...").queue();

        }
    }
}
