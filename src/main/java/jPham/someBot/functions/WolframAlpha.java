package jPham.someBot.functions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.awt.Image;


public class WolframAlpha extends Command {

    Message message;
    String content;
    MessageChannel channel;
    byte[] image;

    public WolframAlpha() {
        name = "WolframAlpha";
        aliases = new String[]{"WAlpha", "search", };
        help = "Searches the Wolfram Alpha database with given search query";
    }
    @Override
    protected void execute(CommandEvent event){

        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        String[] parameterGrab = content.split(" ", 2);

        if (parameterGrab.length <= 1){
            channel.sendMessage("Usage: " + Constants.prefix + "filter <word>").queue();
        } else {
            try {
                image = Files.readAllBytes(ImageApi.call(parameterGrab[1], new DefaultImageParameters().setAppId(Constants.readWolfram())));
                channel.sendFile(image, parameterGrab[1]);
                channel.sendMessage("ok");
            } catch (Exception e) {
                channel.sendMessage("Lookup failed... please try again");
            }
        }


    }
}
