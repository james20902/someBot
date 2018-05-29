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
    public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
        JDA api = new JDABuilder(AccountType.BOT).setToken(Constants.readToken()).buildBlocking();
        api.addEventListener(new WordFilter());
        Constants.updateFilter();
    }


}
