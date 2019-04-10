package com.example.chetan.minimumgame;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> myhand;
    private ArrayList<Integer> indexes;

    public Hand() {
        myhand = new ArrayList<Card>();
        indexes = new ArrayList<Integer>();
    }

    public void clear() {
        myhand.clear();
        indexes.clear();
    }


}
