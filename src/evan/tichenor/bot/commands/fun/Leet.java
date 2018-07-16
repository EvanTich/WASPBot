package bot.evan.tichenor.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class Leet implements CommandExecutor {

    @Command(aliases = {"leet"}, description = "Changes every word to leet speak.", usage = "!leet [words]")
    public String onCommand(String[] args) {
        StringBuilder str = new StringBuilder();

        for(String arg : args) {
            str.append(toLeet(arg)).append(" ");
        }

        return str.toString();
    }

    private String toLeet(String str) {
        StringBuilder rtn = new StringBuilder();

        str = str.replace("ck", "x");

        for(char c : str.toCharArray()){
            switch(c){
                case 'a':
                case 'A':
                    rtn.append("4");
                    break;
                case 'e':
                case 'E':
                    rtn.append("3");
                    break;
                case 'l':
                case 'L':
                case 'i':
                case 'I':
                    rtn.append("1");
                    break;
                case 'o':
                case 'O':
                    rtn.append("0");
                    break;
                case 'g':
                case 'G':
                    rtn.append("9");
                    break;
                case 'h':
                case 'H':
                    rtn.append("|-|");
                    break;
                case 'v':
                case 'V':
                    rtn.append("\\\\/");
                    break;
                case 'w':
                case 'W':
                    rtn.append("\\\\/\\\\/");
                    break;
                case 'm':
                case 'M':
                    rtn.append("/\\\\/\\\\");
                    break;
                case 'k':
                case 'K':
                    rtn.append("|<");
                    break;
                case 's':
                case 'S':
                    rtn.append("5");
                    break;
                case 't':
                case 'T':
                    rtn.append("7");
                    break;
                case 'z':
                case 'Z':
                    rtn.append("2");
                    break;
                default:
                    rtn.append(c);

            }
        }

        return rtn.toString();
    }
}
