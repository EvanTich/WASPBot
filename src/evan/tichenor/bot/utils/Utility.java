package bot.evan.tichenor.utils;

import bot.evan.tichenor.WASPBot;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import jdk.nashorn.api.scripting.URLReader;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/7/2017
 */
public class Utility {

    public enum NumberLiteral {
        Integer,
        Float,
        Hexadecimal,
        Octal,
        Binary,
        Character;

        public static NumberLiteral getType(String str) {
            str = str.toLowerCase();
            if(str.contains("bin"))
                return Binary;
            else if(str.contains("double") || str.contains("float"))
                return Float;
            else if(str.contains("hex"))
                return Hexadecimal;
            else if(str.contains("octal"))
                return Octal;
            else if(str.contains("char"))
                return Character;

            return Integer;
        }
    }

    /**
     *
     * @param args the command arguments
     * @param index the index of the integer to parse
     * @param rtn what it returns if it could not parse
     * @return the parsed integer
     */
    public static int parseInt(String[] args, int index, int rtn) {
        try {
            return Integer.parseInt(args[index]);
        } catch (Exception e) {
            /* Invalid input. */
            return rtn;
        }
    }

    /**
     * Stylizes the text(args) on new lines in an ```xml code block ```
     * @param args
     * @return stylized code block
     */
    public static String codeBlock(String... args) {
        return codeBlockType("xml", args);
    }

    public static String codeBlockType(String type, String... args) {
        StringBuilder str = new StringBuilder();

        str.append("```").append(type).append('\n');
        for(String s : args)
            str.append(s).append('\n');
        str.append("```");

        return str.toString();
    }

    public static String codeBlock(Collection<String> c) {
        return codeBlock(c.toArray(new String[c.size()]));
    }

    public static <T> List<T> toList(Collection<T> col) {
        List<T> list = new ArrayList<>();
        list.addAll(col);
        return list;
    }

    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read( new URL(path) );
        } catch (IOException e) {
            // bad
            return null;
        }
    }

    public static String getStringFromURL(String url) {
        try {
            URL u = new URL(url);
            u.openConnection();
            if(url.indexOf("https") == 0)
                return readAll(new BufferedReader(new InputStreamReader(u.openConnection().getInputStream())));
            return readAll(new BufferedReader(new URLReader(u)));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String readAll(Reader r) {
        StringBuilder str = new StringBuilder();
        try {
            int i;
            while ((i = r.read()) != -1)
                str.append((char) i);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static String readHTTPSURL(String httpsURL) {
        return getStringFromURL(httpsURL);
    }

    public static String getHTTPSContent(HttpsURLConnection con) {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            return readAll(r);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static JSONObject readJSONFromURL(String url) {
        JSONObject obj = null;
        try {
            BufferedReader scan = new BufferedReader(new URLReader(new URL(url)));

            String json = readAll(scan);

            obj = new JSONObject(json);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static JSONObject readJSONArrFromURL(String url) {
        JSONArray obj = null;
        try {
            BufferedReader scan = new BufferedReader(new URLReader(new URL(url)));

            String json = readAll(scan);
            json = json.substring(1, json.length() - 1);

            obj = new JSONArray(json);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return obj.getJSONObject(0);
    }

    public static <T> T[] subarray(T[] arr, int start) {
        return Arrays.copyOfRange(arr, start, arr.length);
    }

    public static boolean isAlpha(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isBinary(String str) {
        return str.matches("[01]+");
    }

    public static String removeNonAlpha(String str) {
        return str.replaceAll("[^A-Za-z]", "");
    }

    public static String parseNumber(NumberLiteral to, String s) {
        int base = 10;
        if(s.contains("0x"))
            base = 16;
        else if(isBinary(s))
            base = 2;
        else if(s.charAt(0) == '0' && s.length() > 2)
            base = 8;

        boolean isInt = true;
        if(s.contains("."))
            isInt = false;

        if(to != NumberLiteral.Hexadecimal)
            s = s.substring(0, s.indexOf("."));

        s = s.replace("0x", "");

        switch(to) {
            case Integer:
                return "Integer: " + Integer.parseInt(s, base);
            case Float:
                return "Double: " + Double.parseDouble(s);
            case Hexadecimal:
                if(isInt)
                    return "Hex: 0x" + Integer.toHexString(Integer.parseInt(s, base));
                else return "Hex: 0x" + Double.toHexString(Double.parseDouble(s));
            case Octal:
                return "Octal: 0" + Integer.toOctalString(Integer.parseInt(s, base));
            case Binary:
                return "Binary: " + Integer.toBinaryString(Integer.parseInt(s, base));
            case Character:
                return "Character: " + (char) Integer.parseInt(s, base);
            default:
                return "Error.";
        }
    }

    @SafeVarargs
    public static <T> boolean arrayContains(T[] arr, T... arr2) {
        for(T e : arr)
            for(T e2 : arr2)
                if(e.equals(e2))
                    return true;
        return false;
    }

    public static <T> T getFuture(Future<T> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {}

        return null;
    }

    public static EmbedBuilder embedImage(String title, String url, String footer, Server server) {
        return setEmbedColor(new EmbedBuilder().setTitle(title).setImage(url).setFooter(footer), server);
    }

    /**
     * Sets embed color to the bot's role color on the specified server, green if no role
     * @param embed
     * @param server
     * @return colored embed
     */
    public static EmbedBuilder setEmbedColor(EmbedBuilder embed, Server server) {
        if(server != null && WASPBot.getBot().getRoles(server).size() > 0)
            embed.setColor(Utility.toList(WASPBot.getBot().getRoles(server)).get(0).getColor());
        else
            embed.setColor(Color.GREEN);
        return embed;
    }
}
