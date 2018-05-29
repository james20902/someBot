package jPham.someBot.bot;

import jPham.someBot.commands.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args)
            throws LoginException, RateLimitedException, InterruptedException
    {
        JDA api = new JDABuilder(AccountType.BOT).setToken(Constants.readToken()).buildBlocking();
        api.addEventListener(new WordFilter());
        api.addEventListener(new Commands());
        Constants.updateFilter();
    }


}
