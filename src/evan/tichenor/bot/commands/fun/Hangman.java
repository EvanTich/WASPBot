package bot.evan.tichenor.commands.fun;

import bot.evan.tichenor.utils.JDictionary;
import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Server;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import jdk.nashorn.api.scripting.URLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/7/2017
 */
public class Hangman implements CommandExecutor {

    class Game {
        private String word;
        private List<String> guesses;
        private boolean gameOn;

        private long lastTime;

        public Game(String word) {
            this.word = word;
            guesses = new ArrayList<>();
            gameOn = true;

            lastTime = System.currentTimeMillis();
        }

        public String guess(String word) {
            lastTime = System.currentTimeMillis(); // refresh timeout counter

            if(!Utility.isAlpha(word))
                return "Alphabetical characters only";
            if(word.length() == 1)
                return guess(word.charAt(0));

            guesses.add(word);

            if(word.equals(this.word))
                return word + " was the word!";
            return word + " was not the word.";
        }

        /**
         * Precondition: gameOn == true, game has to be on
         * @param guess the guessed character
         * @return the output
         */
        public String guess(char guess) {
            String c = guess + "";
            if(guesses.contains(c))
                return "That character was already said.";

            guesses.add(c);

            if(word.contains(c))
                return c + " was correct!";
            return c + " was incorrect.";
        }

        public boolean won() {
            String uniques = "";
            for(char c : word.toCharArray()) {
                if(!uniques.contains(c + ""))
                    uniques += c;
            }
            int unique = uniques.length();

            int count = 0;
            for(String s : guesses)
                if(word.contains(s))
                    count++;
                else if(word.equals(s))
                    return true;

            return count == unique;
        }

        public String end() {
            gameOn = false;
            return "The game was ended. " + getWord();
        }

        private String getWord() {
            return "The word was " + word + '.';
        }

        public boolean hasTimedOut() {
            return System.currentTimeMillis() - lastTime > 450000; // 7.5 mins timeout
        }

        private String getBlanks() {
            StringBuilder str = new StringBuilder();
            // show word blanks
            for(int i = 0; i < word.length(); i++) {
                if(guesses.contains(word.charAt(i) + ""))
                    str.append(word.charAt(i)).append(' ');
                else str.append("_ ");
            }

            return str.toString();
        }

        /**
         * so terribly long
         * @param incorrect number of incorrect guesses
         * @return the hanging stand + person
         */
        private String getHangman(int incorrect) {
            StringBuilder str = new StringBuilder();

            str.append("    ------ \n");
            str.append("   | /    |\n");

            str.append("   |/     ");
            if(incorrect >= 1)
                str.append("O");
            str.append('\n');

            str.append("   |     ");
            if(incorrect >= 3)
                str.append('/');
            else str.append(' ');
            if(incorrect >= 2)
                str.append('|');
            else str.append(' ');
            if(incorrect >= 4)
                str.append('\\');
            else str.append(' ');
            str.append('\n');

            str.append("   |      ");
            if(incorrect >= 5)
                str.append('|');
            else str.append(' ');
            str.append('\n');

            str.append("   |     ");
            if(incorrect >= 6)
                str.append('/');
            else str.append(' ');
            str.append(' ');
            if(incorrect >= 7)
                str.append('\\');
            else str.append(' ');
            str.append('\n');

            str.append("   |\n");
            str.append("___|___");

            return str.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Game))
                return false;

            Game other = (Game) obj;
            return word.equals(other.word) &&
                    guesses.equals(other.guesses) &&
                    gameOn == other.gameOn;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();

            // show hangman + guesses
            List<String> incorrect = guesses.stream().filter(x -> !word.contains(x + "")).collect(Collectors.toList());
            int remaining = 7 - incorrect.size();

            str.append(getHangman(incorrect.size())).append('\n');

            str.append(remaining).append(" guesses remaining! Wrong guesses: ");

            guesses.forEach(x -> {
                if(!word.contains(x))
                    str.append(x).append(' ');
            });

            str.append('\n');

            str.append(getBlanks());

            if(won()) {
                gameOn = false;
                str.append("\nYou win!");
            } else if(remaining <= 0) {
                gameOn = false;
                str.append('\n').append(getWord());
            }

            return str.toString();
        }
    }

    private static final String RANDOM_WORD_LINK = "http://www.setgetgo.com/randomword/get.php";

    private HashMap<Server, Game> games;

    public Hangman() {
        games = new HashMap<>();
    }

    @Command(aliases = {"hangman"}, description = "Play a hangman game.", usage = "!hangman [play, end, guess] <word>", privateMessages = false)
    public String onCommand(String[] args, Server server) {
        if(args.length >= 1) {
            String command = args[0];

            Game game = null;
            if(games.containsKey(server))
                game = games.get(server);

            switch(command) {
                case "play":
                case "start":
                    if(games.containsKey(server)) // if server had a game
                        games.remove(server);

                    String word = loadWord();
                    while(JDictionary.getDefinitions(word).get(0).equals(word + " must be a valid English word."))
                        word = loadWord();

                    games.put(server, new Game(word));
                    return "Game started! 10 guesses remaining.";
                case "end":
                case "stop":
                    if(game != null) {
                        games.remove(server);
                        return Utility.codeBlock(game.end());
                    } else return "There is no game on for this server.";
                case "guess":
                    if(game == null)
                        return "There is no hangman game going on. Use (!hangman start) to start a game.";
                    else if(args.length < 2)
                        return "No guess.";

                    if(game.hasTimedOut())
                        return "The game has timed out. Please start a new one.";
                    else if(game.gameOn)
                        return Utility.codeBlock(game.guess(args[1]), game.toString());
                    return "There is no hangman game going on. Use (!hangman start) to start a game.";
            }

            if(game != null && !game.gameOn)
                games.remove(server);

            return "Incorrect input.";
        }

        return "No input";
    }

    private String loadWord() {
        try {
            BufferedReader scan = new BufferedReader(new URLReader(new URL(RANDOM_WORD_LINK)));

            return scan.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("couldn't read, robots can't read.");
            // error
        }

        return "";
    }
}
