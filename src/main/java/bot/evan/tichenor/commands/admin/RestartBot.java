package bot.evan.tichenor.commands.admin;

import bot.evan.tichenor.WASPBot;
import bot.evan.tichenor.utils.BotConstants;
import de.btobastian.javacord.entities.User;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 4/6/2017
 */
public class RestartBot implements CommandExecutor {

    @Command(aliases = {"restart"}, description = "Restarts the bot.", showInHelpPage = false, requiresMention = true)
    public String onCommand(User user) {
        if(user.getId().equals(BotConstants.OWNER_ID)) {
            WASPBot.restart();
            return "Restarting...";
        }

        return "";
    }
}
