package controllers;

import model.Counter;
import model.Message;
import model.Person;
import model.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreController {
    private final Settings settings;
    private Message lastMessage;
    private final DatabaseController databaseController;
    private Calendar day = Calendar.getInstance();
    private int nextPoint = 100;
    private HashMap<String, HashMap<String, Integer>> scores = new HashMap<>();
    private HashMap<String, Person> persons = new HashMap<>();

    public ScoreController(DatabaseController databaseController, Settings settings) {
        this.databaseController = databaseController;
        this.settings = settings;
    }

    public void parseLine(String text) throws Exception {
        Message message = parseMessage(text);
        if(message!=null) {
            checkNewDay(message.getDate());
            switch (message.check1337()) {
                case "good":
                    addGood(message);
                    break;
                case "bad":
                    addBad(message);
                    break;
                default:
                    break;
            }
        }
    }

    private void addBad(Message message) throws Exception{
        HashMap<String, Integer> score = scores.getOrDefault(message.getPerson(), new HashMap<>());
        if(score.containsKey(message.getDay())){
            score.put(message.getDay(),getFalseSpamPoints(score.get(message.getDay())));
        }else{
            score.put(message.getDay(),-60);
            Person person = persons.get(message.getPerson());
            if(person==null){
                person = new Person(message.getPerson(), message.getDate());
                persons.put(message.getPerson(),person);
            }
            person.addBad();
            databaseController.put(message.getPerson(), -60, false);
        }
        scores.put(message.getPerson(),score);
    }

    private void addGood(Message message)throws Exception {
        HashMap<String, Integer> score = scores.getOrDefault(message.getPerson(), new HashMap<>());
        if(score.containsKey(message.getDay())){
            score.put(message.getDay(),getSpamPoints(score.get(message.getDay())));
        }else{
            score.put(message.getDay(),getPoints(message.getPerson(),message.getDate()));
            Person person = persons.get(message.getPerson());
            if(person==null){
                person = new Person(message.getPerson(), message.getDate());
                persons.put(message.getPerson(),person);
            }
            person.addGood();

        }
        scores.put(message.getPerson(),score);
    }

    private Integer getSpamPoints(Integer point) {
        if(point<=20){
            return 20;
        }else{
            return point-20;
        }
    }

    private Integer getFalseSpamPoints(Integer point) {
        if(point<=-100){
            return -100;
        }else{
            return point-20;
        }
    }


    private Integer getPoints(String person, Date date)throws Exception{
        int point = nextPoint;
        nextPoint = settings.getPoints();

        if(persons.containsKey(person)){
            int multiplier = persons.get(person).addAndCheckStreak(date);
            if(multiplier>=3){
                point=point+(multiplier*10);
            }
        }else{
            persons.put(person,new Person(person, date));
        }
        databaseController.put(person,point,true);
        return point;
    }

    private void checkNewDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        if (day.get(Calendar.DAY_OF_YEAR)!=this.day.get(Calendar.DAY_OF_YEAR)){
            resetDay(day);
        }
    }

    private void resetDay(Calendar day) {
        this.day=day;
        this.nextPoint = settings.getNewDayPoints();
    }


    private Message parseMessage(String line) {
        Message message=null;
        if (line.matches("^\\[[[(0-9)][(0-9)]-[(0-9)][(0-9)]-[(0-9)][(0-9)] [(0-9)][(0-9)]:[(0-9)][(0-9)]:[(0-9)][(0-9)]\\] ](.*)")) {
            String timeString[] = line.split("]");
            message = new Message();
            try {
                message.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(timeString[0].substring(1)));
                String[] personMessage = timeString[1].split(": ");
                message.setPerson(personMessage[0]);
                try {
                    //System.out.println(person+" : "+personMessage[1]);
                    message.setMessage(personMessage[1]);
                } catch (IndexOutOfBoundsException  e) {

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String time =  timeString[0].split(" ")[1];
            String times[] = time.split(":");
            time = times[0]+":"+times[1];
            message.setTime(time);
        }
        return message;
    }

    public void getScores() {
        System.out.println("Printing out scores:");


        scores.forEach((person,scores)->
        {
            AtomicInteger points = new AtomicInteger(0);
            scores.forEach((day,score)->{
                points.addAndGet(score);
            });
//            System.out.printf(String.format("%-25s",person)+" score: "+String.format("%-10s",points)+"good:"+persons.get(person).getGoods()+"\tbad:"+persons.get(person).getBads()+"\n");

        });

    }

    public Integer getStreak(String person) {
        return persons.get(person).getBestStreak();
    }
}
