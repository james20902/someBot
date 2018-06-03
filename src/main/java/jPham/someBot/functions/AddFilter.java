package jPham.someBot.functions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class AddFilter extends Command {

    Message message;
    String content;
    MessageChannel channel;

    public AddFilter() {
        name = "addfilter";
        aliases = new String[]{"filter"};
        help = "filters a word";
    }
    @Override
    protected void execute(CommandEvent event){

        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        String[] parameterGrab = content.split(" ", 2);
        String newWordConvert = parameterGrab[1].replaceAll("\\W","").toLowerCase();

        if (parameterGrab[1].length() < 1){
            channel.sendMessage("Usage: " + Constants.prefix + "filter <word>").queue();
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
