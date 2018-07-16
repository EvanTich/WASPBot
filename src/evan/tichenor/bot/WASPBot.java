package bot.evan.tichenor;

import bot.evan.tichenor.commands.admin.*;
import bot.evan.tichenor.commands.fun.*;
import bot.evan.tichenor.commands.util.*;
import bot.evan.tichenor.commands.util.dict.*;
import bot.evan.tichenor.commands.*;

import bot.evan.tichenor.listeners.MessageListener;
import bot.evan.tichenor.utils.BotConstants;
import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.*;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;

import java.security.Security;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/4/2017
 *
 * If you were wondering: WASP is short for Walton App and Software Programming, a club from my high school.
 *
 * This Discord bot works, but is not viable for more than one server (or any at this time).
 */
public class WASPBot {

    private static DiscordAPI api;

    private static long startTime;

    public WASPBot(String token) {
        api = Javacord.getApi(token, true);

        startTime = System.currentTimeMillis();

        addSSLProvider();

        api.connect(new FutureCallback<DiscordAPI>() {
            public void onSuccess(DiscordAPI discordAPI) {
                api.setGame("with the Turing Test"); // Playing...
                api.setAutoReconnect(true);

                // set up normal listeners below here
                api.registerListener(new MessageListener());

                // specific commands below here
                CommandHandler handler = new JavacordHandler(api);

                handler.setDefaultPrefix("!");

                //handler.registerCommand(new Card());

                // info
                handler.registerCommand(new Help(handler));
                handler.registerCommand(new BotInfo());
                handler.registerCommand(new Uptime());
                handler.registerCommand(new UserId());
                handler.registerCommand(new ServerId());
                handler.registerCommand(new Version());

                // fun
                // TODO: check these commands before putting on github
                handler.registerCommand(new Cat());
                handler.registerCommand(new CatFacts()); // not sure if working
                handler.registerCommand(new Dog());
                handler.registerCommand(new DogFacts()); // not sure if working
                handler.registerCommand(new Bird());
                // UP THERE
                handler.registerCommand(new Hangman()); // one game on multiple server problem, could fix with a map
                handler.registerCommand(new Leet());
                handler.registerCommand(new MagicConch());
                handler.registerCommand(new RPS());
                handler.registerCommand(new SuperSecret());

                // dict, relies heavily on websites not changing
                handler.registerCommand(new Define());
                handler.registerCommand(new Synonym());
                handler.registerCommand(new Antonym());
                handler.registerCommand(new Verbose());

                // util
                handler.registerCommand(new Brainfuck()); // interpreter for that cool language
                handler.registerCommand(new CoinFlip());
                handler.registerCommand(new Convert());
                // handler.registerCommand(new HumbleBundle()); // does not work anymore, relied heavily on websites not changing
                handler.registerCommand(new Ping()); // ping, pong
                handler.registerCommand(new Roll());
                handler.registerCommand(new BigText()); // probably my favorite command

                // really the only admin commands, and most are screwy
                handler.registerCommand(new ShutdownBot());
                handler.registerCommand(new RestartBot()); // I don't even know if this worked
                //handler.registerCommand(new Prune()); // broken (stops the bot) (only allows owner to use it right now)
            }

            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                shutdown("WASPBot failed to connect to API.");
            }
        });
    }

    public static synchronized void sendMessageBlocking(String userID, String str) {
        try {
            sendMessageBlocking(api.getUserById(userID).get(), str);
        } catch (ExecutionException | InterruptedException e) { }
    }

    public static synchronized void sendMessageBlocking(User user, String str) {
        Future<Message> message = user.sendMessage(str);
        long start = System.currentTimeMillis();
        while(!message.isDone() && start + 30000 > System.currentTimeMillis());
    }

    public static void shutdown(String message) {
        sendMessageBlocking(BotConstants.OWNER_ID, message);
        api.disconnect();

        System.exit(0);
    }

    public static void restart() {
        sendMessageBlocking(BotConstants.OWNER_ID, "Restarting...");
        api.setAutoReconnect(false);
        api.disconnect();

        api = null;

        System.gc();
        Main.restart();
    }

    public static DiscordAPI getApi() {
        return api;
    }

    public static User getBot() {
        return api.getYourself();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static String getUptime() {
        long millis = System.currentTimeMillis() - startTime;
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return "Uptime: " + days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
    }

    public static void addSSLProvider() {
        // I'll tell you something, I have no clue how to connect to https :)
        System.setProperty("java.protocol.handler.pkgs",
                "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }
}
