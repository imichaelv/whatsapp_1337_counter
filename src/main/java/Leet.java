import model.Chat;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import java.io.*;

public class Leet {

    public static final String resourceDoc = "_chat.txt";

    private static final String OPT_YEAR = "year";
    private static Options options = new Options()
            .addOption(OPT_YEAR,true, "Score of the year -year 2020")
            .addOption("help", false, "prints help");

    public static void main(String[] args){
        try {
            Leet leet = new Leet(args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Leet(String[] args) throws Exception {
        System.out.println("Starting 1337 scanner");
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine =  parser.parse(options, args);
        ClassLoader classLoader = this.getClass().getClassLoader();
        File text = new File(classLoader.getResource(resourceDoc).getFile());

        System.out.println("File found : "+text.exists());

        BufferedReader reader = new BufferedReader(new FileReader(text));
        String line = reader.readLine();
        Chat chat;
        if(commandLine.hasOption(OPT_YEAR)){
            System.out.println("Finding year " + commandLine.getOptionValue(OPT_YEAR));
            chat = new Chat(Integer.parseInt(commandLine.getOptionValue(OPT_YEAR)));
        }else{
            chat = new Chat();
        }
        while(line!=null){
            chat.addMessage(line);
            line = reader.readLine();
        }
        chat.tellScore();
//        chat.tellTime();
    }
}
