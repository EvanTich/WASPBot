package bot.evan.tichenor.commands.util.dict;

import bot.evan.tichenor.utils.JDictionary;
import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.List;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/11/2017
 */
public class Define implements CommandExecutor {

    @Command(aliases = {"define"}, description = "Define words", usage = "!define [word]")
    public String onCommand(String[] args) {
        if(args.length == 0)
            return "Error: No word";

        List<String> defs = JDictionary.getDefinitions(args[0]);

        return Utility.codeBlock(defs);
    }
}
