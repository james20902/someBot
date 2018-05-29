package jPham.someBot.commands;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;


public class WordFilter extends ListenerAdapter {


    Message message;
    String content;
    MessageChannel channel;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(), event.getChannel().getName(), event.getAuthor(), event.getMessage().getContentDisplay());
        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();


        if (checkWords(content)){
            message.delete().queue();
            channel.sendMessage("Hey watch it!").queue();
        }

    }
    @Override
    public void onMessageUpdate(MessageUpdateEvent event){
        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        if (checkWords(content)){
            message.delete().queue();
            channel.sendMessage("Hey watch it!").queue();
        }
    }
    public boolean checkWords(String message){
        String newWordConvert = message.replaceAll("\\W","").toLowerCase();
        for(String i: Constants.filter){
            if (newWordConvert.contains(i)){
                return true;
            }
        }
        return false;
    }
}