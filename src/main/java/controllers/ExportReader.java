package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ExportReader {
    private File export;
    private ScoreController scoreController;

    public ExportReader(String fileName, ScoreController scoreController){
        this.export = new File(fileName);
        this.scoreController = scoreController;
    }

    public void parseScores() throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(export));
        String line = reader.readLine();
        while (line!=null){
            scoreController.parseLine(line);
            line= reader.readLine();
        }
        scoreController.getScores();
    }

}
