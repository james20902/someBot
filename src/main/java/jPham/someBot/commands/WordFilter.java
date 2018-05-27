package jPham.someBot.commands;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;


public class WordFilter extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(), event.getChannel().getName(), event.getAuthor(), event.getMessage().getContentDisplay());
        Message message = event.getMessage();
        String content = message.getContentStripped();
        MessageChannel channel = event.getChannel();
        if (checkWords(content)){
            message.delete().queue();
            channel.sendMessage("Hey watch it!").queue();
        }

    }

    public boolean checkWords(String message){
        String newMessage = message.replaceAll("\\W","");
        System.out.println("Converted message: " + newMessage);

        Constants.nonowords.add("fuck");
        Constants.nonowords.add("bitch");

        for(String i: Constants.nonowords){
            if (newMessage.toLowerCase().contains(i)){
                return true;
            }
        }
        return false;
    }
}