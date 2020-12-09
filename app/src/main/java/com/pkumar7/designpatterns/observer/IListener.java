package com.pkumar7.designpatterns.observer;

/**
 * Created by Pankaj Kumar on 08/December/2020
 */
interface IListener {
    void onChangeEvent(String oldValue, String newValue);
}
