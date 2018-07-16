package bot.evan.tichenor.commands.admin;

import de.btobastian.javacord.Javacord;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/8/2017
 */
public class Version implements CommandExecutor {

    @Command(aliases = {"version", "v"}, showInHelpPage = false, requiresMention = true)
    public String onCommand() {
        return String.format("Javacord Version: %s", Javacord.VERSION);
    }
}
