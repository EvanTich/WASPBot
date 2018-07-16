package bot.evan.tichenor.commands.util;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/11/2017
 */
public class Convert implements CommandExecutor {

    @Command(aliases = {"convert"}, description = "Convert from numbers to numbers.", usage = "!convert [type] [#]")
    public String onCommand(String[] args) {
        return Utility.parseNumber(Utility.NumberLiteral.getType(args[0]), args[1]);
    }
}
