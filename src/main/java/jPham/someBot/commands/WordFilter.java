package jPham.someBot.commands;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;



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

        if (content.startsWith(Constants.prefix + "filter")){
            if (content.length() <= 7){
                channel.sendMessage("usage: !filter <word>").queue();
            } else {
                addWord(content.substring(6));
            }
        } else if (checkWords(content)){
            message.delete().queue();
            channel.sendMessage("Hey watch it!").queue();
        }

    }
    public void addWord(String newWord){
        for(String i: Constants.nonowords){
            if (i == newWord){
                channel.sendMessage("This word already exists within the filter!").queue();
                break;
            }
        }
        channel.sendMessage("Word added!").queue();
        Constants.nonowords.add(newWord);
    }

    public boolean checkWords(String message){
        String newMessage = message.replaceAll("\\W","");
        System.out.println("Converted message: " + newMessage);

        for(String i: Constants.nonowords){
            if (newMessage.toLowerCase().contains(i)){
                return true;
            }
        }
        return false;
    }
}