package jPham.someBot.bot;

import jPham.someBot.commands.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter
{

    public static commands commands;
    public static responses responses;


    public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
        JDA api = new JDABuilder(AccountType.BOT).setToken(Constants.readToken()).buildBlocking();
        api.addEventListener(new Main());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),event.getTextChannel().getName(), event.getMember().getEffectiveName(),event.getMessage().getContentDisplay());
            if (content.startsWith(Constants.prefix)){
                commands = new commands(event);
            } else {
                responses = new responses(event);
            }
        }
    }
}
