package bot.evan.tichenor.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/7/2017
 */
public class MagicConch implements CommandExecutor {

    private static final String[] ANSWERS = {
            "It is certain",
            "It is decidedly so",
            "Without a doubt",
            "Yes, definitely",
            "You may rely on it",
            "As I see it, yes",
            "Most likely",
            "Outlook good",
            "Yes",
            "Signs point to yes",
            "Reply hazy try again",
            "Ask again later",
            "Better not tell you now",
            "Cannot predict now",
            "Concentrate and ask again",
            "Don't count on it",
            "My reply is no",
            "My sources say no",
            "Outlook not so good",
            "Very doubtful"
    };

    @Command(aliases = {"8ball", "conch", "magicconch"}, description = "Ask it any yes/no question.")
    public String onCommand() {
        return ANSWERS[(int)(Math.random() * ANSWERS.length)] + '.';
    }
}
