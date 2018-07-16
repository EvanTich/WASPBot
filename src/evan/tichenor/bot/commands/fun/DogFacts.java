package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 4/18/2017
 */
public class DogFacts implements CommandExecutor {

    private static final String API = "https://dog.ceo/api/breeds/image/random";

    @Command(aliases = {"dogfact", "dogfacts"}, description = "Have some dog facts!")
    public String onCommand() {
        return loadFact();
    }

    private String loadFact() {
        JSONObject obj = Utility.readJSONFromURL(API);

        return obj.getString("message");
    }
}
