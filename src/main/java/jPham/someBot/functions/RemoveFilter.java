package jPham.someBot.functions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class RemoveFilter extends Command{

    Message message;
    String content;
    MessageChannel channel;

    public RemoveFilter() {
        name = "removefilter";
        aliases = new String[]{"unfilter"};
        help = "filters a word";
    }
    @Override
    protected void execute(CommandEvent event){

        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        String[] parameterGrab = content.split(" ", 2);

        if (parameterGrab.length <= 1){
            channel.sendMessage("Usage: " + Constants.prefix + "unfilter <word>");
        } else {
            String oldWordConvert = parameterGrab[1].replaceAll("\\W","").toLowerCase();
            if (Constants.filter.contains(oldWordConvert)) {
                try {
                    BufferedReader file = new BufferedReader(new FileReader("filter.txt"));
                    String line;
                    String input = "";
                    while ((line = file.readLine()) != null) {
                        if (line.equals(oldWordConvert)) {
                            line = "";
                        }
                        input += line + "\n";
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
}
