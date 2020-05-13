package model;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> messages;
    private Scoreboard scoreboard = new Scoreboard();
    private String lastTime;
    private Integer year = null;

    public Chat() {
        this.messages = new ArrayList<>();
    }

    public Chat(Integer year){
        this();
        this.year = year;
    }

    public void addMessage(String text) {
        Message message;
        if (messages.size() <= 3  ) {
            message = new Message(text, null);
        } else {
            message = new Message(text, messages.get(messages.size()-1));
            switch (message.check1337()) {
                case "good":
                    //System.out.println("good");
                    scoreboard.addGood(message.getPerson(), message.getDate(), year);
                    break;
                case "bad":
                    scoreboard.addBad(message.getPerson(), message.getDate(), year);
                    break;
                default:
                    break;
            }
        }
        messages.add(message);
        if(message.getTime()!=null){
            lastTime = message.getTime();
        }
        scoreboard.addTime(lastTime);
    }

    public void tellScore(){
        scoreboard.tellScore();
    }
    public void tellTime(){
        scoreboard.tellTime();
        System.out.println("\n\n\n\n");
        scoreboard.sortTime();
    }
}
