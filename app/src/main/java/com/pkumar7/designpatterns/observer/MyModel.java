package com.pkumar7.designpatterns.observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Pankaj Kumar on 08/December/2020
 */
class MyModel {
    HashSet<IListener> mObservers = new HashSet<>();
    List<Person> persons = new ArrayList<>();
    public MyModel(){
        Person p = new Person();
        p.setFirstName("Pankaj");
        p.setLastName("Kumar");

        Person p2 = new Person();
        p2.setFirstName("Prakash");
        p2.setLastName("Babu");

        persons.add(p);
        persons.add(p2);
    }
    public List<Person> getPersons(){
        return persons;
    }

    public void addListener(IListener listener) {
        mObservers.add(listener);
    }
    class Person {
        String firstName;String lastName;
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            notifyListeners(this.firstName, firstName);
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            notifyListeners(this.lastName, lastName);
            this.lastName = lastName;
        }
        public void notifyListeners(String oldValue, String newValue){
            for(IListener listener : mObservers){
                listener.onChangeEvent(oldValue, newValue);
            }
        }
    }

}
