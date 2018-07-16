package bot.evan.tichenor.commands.util;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 4/2/2017
 */
@Deprecated
public class HumbleBundle implements CommandExecutor {

    @Command(aliases = {"hb", "humble", "bundle", "humblebundle"}, description = "Gets the current game bundle from Humble Bundle.")
    public String onCommand(String[] args) {
        String url = "https://www.humblebundle.com/";
        if(args.length > 0)
            url = args[0];

        try {
            Document doc = Jsoup.connect(url).get();

            StringBuilder str = new StringBuilder();

            String title = doc.title();
            title = title.replaceAll("\\((.*)\\)", "").trim();

            str.append("# ").append(title).append(" #\n");

            Elements mainContent = doc.select("div.main-content-row.dd-game-row.js-nav-row");

            for(Element element : mainContent) {
                /*
                    Will look like:
                    HEADER
                    ----
                    GAME
                    GAME
                    GAME

                    HEADER...
                 */
                str.append(
                        element.select("h2.dd-header-headline").get(0).text()
                ).append("\n======================\n");

                Elements names = element.select("div.dd-image-box-caption");

                if(names.size() > 0)
                    names.forEach(
                        x -> {
                            Elements span = x.getElementsByTag("span");
                            if(span.isEmpty())
                                str.append(x.text());
                            else
                                str.append(span.get(0).text());
                            str.append('\n');
                        }
                    );

                str.append('\n');
            }

            return '<' + url + '>' + Utility.codeBlockType("markdown", str.toString());
        } catch(HttpStatusException e) {
            return "Error: " + url + " is not a correct Humble Bundle link";
        } catch (IOException | IndexOutOfBoundsException e) {
            // poo
        }

        return "Unknown Error";
    }
}
