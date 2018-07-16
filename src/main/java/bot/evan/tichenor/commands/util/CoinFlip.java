package bot.evan.tichenor.commands.util;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/7/2017
 */
public class CoinFlip implements CommandExecutor {

    @Command(aliases = {"flip", "coinflip"}, description = "Flip some coins.", usage = "!flip <times>")
    public String onCommand(String[] args) {
        StringBuilder str = new StringBuilder();

        int times = Utility.parseInt(args, 0, 1);

        if(times > 500)
            times = 500; // artificial max, more flips means a bigger string for discord, which may not be possible.

        for(; times > 0; times--) {
            double chance = Math.random();
            if(chance <= .49975)
                str.append("[H] ");
            else if(chance <= .9995)
                str.append("[T] ");
            else str.append("[S] "); // .05% chance
        }

        return str.toString();
    }
}
