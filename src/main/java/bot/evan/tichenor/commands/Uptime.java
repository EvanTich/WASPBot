package bot.evan.tichenor.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import static bot.evan.tichenor.WASPBot.getUptime;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 4/8/2017
 */
public class Uptime implements CommandExecutor {

    @Command(aliases = {"uptime"}, description = "Gets WaspBot's uptime.")
    public String onCommand() {
        return getUptime();
    }
}
