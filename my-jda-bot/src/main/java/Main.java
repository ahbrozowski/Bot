import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;

public class Main extends ListenerAdapter {
    String[] ADJ;
    String[] FOOD;
    String author = "";
    boolean add = false;
    boolean adj = false;

    public static void main(String[] args) throws LoginException, IOException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String homeDir = System.getProperty("user.home");
        String token = readFile(homeDir + "/.JDA/token", StandardCharsets.UTF_8);
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
        boolean used = false;
       if (!add) {
           System.out.println("We received a message from " +
                   event.getAuthor().getName() + ":" +
                   event.getMessage().getContentDisplay()
           );

           if (event.getMessage().getContentRaw().equals("!name")) {
               String name = makeName();
               event.getChannel().sendMessage(name).queue();
           } else if (event.getMessage().getContentRaw().equals("!add adj")) {
               event.getChannel().sendMessage("what word would you like to add?").queue();
               author = event.getAuthor().getName();
               add = true;
               adj = true;

           }else if (event.getMessage().getContentRaw().equals("!add food")) {
               event.getChannel().sendMessage("what word would you like to add?").queue();
               author = event.getAuthor().getName();
               add = true;
               adj = false;

           }
       } else {
           if(FOOD == null) {
               try {
                   loadFiles();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (event.getAuthor().getName().equals(author)) {
               String homeDir = System.getProperty("user.home");
               String word = "," + event.getMessage().getContentRaw();
               if(adj) {
                   for (String s : ADJ) {
                       if (event.getMessage().getContentRaw().equals(s)) {
                           used = true;
                       }
                   }
               } if(!adj){
                   for (String s : FOOD) {
                       if (event.getMessage().getContentRaw().equals(s)) {
                           used = true;
                       }
                   }
               }
               if(!used) {
                   String path = "";
                   if(adj) {
                       path = "/.JDA/adj";
                   } else {
                       path = "/.JDA/food";
                   }

                   try {
                       Files.write(Paths.get(homeDir + path), word.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   try {
                       loadFiles();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   event.getChannel().sendMessage("Added!").queue();
               } else {
                   event.getChannel().sendMessage("Oops that is already in my code!").queue();
                   used = false;
               }
               add = false;
           }
       }
   }

    public String makeName() {
        int a = (int)(Math.random() * ADJ.length);
        int f = (int)(Math.random() * FOOD.length);

        String name = ADJ[a] + " " + FOOD[f];
        return name.toLowerCase();
    }

    public void loadFiles() throws IOException{
        String homeDir = System.getProperty("user.home");
        String f = readFile(homeDir +"/.JDA/food", StandardCharsets.UTF_8);
        String a = readFile(homeDir + "/.JDA/adj", StandardCharsets.UTF_8);
        FOOD = f.split(",");
        ADJ = a.split(",");
        for(String s: ADJ) {
            System.out.print(s+ ",");
        }

    }
}
