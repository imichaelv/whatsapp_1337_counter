package model;

import CommandLine.CommandLine;

import java.util.Date;

public class Settings {
    private Date startDate;
    private Date endDate;
    private boolean scores;
    private String exportFile;
    private int maxPoints=150;
    private int points = 100;
    private int badPoints = -50;

    public Settings(CommandLine commandLine) throws Exception{
        this.startDate = commandLine.getStartDate();
        this.endDate = commandLine.getEndDate();
        this.scores = commandLine.enabledPoints();
        this.exportFile = commandLine.getExportFile();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isScores() {
        return scores;
    }

    public String getExportFile() {
        return exportFile;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getPoints() {
        return points;
    }

    public int getBadPoints() {
        return badPoints;
    }

    public int getNewDayPoints() {
        if(scores){
            return maxPoints;
        }else{
            return points;
        }
    }
}
