package com.hynial.preinter.jbase.accessible;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccessSituations {
    private static final Person[] PEOPLE = new Person[]{new Person("a", 1), new Person("b", 2)};
    public static final List<Person> VALUES = Collections.unmodifiableList(Arrays.asList(PEOPLE));

    public Person[] getPeople(){
        return PEOPLE.clone();
    }

    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return this.name + ":" + this.age;
        }
    }

    public void printPerson(Person[] person){
        if(person == null) {
            System.out.println("Person Null");
            return ;
        }

        for (Person p : person){
            System.out.println(p.toString());
        }
    }
    public static void main(String[] args) {
        AccessSituations accessSituations = new AccessSituations();
        Person[] people = accessSituations.getPeople();

        System.out.println("Src:");
        accessSituations.printPerson(people);
//        people[0].setName("a->2");
        people[0] = new Person("a->3", 11);

        System.out.println("Dest:");
        accessSituations.printPerson(people);

        System.out.println("---" + PEOPLE[0].toString());

//        VALUES.set(0, new Person("c", 12));
    }
}
