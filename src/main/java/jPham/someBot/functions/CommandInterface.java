package jPham.someBot.functions;

import net.dv8tion.jda.core.entities.MessageChannel;

public interface CommandInterface {
   void command(String Word, MessageChannel message);
}
