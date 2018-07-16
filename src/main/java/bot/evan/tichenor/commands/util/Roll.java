package bot.evan.tichenor.commands.util;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class Roll implements CommandExecutor {

    @Command(aliases = {"roll", "dice"}, description = "Roll the dice!", usage = "!roll [sides] <times>")
    public String onCommand(String[] args) {
        int sides = Utility.parseInt(args, 0, 6),
            times = Utility.parseInt(args, 1, 1);

        int sum = 0;
        for(; times > 0; times--)
            sum += ((int)(Math.random() * sides) + 1);

        return "[" + sum + "]";
    }
}
