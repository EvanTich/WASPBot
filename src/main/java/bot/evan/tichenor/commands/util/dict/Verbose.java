package bot.evan.tichenor.commands.util.dict;

import bot.evan.tichenor.utils.JDictionary;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import static bot.evan.tichenor.utils.Utility.removeNonAlpha;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 3/27/2017
 */
public class Verbose implements CommandExecutor {

    @Command(aliases = {"verbose"}, description = "Verbosifies the text.", usage = "!verbose [words]")
    public String onCommand(String[] args) {
        if(args.length == 0)
            return "Need words to verbose.";

        StringBuilder str = new StringBuilder();

        for(String s : args) {
            String biggest = "";
            for (String syns : JDictionary.getSynonyms(removeNonAlpha(s)))
                if(Math.abs(syns.length() - biggest.length()) <= s.length() / 2 && Math.random() > .75 || biggest.isEmpty())
                    biggest = syns;
            str.append(biggest).append(' ');
        }

        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}
