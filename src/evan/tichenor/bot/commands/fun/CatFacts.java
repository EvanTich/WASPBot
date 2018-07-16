package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/8/2017
 */
public class CatFacts implements CommandExecutor {

    private static final String API = "https://catfact.ninja/fact";

    @Command(aliases = {"catfact", "catfacts"}, description = "Have some cat facts!")
    public String onCommand() {
        return loadFact();
    }

    private String loadFact() {
        JSONObject obj = Utility.readJSONFromURL(API);

        return obj.getString("fact");
    }
}
