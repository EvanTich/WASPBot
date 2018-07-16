package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.json.JSONObject;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 6/10/2017
 */
public class Bird implements CommandExecutor {

    private static final String API = "https://random.birb.pw/tweet.json";

    @Command(aliases = {"bird", "birb"}, description = "Gives a random bird picture.")
    public void onCommand(Message message, Server server) {
        String bird = randomBird();
        message.reply("", Utility.embedImage("Tweet", bird, bird, server));
    }

    /**
     * @return a random bird link
     */
    private String randomBird() {
        return "https://random.birb.pw/img/" + new JSONObject(Utility.readHTTPSURL(API)).getString("file");
    }
}
