package bot.evan.tichenor.commands.util;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/4/2017
 */
public class Ping implements CommandExecutor {

    @Command(aliases = "ping", description = "Pong!")
    public String onCommand() {
        return "Pong!";
    }

    @Command(aliases = "pong", description = "Ping!")
    public String onPongCommand() {
        return "Ping!";
    }
}
