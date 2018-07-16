package bot.evan.tichenor.commands.admin;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

import java.util.List;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/13/2017
 */
public class UserId implements CommandExecutor {

    @Command(aliases = {"uid", "user", "userid"}, description = "Outputs the user's id.", usage = "!uid [name(s)]", showInHelpPage = false)
    public String onCommand(Message message, User user) {
        if(message.getMentions().size() == 0)
            return String.format("Your user id is: %s.", user.getId());

        StringBuilder str = new StringBuilder();

        List<User> users = message.getMentions();

        for(int i = 0; i < users.size(); i++) {
            str.append(users.get(i));
            if(i != users.size() - 1)
                str.append(", ");
        }

        return String.format("Here are the user(s): %s.", str.toString());
    }
}
