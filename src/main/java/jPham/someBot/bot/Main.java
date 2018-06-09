package jPham.someBot.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;


import jPham.someBot.functions.AddFilter;
import jPham.someBot.functions.WolframAlpha;
import jPham.someBot.functions.WordFilter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix(Constants.prefix);
        builder.setOwnerId("181796145926766592");

        builder.addCommand(new AddFilter());
        //builder.addCommand(new RemoveFilter());
        builder.addCommand(new WolframAlpha());
        builder.setListener(new WordFilter());


        CommandClient client = builder.build();


        JDA api = new JDABuilder(AccountType.BOT).setToken(Constants.readToken()).buildBlocking();
        api.addEventListener(client);
        Constants.updateFilter();
    }


}
