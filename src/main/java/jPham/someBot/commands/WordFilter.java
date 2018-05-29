package jPham.someBot.commands;

import jPham.someBot.bot.Constants;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
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

        if (content.startsWith(Constants.prefix + "filter")){
            if (content.length() <= 7){
                channel.sendMessage("usage: !filter <word>").queue();
            } else {
                addWord(content.substring(8));
            }
        } else if(content.startsWith(Constants.prefix + "unfilter")){
            removeWord(content.substring(10));
        } else if (checkWords(content)){
            message.delete().queue();
            channel.sendMessage("Hey watch it!").queue();
        }

    }
    public void addWord(String newWord){
        String newWordConvert = newWord.replaceAll("\\W","").toLowerCase();
        if (Constants.filter.contains(newWordConvert)){
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
    public void removeWord(String oldWord){
        String oldWordConvert = oldWord.replaceAll("\\W","").toLowerCase();
        if (Constants.filter.contains(oldWordConvert)) {
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