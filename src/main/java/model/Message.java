package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private Date date;
    private String message;
    private String person;
    private String time;


    public Message(){}
    public Message(String message, Message lastMessage) {
        checkTime(message);
    }

    public void checkTime(String message) {
        if (message.matches("^\\[[[(0-9)][(0-9)]-[(0-9)][(0-9)]-[(0-9)][(0-9)] [(0-9)][(0-9)]:[(0-9)][(0-9)]:[(0-9)][(0-9)]\\] ](.*)")) {
            String timeString[] = message.split("]");
            try {
                this.date = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(timeString[0].substring(1));
                String[] personMessage = timeString[1].split(": ");
                person = personMessage[0];
                try {
                    //System.out.println(person+" : "+personMessage[1]);
                    this.message = personMessage[1];
                } catch (IndexOutOfBoundsException  e) {

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            time =  timeString[0].split(" ")[1];
            String times[] = time.split(":");
            time = times[0]+":"+times[1];
        }
    }

    public String check1337() {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(date);

            if (message != null && message.contains("1337")) {
                if(!message.contains("1337+") && !message.contains("1337 +") ) {
                    //System.out.println(message);
                    if (time.equals("13:37")) {
                        return "good";
                    } else {
                        return "bad";
                    }
                }else{
                    //System.out.println(person+" is een klootzak");
                }
            }
        }
        return "overig";
    }
    public String getPerson(){
        return person;
    }
    public String getTime(){
        return time;
    }
    public Date getDate(){return date; }

    public String getDay(){
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
