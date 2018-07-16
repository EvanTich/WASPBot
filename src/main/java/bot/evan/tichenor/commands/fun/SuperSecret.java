package bot.evan.tichenor.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 *
 * There is no real reason for this command, it's just here to be "secret".
 */
public class SuperSecret implements CommandExecutor {

    @Command(aliases = {"supersecret"}, showInHelpPage = false)
    public String onCommand() {
        return "Sneaky-Beaky like.";
    }
}
