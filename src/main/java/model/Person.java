package model;

@Deprecated
public class Person {
    private String name;
    private Counter counter;

    public Person(String name){
        this.name = name;
        this.counter = new Counter();
    }

}
