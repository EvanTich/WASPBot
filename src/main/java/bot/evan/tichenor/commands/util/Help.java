package bot.evan.tichenor.commands.util;

import bot.evan.tichenor.WASPBot;
import bot.evan.tichenor.utils.Utility;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import de.btobastian.sdcf4j.CommandHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 2/5/2017
 */
public class Help implements CommandExecutor {

    private final CommandHandler commandHandler;

    public Help(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Command(aliases = {"help", "commands"}, description = "Shows this page", usage = "!help <commands>")
    public void onCommand(String[] args, Message message, Server server) {
        List<Command> commands;
        if(args.length > 0) {
            commands = new ArrayList<>();
            for(String arg : args) {
                for (CommandHandler.SimpleCommand command : commandHandler.getCommands()) {
                    Command cmd = command.getCommandAnnotation();

                    if(Utility.arrayContains(cmd.aliases(), arg))
                        commands.add(cmd);
                }
            }
        } else commands = commandHandler.getCommands()
                .stream().map(CommandHandler.SimpleCommand::getCommandAnnotation)
                .filter(Command::showInHelpPage)
                .collect(Collectors.toList());

        message.reply("", embedCommands(server, commands));
    }

    private EmbedBuilder embedCommands(Server server, List<Command> commands) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Commands");
        embed.setFooter("Made with Java");

        if(server != null && WASPBot.getBot().getRoles(server).size() > 0)
            embed.setColor(Utility.toList(WASPBot.getBot().getRoles(server)).get(0).getColor());
        else
            embed.setColor(Color.GREEN);

        for(Command cmd : commands) {
            StringBuilder usage = new StringBuilder();

            usage.append(commandHandler.getDefaultPrefix());

            for (String alias : cmd.aliases())
                usage.append(alias).append(" | ");
            // delete last 2 characters, keep space (its needed yo)
            usage.delete(usage.length() - 2, usage.length());

            for(String extraBit : Utility.subarray(cmd.usage().split(" "), 1))
                usage.append(extraBit).append(' ');

            String description = cmd.description();
            if(description.equals("none"))
                description = cmd.aliases()[0];

            embed.addField(usage.toString(), description, false);
        }

        return embed;
    }
}
