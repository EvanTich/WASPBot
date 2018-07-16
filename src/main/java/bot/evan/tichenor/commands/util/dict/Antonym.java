package bot.evan.tichenor.commands.util.dict;

import bot.evan.tichenor.utils.JDictionary;
import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.List;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/18/2017
 */
public class Antonym implements CommandExecutor {

    @Command(aliases = {"antonym", "ants", "antonyms", "ant"}, description = "Shows the antonyms of the specified word.", usage = "!antonym [word]")
    public String onCommand(String[] args) {
        if(args.length == 0)
            return "Error: No word";

        List<String> ants = JDictionary.getAntonyms(args[0]);

        return Utility.codeBlock(ants);
    }
}
