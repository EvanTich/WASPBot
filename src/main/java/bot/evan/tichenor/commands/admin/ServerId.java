package bot.evan.tichenor.commands.admin;

import de.btobastian.javacord.entities.Server;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/13/2017
 */
public class ServerId implements CommandExecutor {

    @Command(aliases = {"sid", "server", "serverid"}, description = "Shows the current server id.", showInHelpPage = false)
    public String onCommand(Server server) {
        return String.format("You are in %s.", server.toString());
    }
}
