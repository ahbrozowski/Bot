import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, IOException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String homeDir = System.getProperty("user.home");
        String token = readFile(homeDir + "/.JDA/token", StandardCharsets.UTF_8);;
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        String content = Files.lines(Paths.get(path), encoding)
                .collect(Collectors.joining(System.lineSeparator()));

        return content;
    }

   @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ":" +
                event.getMessage().getContentDisplay()
        );

        if(event.getMessage().getContentRaw().equals("!name")) {
            String name = makeName();
            event.getChannel().sendMessage(name).queue();
        }
    }

    public String makeName() {
        String[] adj = {"attractive","bald","beautiful","chubby","clean","dazzling","drab","elegant","fancy","fit","flabby","glamorous","gorgeous","handsome","long","magnificent","muscular","plain","plump","quaint","scruffy","shapely","short","skinny","stocky","ugly","unkempt","unsightly"};
        String[] food = {"apple", "avocado", "almond", "anchovy", "aniseed","asparagus","banana","blueberries", "blackberries", "biscuits","bhaji","beans","cake","crisps", "curry", "carrot", "coconut","cookie", "dates", "damson jam", "dried fruit", "doughnut", "doner meat", "egg", "eggplant" , "enchilada" , "egg fried rice", "fish", "fizzy drink", "figs", "fruit salad", "fruit" ,"fruit cake"};
        int a = (int)(Math.random() * adj.length);
        int f = (int)(Math.random() * food.length);

        String name = adj[a] + " " + food[f];

        return name;
    }
}
