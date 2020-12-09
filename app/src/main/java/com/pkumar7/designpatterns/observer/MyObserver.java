package com.pkumar7.designpatterns.observer;

/**
 * Created by Pankaj Kumar on 08/December/2020
 */
class MyObserver implements IListener {

    public MyObserver(MyModel model){
        model.addListener(this);
    }
    
    @Override
    public void onChangeEvent(String oldValue, String newValue) {
        System.out.println("oldValue = " + oldValue + " newValue= " + newValue);
    }
}
