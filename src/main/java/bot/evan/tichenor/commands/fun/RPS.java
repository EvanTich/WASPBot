package bot.evan.tichenor.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/6/2017
 */
public class RPS implements CommandExecutor {

    private static final String
            WIN = "You won!",
            LOSE = "You lose!",
            TIE = "Tie!";

    @Command(aliases = {"rps", "rock", "paper", "scissors"}, description = "Play rps vs the computer.", usage = "!rps [move]")
    public String onCommand(String command, String[] args) {
        int comp = random(), user;

        if(!command.equals("!rps"))
            user = getValue(command.substring(1));
        else if(args.length != 0)
            user = getValue(args[0]);
        else return "No input.";

        return determine(comp, user) + " I picked " + getMove(comp) + ".";
    }

    private String determine(int comp, int user) {
        // 0 - rock
        // 1 - paper
        // 2 - scissors

        if(comp == user)
            return TIE;
        else if(comp == 0 && user == 1 || comp == 1 && user == 2 || comp == 2 && user == 0)
            return WIN;
        else if(comp == 0 && user == 2 || comp == 1 && user == 0 || comp == 2 && user == 1)
            return LOSE;

        return "Error.";
    }

    private int random() {
        return (int)(Math.random() * 3);
    }

    private int getValue(String move) {
        char c = move.charAt(0);

        return c == 'r' ? 0 : (c == 'p' ? 1 : 2);
    }

    private String getMove(int x) {
        if(x == 0)
            return "rock";
        else if(x == 1)
            return "paper";
        return "scissors";
    }
}
