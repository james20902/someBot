package jPham.someBot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class responses {

    public responses(MessageReceivedEvent event){

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (event.getAuthor().isBot()) return;
        if (content.startsWith("i'm") && content.contains("gay")) {
            if (Math.random() > 0.5) {
                channel.sendMessage("Hi " + content.substring(content.indexOf("i'm") + 4) + ", I'm someBot!").queue();
            } else {
                channel.sendMessage("no, YOU'RE gay, " + event.getAuthor().getName()).queue();
            }
        } else if (content.startsWith("i'm")) {
            try {
                channel.sendMessage("Hi " + content.substring(content.indexOf("i'm") + 4) + ", I'm someBot!").queue();
            } catch (StringIndexOutOfBoundsException exception) {
                channel.sendMessage("you're what?").queue();
            }
        } else if (content.contains("gay")) {
            channel.sendMessage("no, YOU'RE gay, " + event.getAuthor().getName()).queue();
        }
    }

}
