package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.io.IOException;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class Cat implements CommandExecutor {

    private static final String API = "http://aws.random.cat/meow";

    private static final String[] meows = {"Meow", "Mroww", "Meeeoooow", "Maaw", "Mew"};

    @Command(aliases = {"cat", "kitten", "kitty"}, description = "Gives a random cat picture.")
    public void onCommand(Message message, Server server) {
        String cat = randomCat();
        message.reply("", Utility.embedImage(randomTitle(), cat, cat, server));
    }

    /**
     * Returns a random cat link
     * @return
     */
    private String randomCat() {
        return Utility.readJSONFromURL(API).getString("file");
    }

    private String randomTitle() {
        return meows[(int)(Math.random() * meows.length)];
    }
}
