package bot.evan.tichenor;

import static bot.evan.tichenor.utils.BotConstants.TOKEN;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/3/2017
 */
public class Main {

    private static WASPBot bot;

    public static void main(String[] args) {
        bot = new WASPBot(TOKEN);
    }

    public static WASPBot restart() {
        WASPBot.shutdown("Restarting WASPBot...");
        return ( bot = new WASPBot(TOKEN) );
    }

    public static WASPBot getBot() {
        return bot;
    }
}
