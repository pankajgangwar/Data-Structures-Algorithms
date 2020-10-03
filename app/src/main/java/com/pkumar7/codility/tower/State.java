package com.pkumar7.codility.tower;

import java.util.LinkedList;
import java.util.List;

public class State {

    private int[] towers;

    State(int[] towers) {

        this.towers = towers;
    }


    public List<State> buildStates() {
        List<State> states = new LinkedList<>();
        for (int i=0; i< towers.length ; i++ ){
            states.addAll(buildStates(i) );
        }
        return states;
    }

    public List<State> buildStates(int index) {

        List<State> states = new LinkedList<>();

        addState(states, index, index - 2);
        addState(states, index, index - 1);
        addState(states, index, index + 1);
        addState(states, index, index + 2);

        return states;
    }

    private void addState(List<State> states, int index, int moveToIndex) {

        if(towers[index] == 0 ){
            return;
        }

        if (moveToIndex < 0 || moveToIndex > towers.length-1) {
            return;
        }

        int[] clone = towers.clone();
        clone[index] = clone[index] - 1;
        clone[moveToIndex] = clone[moveToIndex] + 1;
        states.add(new State(clone));
    }

    @Override public boolean equals(Object obj) {

        int[] other = (int[]) obj;

        for (int i : towers) {
            if (other[i] != towers[i]) {
                return false;
            }
        }
        return false;
    }

    @Override public String toString() {
        String arrTxt = "";
        for (int tower : towers  ) {
            arrTxt= arrTxt +"_"+ tower;
        }
        return arrTxt;
    }
}
