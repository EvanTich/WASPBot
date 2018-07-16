package bot.evan.tichenor.commands;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import static bot.evan.tichenor.utils.BotConstants.OWNER_ID;
import static bot.evan.tichenor.WASPBot.getUptime;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class BotInfo implements CommandExecutor {

    @Command(aliases = {"info", "i" }, description = "Shows some information about the bot.")
    public MessageBuilder onCommand(DiscordAPI api, Message message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("\u200b\n").appendMention(api.getYourself()).append(" was made by ");

        User owner;
        if(!message.isPrivateMessage() &&
                (owner = message.getChannelReceiver().getServer().getMemberById(OWNER_ID)) != null) {
            builder.appendMention(owner);
        } else builder.append("Ugff"); // don't hurt me :)

        builder.append(",\nis on ").append("" + api.getServers().size())
                .append(" servers, and serves ").append("" + api.getUsers().size())
                .append(" users.\n").append(getUptime());

        return builder;
    }
}
