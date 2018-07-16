package bot.evan.tichenor.commands.admin;

import bot.evan.tichenor.utils.BotConstants;
import bot.evan.tichenor.WASPBot;
import de.btobastian.javacord.entities.User;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class ShutdownBot implements CommandExecutor {

    @Command(aliases = {"shutdown", "stop", "exit"}, description = "Shuts the bot down.", showInHelpPage = false, requiresMention = true)
    public String onCommand(User user) {
        if(user.getId().equals(BotConstants.OWNER_ID)) {
            WASPBot.shutdown("Shutting down...");
            return "Goodnight...";
        }

        return ""; // nothing
    }
}
