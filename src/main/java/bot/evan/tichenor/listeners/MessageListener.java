package bot.evan.tichenor.listeners;

import bot.evan.tichenor.utils.BotConstants;
import bot.evan.tichenor.WASPBot;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.MessageEditListener;
import de.btobastian.javacord.listener.message.TypingStartListener;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class MessageListener implements MessageCreateListener, MessageEditListener, TypingStartListener {

    private static long WILLIAM_TIME = 0;
    private static long WILLIAM_AFK_TIME = 1800000;

    public void onMessageCreate(DiscordAPI api, Message message) {

        String str = message.toString().toLowerCase();

        if(message.getMentions().contains(WASPBot.getBot())) {
            boolean shouldReply = false;
            for(String s : BotConstants.HELLO_MESSAGES)
                if(str.contains(s))
                    shouldReply = true;

            if (str.contains("thanks"))
                message.reply("No problem!");
            else if(str.contains("lol what"))
                message.reply("I know, right?");
            else if(str.contains("why tho"))
                message.reply("", new EmbedBuilder().setTitle("Why tho?").setImage("http://i.imgur.com/OZDObmE.jpg"));
            else if(str.contains("mmmmm.jpg"))
                message.reply("", new EmbedBuilder().setTitle("mmmmm.jpg").setImage("http://i.imgur.com/8Ts7YhF.jpg"));
            else if(str.contains("i love you"))
                message.reply("", new EmbedBuilder().setTitle("I love you.").setImage("http://i.imgur.com/7HhdBDj.png"));
            else if(shouldReply)
                message.reply(BotConstants.HELLO_MESSAGES[ (int)(Math.random() * BotConstants.HELLO_MESSAGES.length) ]);
        }

    }

    public void onMessageEdit(DiscordAPI api, Message message, String s) {

    }

    public void onTypingStart(DiscordAPI api, User user, Channel channel) {
        // 30 mins //
        if(user.getId().equals(BotConstants.WILLIAM_ID)) {
            if(WILLIAM_TIME + WILLIAM_AFK_TIME < System.currentTimeMillis())
                channel.sendMessage( String.format("WEEWOO WEEWOO %s ALERT!", user.getName().toUpperCase()) );
            WILLIAM_TIME = System.currentTimeMillis();
        }
    }
}
