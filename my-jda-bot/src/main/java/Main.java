import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NzQxNzg1NTEzNTgwNjkxNDY3.Xy8nmA.MXz0M7282QlyxIVCZdmt9uXGlIQ";
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
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
