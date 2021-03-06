package core;

import com.github.johnnyjayjay.discord.commandapi.CommandSettings;
import commands.RefreshCommand;
import commands.RunCommand;
import commands.TradeCommand;
import listener.MessageRecieved;
import listener.OnReady;
import listener.PrivateMessageRecieved;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.apache.log4j.varia.NullAppender;

import javax.security.auth.login.LoginException;

import static core.Config.PREFIX;

public class Main {

    public static JDABuilder builder = new JDABuilder(AccountType.CLIENT);
    public static JDA jda;

    public static void main(String[] args) {

        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        Config.init();
        Pokes.init();

        System.out.println("\u001B[35m" + "Initializing SharpyCatcher v0.2.1\u001B[0m");

        builder.setToken(Config.getValue("token"));

        initListeners();

        try {
            System.out.println("\u001B[35m" + "Connecting...\u001B[0m");
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(0);
        }


        CommandSettings settings = new CommandSettings(PREFIX, jda, true);
        settings.put(new RunCommand(), "run", "shell", "echo")
                .put(new RefreshCommand(), "reload", "refresh")
                .put(new TradeCommand(), "trade")
                .activate();

    }


    private static void initListeners() {
        builder
                .addEventListener(new MessageRecieved())
                .addEventListener(new OnReady())
                .addEventListener(new PrivateMessageRecieved())
        ;

    }
}
