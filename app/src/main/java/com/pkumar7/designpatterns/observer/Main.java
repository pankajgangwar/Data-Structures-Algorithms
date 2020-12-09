package com.pkumar7.designpatterns.observer;

import java.util.List;

/**
 * Created by Pankaj Kumar on 08/December/2020
 */
class Main {
    public static void main(String[] args) {
        MyModel myModel = new MyModel();
        MyObserver myObserver = new MyObserver(myModel);
        List<MyModel.Person> persons = myModel.getPersons();
        for(MyModel.Person p : persons){
            p.setLastName(p.getLastName() + " Gangwar");
        }
    }
}
