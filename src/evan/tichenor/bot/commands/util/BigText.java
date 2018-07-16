package bot.evan.tichenor.commands.util;

import de.btobastian.javacord.entities.User;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 9/9/2017
 */
public class BigText implements CommandExecutor {

    @Command(aliases = {"big", "bigtext"}, description = "Make big text.", usage = "!big <text>")
    public String onCommand(String[] args) {
        StringBuilder nStr = new StringBuilder();
        for(String str : args) {
            for (char c : str.toCharArray())
                nStr.append(toBig(c));
            nStr.append("   ");
        }

        return nStr.toString();
    }

    public String toBig(char c) {
        c = Character.toLowerCase(c);
        if(c >= 'a' && c <= 'z')
            return String.format(":regional_indicator_%s:", c);
        else if (Character.isDigit(c))
            switch (c) {
                case '0':
                    return ":zero:";
                case '1':
                    return ":one:";
                case '2':
                    return ":two:";
                case '3':
                    return ":three:";
                case '4':
                    return ":four:";
                case '5':
                    return ":five:";
                case '6':
                    return ":six:";
                case '7':
                    return ":seven:";
                case '8':
                    return ":eight:";
                case '9':
                    return ":nine:";
            }

        return c + "";
    }
}
