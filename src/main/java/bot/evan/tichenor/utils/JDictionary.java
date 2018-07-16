package bot.evan.tichenor.utils;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/18/2017
 */
public class JDictionary {

    private static final String
            THESAURUS = "http://www.thesaurus.com/browse/",
            DICTIONARY = "http://wordnetweb.princeton.edu/perl/webwn?s=";

    public static List<String> getDefinitions(String word) {
        if(word.contains(" "))
            return Collections.singletonList("Term must be a single word");

        try {
            Document doc = Jsoup.connect( String.format("%s%s", DICTIONARY, word) ).get();

            Elements types = doc.getElementsByTag("h3");
            Elements defs = doc.getElementsByTag("ul");

            List<String> definitions = new ArrayList<>();

            for(int i = 0; i < types.size(); i++) {
                String type = types.get(i).text();
                String ul = defs.get(i).text();

                // regex doesn't work with nested parentheses
                Matcher matcher = Pattern.compile("\\(([^()]*|\\([^()]*\\))*\\)").matcher(ul);

                HashMap<String, Integer> numbers = new HashMap<>();

                while(matcher.find()) {
                    String def = matcher.group();
                    def = def.substring(1, def.length() - 1);
                    if (def.length() > 5 || def.contains(" ")) {
                        if (!numbers.containsKey(type))
                            numbers.put(type, 0);

                        if (numbers.get(type) < 5) {
                            numbers.put(type, numbers.get(type) + 1);
                            definitions.add(String.format("%s: %s", type, def));
                        }
                    }
                }
            }

            return definitions;
        } catch (IOException | IndexOutOfBoundsException e) {
            // remove later
        }

        return Collections.singletonList( String.format("%s must be a valid English word.", word) );
    }

    public static List<String> getSynonyms(String word) {
        List<String> syns = getThesaurus("div#filters-0", "synonyms", word);

        if(syns.contains(word) && syns.size() == 1)
            return syns;

        for(int i = 0; i < syns.size(); i++)
            syns.set(i, syns.get(i).substring(0, syns.get(i).length() - 4));

        return syns;
    }

    public static List<String> getAntonyms(String word) {
        return getThesaurus("section.antonyms", "antonyms", word);
    }

    private static List<String> getThesaurus(String thing, String name, String word) {
        try {
            Document doc = Jsoup.connect( String.format("%s%s", THESAURUS, word) ).get();
            Elements e1 = doc.select(thing);
            if(e1.size() == 0)
                return Collections.singletonList(word);

            Elements elements = e1.get(0).getElementsByTag("li");

            // make it so there will only be 5 or less
            while(elements.size() > 5)
                elements.remove(5);

            return elements.stream().map(Element::text).collect(Collectors.toList());
        } catch (HttpStatusException e) {
            return Collections.singletonList(word);
        } catch (IOException e) {
            // e.printStackTrace(); // remove later
            // dis bad
        }

        return Collections.singletonList( word );
    }
}
