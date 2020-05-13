package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Counter implements Comparable<Counter>{
    private int good = 0;
    private int bad = 0;
    private Integer year=null;
    private HashMap<Integer, ArrayList<String>> goodYears = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> badYears = new HashMap<>();

    public Counter(){

    }

    public Counter(Integer year){
        this.year = year;
    }

    public void addBad(){
        this.bad++;
    }

    public void addBad(Date date){
        add(badYears, date);
        addBad();
    }

    public void addGood(Date date){
        add(goodYears,date);
        addGood();
    }

    private void add(HashMap<Integer, ArrayList<String>> map, Date date){
        map.getOrDefault(parseYear(date),new ArrayList<>());
        ArrayList arrayList = new ArrayList();
        arrayList.add(parseDate(date));
        if(!map.getOrDefault(year, new ArrayList<>()).contains(parseDate(date))){
            ArrayList a = map.getOrDefault(year, new ArrayList<>());
            a.addAll(arrayList);
            map.put(year,a);
        }
    }

    private Integer parseYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar.get(Calendar.YEAR);
    }

    private String parseDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void addGood(){
        this.good++;
    }

    public String printScore(){
        if(year!=null){
            return "good: "+goodYears.getOrDefault(year,new ArrayList<>()).size()+ " bad: "+badYears.getOrDefault(year, new ArrayList<>()).size();
        }else{
            return "good: "+good+ " bad: "+bad;
        }
    }

    @Override
    public int compareTo(Counter o) {
        if(year!=null){
            return (int) (this.goodYears.getOrDefault(year,new ArrayList<>()).size() - o.goodYears.getOrDefault(year,new ArrayList<>()).size());
        }else {
            return (int) (this.good - o.good);
        }
    }

    public int getGood() {
        return good;
    }
}
