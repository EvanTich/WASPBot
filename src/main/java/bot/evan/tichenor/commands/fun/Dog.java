package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.json.JSONObject;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 4/5/2017
 */
public class Dog implements CommandExecutor {
    private static final String API = "https://random.dog/woof.json";

    private static final String[] woofs = {"Woof-woof", "Arf-arf", "Ruff-ruff", "Bow-wow", "Bau-bau", "Gav-gav", "Vov-vov"};

    @Command(aliases = {"dog", "doggo", "pupper"}, description = "Gives a random dog picture.")
    public void onCommand(Message message, Server server) {
        String dog = randomDog();
        while(dog.contains(".mp4"))
            dog = randomDog();
        message.reply("", Utility.embedImage(randomTitle(), dog, dog, server));
    }

    /**
     * @return a random dog link
     */
    private String randomDog() {
        return new JSONObject(Utility.readHTTPSURL(API)).getString("url");
    }

    private String randomTitle() {
        return woofs[(int)(woofs.length * Math.random())];
    }
}
