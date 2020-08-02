package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Person {
    private String name;
    private ArrayList<Date> streak = new ArrayList<>();
    private ArrayList<Date> bestStreak = new ArrayList<>();
    private int goodCounter = 0;
    private int badCounter = 0;


    public Person(String name, Date date){
        this.name = name;
        streak.add(date);
    }

    public Integer addAndCheckStreak(Date date){
        Date yesterdayDate;
        try{
            yesterdayDate = streak.get(streak.size()-1);
            if(name.contains("Micha")){
//                System.out.println(yesterdayDate);
            }
            Calendar yesterday = Calendar.getInstance();
            yesterday.setTime(yesterdayDate);
            Calendar today = Calendar.getInstance();
            today.setTime(date);

            today.add(Calendar.DATE,-1);
            if(toDate(yesterday).equals(toDate(today))){
                streak.add(date);
            }else{
                if(streak.size()>bestStreak.size()){
                    bestStreak = streak;
//                    System.out.println(name+" lost streak of: "+bestStreak.size());
                }
                streak = new ArrayList<>();
                streak.add(date);

            }
        }catch (IndexOutOfBoundsException e){
            streak.add(date);
        }

        return streak.size();
    }

    public Integer getBestStreak(){
        return bestStreak.size();
    }

    private String toDate(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.YEAR);
    }

    public void addGood() {
        this.goodCounter++;
    }

    public void addBad(){
        this.badCounter++;
    }

    public Integer getGoods() {
        return goodCounter;
    }

    public Integer getBads(){
        return badCounter;
    }
}
