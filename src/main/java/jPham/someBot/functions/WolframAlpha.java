package jPham.someBot.functions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class WolframAlpha extends Command {

    File result;
    URL website;

    Message message;
    String content;
    MessageChannel channel;

    WAEngine engine;
    WAQuery query;
    WAQueryResult queryResult;

    public WolframAlpha(){
        engine = new WAEngine();
        engine.setAppID(Constants.readWolfram());
        engine.addFormat("image");
        query = engine.createQuery();

        name = "WolframAlpha";
        aliases = new String[]{"WAlpha", "search"};
        help = "searches WolframAlpha based on prompt given";
    }

    @Override
    protected void execute(CommandEvent event){
        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        String[] parameterGrab = content.split(" ", 2);
        query.setInput(parameterGrab[1]);
        if (parameterGrab.length <= 1){
            channel.sendMessage("Usage: " + Constants.prefix + "search <search term>").queue();
        } else {
            try {
                queryResult = engine.performQuery(query);
                if (queryResult.isError()) {
                    channel.sendMessage("something went wrong, please try again").queue();
                    System.out.println("Query error");
                    System.out.println("  error code: " + queryResult.getErrorCode());
                    System.out.println("  error message: " + queryResult.getErrorMessage());
                } else if (!queryResult.isSuccess()) {
                    channel.sendMessage("Query not understood, no results found, please try again").queue();
                    System.out.println("Query was not understood; no results available.");
                } else {
                    result = new File(parameterGrab[1] + ".jpg");
                    website = new URL((engine.toURL(query)).replaceFirst("query", "simple"));
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(result);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    channel.sendFile(result).queue();
                    fos.close();
                    rbc.close();
                }
            } catch (Exception e) {
                channel.sendMessage("something went wrong, please try again").queue();
                e.printStackTrace();
            }
        }

    }

}
