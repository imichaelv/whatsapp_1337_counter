package model;

public class TijdCounter implements Comparable<TijdCounter>{
    private int time;
    private String timeString;
    private int counter;

    public TijdCounter(String tijd){
        this.timeString = tijd;
        counter = 1;
        time = Integer.parseInt(tijd.replace(":",""));
    }

    public void addTime(){
        this.counter++;
    }

    @Override
    public int compareTo(TijdCounter tijdCounter){
        return (int) (this.time-tijdCounter.time);
    }

    public String getCouter(){
        return ""+counter;
    }
}
