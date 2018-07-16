package bot.evan.tichenor.commands.admin;

import bot.evan.tichenor.utils.BotConstants;
import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.MessageHistory;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.Collection;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/14/2017
 */
public class Prune implements CommandExecutor {

    @Command(aliases = {"prune", "cut"}, description = "Prunes x amount of messages", usage = "!prune [#]", privateMessages = false, showInHelpPage = false)
    public String onCommand(String[] args, Channel channel, User user) {
        /* TODO: make it work
        for(Role role : user.getRoles(server)) {
            PermissionState state = role.getPermissions().getState(PermissionType.MANAGE_MESSAGES);
            if(state != PermissionState.ALLOWED)
                return "You cannot perform this action. (Insufficient permissions)";
        } */

        if(!user.getId().equals(BotConstants.OWNER_ID))
            return "";

        int x = Utility.parseInt(args, 0, 0);

        if(x == 0)
            return "Error, #<0 or it didn't exist in the first place.";

        MessageHistory history = Utility.getFuture(channel.getMessageHistory(x));

        if(history == null)
            return "There was an anticipated, but unexpected error.";

        Collection<Message> messages = history.getMessages();

        if(messages == null)
            return "Error: no messages to prune.";

        messages.forEach(Message::delete);

        return String.format("Pruned %d messages.", messages.size());
    }
}
