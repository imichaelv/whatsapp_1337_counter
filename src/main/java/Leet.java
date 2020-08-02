import CommandLine.CommandLine;
import controllers.DatabaseController;
import controllers.ExportReader;
import controllers.ScoreController;
import model.Chat;
import model.Settings;
import org.h2.util.StringUtils;
//import org.apache.commons.cli.CommandLine;
//import org.apache.commons.cli.CommandLineParser;
//import org.apache.commons.cli.DefaultParser;
//import org.apache.commons.cli.Options;
import java.io.*;
import java.util.Scanner;

public class Leet {

    public static final String resourceDoc = "_chat.txt";

    private static final String OPT_YEAR = "year";
//    private static Options options = new Options()
//            .addOption(OPT_YEAR,true, "Score of the year -year 2020")
//            .addOption("help", false, "prints help");

    public static void main(String[] args){
        try {
            CommandLine commandLine = new CommandLine(args);
            Settings settings = new Settings(commandLine);
            DatabaseController databaseController = new DatabaseController();
            ScoreController scoreController = new ScoreController(databaseController, settings);
            ExportReader exportReader = new ExportReader(settings.getExportFile(), scoreController);
            exportReader.parseScores();
            if(commandLine.sqlMode()){
                System.out.println("Table: Scores\n" +
                        "ID | NAME | POINTS | GOOD ");
                System.out.println("Enter the sql query now.");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                while (!line.equals("exit")){
                    if(line.equals("help")) {
                        System.out.println("enter a sql query or use one of these pre-queries");
                        System.out.println("1: selecting the highscore based on good results");
                        System.out.println("2: selecting the highscore based on false results");
                    }else if(StringUtils.isNumber(line)){
                        switch (line){
                            case "3":
                                databaseController.executeOverview(scoreController);
                                break;
                            case "2":
                                databaseController.execute("select name, count(good) from scores where good=0 group by name order by count(good) desc");
                                break;
                            case "1":
                            default:
                                databaseController.execute("select name, count(good) from scores where good=1 group by name order by count(good) desc");
                                break;
                        }

                    }else{
                        databaseController.execute(line);
                    }
                    line = scanner.nextLine();
                }

            }
            //            Leet leet = new Leet(args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    public Leet(String[] args) throws Exception {
//        System.out.println("Starting 1337 scanner");
//        CommandLineParser parser = new DefaultParser();
//        CommandLine commandLine =  parser.parse(options, args);
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        File text = new File(classLoader.getResource(resourceDoc).getFile());
//
//        System.out.println("File found : "+text.exists());
//
//        BufferedReader reader = new BufferedReader(new FileReader(text));
//        String line = reader.readLine();
//        Chat chat;
//        if(commandLine.hasOption(OPT_YEAR)){
//            System.out.println("Finding year " + commandLine.getOptionValue(OPT_YEAR));
//            chat = new Chat(Integer.parseInt(commandLine.getOptionValue(OPT_YEAR)));
//        }else{
//            chat = new Chat();
//        }
//        while(line!=null){
//            chat.addMessage(line);
//            line = reader.readLine();
//        }
//        chat.tellScore();
////        chat.tellTime();
//    }
}
