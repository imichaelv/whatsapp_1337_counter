package CommandLine;

import org.apache.commons.cli.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandLine {
    private static String OPT_YEAR = "year";
    private static String OPT_BEGIN_DATE = "begindate";
    private static String OPT_END_DATE = "enddate";
    private static String OPT_POINTS = "points";
    private static String OPT_EXPORT_FILE = "file";
    private static String OPT_SQL = "sql";

    private org.apache.commons.cli.CommandLine cliCommandLine;

    private static Options options = new Options()
            .addOption(OPT_YEAR, true, "Score of the year -year 2020")
            .addOption(OPT_BEGIN_DATE, true, "Score counting from the begin date")
            .addOption(OPT_END_DATE, true, "Score counting till the end date")
            .addOption(OPT_EXPORT_FILE, true, "The whatsapp _chat.txt file")
            .addOption(OPT_POINTS, false, "Scores including scoreboard")
            .addOption(OPT_SQL , false, "SQL output")
            .addOption("help", false, "prints help");

    public CommandLine(String args[]) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            cliCommandLine = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp();
        }
    }

    public void printHelp() {
        System.out.println("\nThis is the 1337 whatsapp counter.");
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar Leet.jar", options);
    }

    public boolean enabledPoints() {
        return cliCommandLine.hasOption(OPT_POINTS);
    }

    public Date getStartDate() throws java.text.ParseException {
        String start = null;
        if (cliCommandLine.hasOption(OPT_BEGIN_DATE)) {
           return handleDate(cliCommandLine.getOptionValue(OPT_BEGIN_DATE));
        }
        return null;
    }

    public Date getEndDate() throws java.text.ParseException {
        String end = null;
        if (cliCommandLine.hasOption(OPT_END_DATE)) {
            return handleDate(cliCommandLine.getOptionValue(OPT_BEGIN_DATE));
        }
        return null;
    }

    private Date handleDate(String date){
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (java.text.ParseException e) {
            System.err.println("Wrong date format used, please use dd-MM-yyyy format. you have used: "+date);
        }
        return null;
    }

    public String getExportFile(){
        if(cliCommandLine.hasOption(OPT_EXPORT_FILE)){
            return cliCommandLine.getOptionValue(OPT_EXPORT_FILE);
        }else{
            return "_chat.txt";
        }
    }

    public boolean sqlMode() {
        return cliCommandLine.hasOption(OPT_SQL);
    }
}
