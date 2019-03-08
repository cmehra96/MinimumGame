package com.example.chetan.minimumgame;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> myhand;
    private ArrayList<Integer> handvalue;

    public Hand() {
        myhand = new ArrayList<Card>();
        handvalue = new ArrayList<Integer>();
    }

    public void clear() {
        myhand.clear();
        handvalue.clear();
    }


}
