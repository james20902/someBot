package jPham.someBot.commands;

import jPham.someBot.bot.Constants;
import jPham.someBot.functions.AddFilter;
import jPham.someBot.functions.CommandInterface;
import jPham.someBot.functions.RemoveFilter;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    Message message;
    String content;
    MessageChannel channel;

    AddFilter filter;
    RemoveFilter unfilter;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(), event.getChannel().getName(), event.getAuthor(), event.getMessage().getContentDisplay());
        message = event.getMessage();
        content = message.getContentStripped();
        channel = event.getChannel();

        filter = new AddFilter();
        unfilter = new RemoveFilter();

        command("filter", content.substring(8), filter);
        command("unfilter", content.substring(10), unfilter);

    }
    public void command(String command, String parameter,  CommandInterface whattodo){
        if (content.startsWith(Constants.prefix + command)){
            whattodo.command(parameter, channel);
        }
        
    }
}
