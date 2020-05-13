package model;

import java.util.*;
import java.util.stream.Collectors;

public class Scoreboard{
    private HashMap<String, Counter> scores = new HashMap<>();
    private HashMap<String, TijdCounter> times = new HashMap<>();

    public Scoreboard(){

    }

    public void addTime(String time){
        if(times.containsKey(time)){
            times.get(time).addTime();
        }else{
            times.put(time,new TijdCounter(time));
        }
    }

    public void addGood(String person, Date date, Integer year){
        //System.out.println(person);
        if(scores.containsKey(person)){
            scores.get(person).addGood(date);
        }else{
            Counter counter = new Counter(year);
            counter.addGood(date);
            scores.put(person, counter);
        }
    }

    public void addBad(String person, Date date, Integer year){
        if(scores.containsKey(person)){
            scores.get(person).addBad(date);
        }else{
            Counter counter = new Counter(year);
            counter.addBad(date);
            scores.put(person, counter);
        }
    }

    public void tellScore(){
        System.out.println("Telling score");
        System.out.println("Size: "+scores.size());
        final int j = 1;
        scores = scores
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (oldValue,newValue)->oldValue,LinkedHashMap::new)
                );
        scores.entrySet().iterator().forEachRemaining((i)->{
            System.out.printf(String.format("%-25s",i.getKey())+" score: "+i.getValue().printScore()+"\n");
        });
    }

    public void tellTime(){
        System.out.println("Times score");
        System.out.println("Size: "+times.size());
        List<TijdCounter> sorted = new ArrayList<>(times.values());
        //Collection
        Iterator it = times.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey()+ " score: "+ ((TijdCounter)pair.getValue()).getCouter());
        }
    }

    public void sortTime(){
        TreeMap<String, TijdCounter> sorted = new TreeMap<>();
        sorted.putAll(times);

        Iterator it = sorted.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey()+ " Score: "+ ((TijdCounter)pair.getValue()).getCouter());
        }
    }
}