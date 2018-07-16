package bot.evan.tichenor.commands.util;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/6/2017
 */
public class Brainfuck implements CommandExecutor {

    private static final int MAX_SIZE = 512;
    private static final int MAX_CYCLE_COUNT = 10000;

    @Command(aliases = {"bf", "brainfuck"}, description = "Brainfuck interpreter. (Note: only 512 memory spaces)", usage = "!bf [code] [inputs...]")
    public String onCommand(String[] args) {
        if(args.length == 0)
            return "No input.";

        char[] code = args[0].toCharArray();
        char[] input = connectAll(1, args);

        return run(code, input);
    }

    private String run(char[] code, char[] input) {
        StringBuilder output = new StringBuilder();

        char[] data = new char[MAX_SIZE];

        int inIndex = 0,
            pointer = 0,
            cycle = 0;

        for(int i = 0; i < code.length; i++) {
            if(cycle++ >= MAX_CYCLE_COUNT)
                return error(i, "Cycle count exceeded the maximum clyde count of " + MAX_CYCLE_COUNT);

            switch(code[i]) {
                case '>':
                    pointer++;
                    break;
                case '<':
                    pointer--;
                    break;
                case '+':
                    data[pointer]++;
                    break;
                case '-':
                    data[pointer]--;
                    break;
                case '.':
                    output.append(data[pointer]);
                    break;
                case ',':
                    if(inIndex < input.length)
                        data[pointer] = input[inIndex++];
                    else return error(i, "Not enough input");
                    break;
                case '[': // while start
                    if(data[pointer] == 0) {
                        int depth = 1;
                        pointer++;
                        while(depth > 0) {
                            char c = code[++i];
                            if(c == '[')
                                depth++;
                            else if(c == ']')
                                depth--;
                        }
                    }
                    break;
                case ']': // while end
                    if(data[pointer] != 0) {
                        int depth = -1;
                        while(depth < 0) {
                            char c = code[--i];
                            if(c == '[')
                                depth++;
                            else if(c == ']')
                                depth--;
                        }
                    }
                    break;
            }

            if(pointer >= MAX_SIZE)
                return error(i, "Upward out of bounds");
            else if(pointer < 0)
                return error(i, "Downward out of bounds");
        }

        return output.toString();
    }

    private String error(int index, String type) {
        return String.format("Error at index %1d: %2s", index, type);
    }

    private char[] connectAll(int startIndex, String... args) {
        StringBuilder rtn = new StringBuilder();

        for(int i = startIndex; i < args.length; i++)
            rtn.append(args[i]);

        return rtn.toString().toCharArray();
    }
}
