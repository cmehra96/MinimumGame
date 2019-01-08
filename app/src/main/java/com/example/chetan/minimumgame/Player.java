package com.example.chetan.minimumgame;

import java.util.ArrayList;

public class Player {
    protected Deck mydeck= new Deck();
    protected String name;
    protected int Score;
    protected boolean issafed;

    public Player()
    {

        Score=0;
        name="";
        issafed=false;
    }

    public Player(String name, int score,boolean issafed)
    {
        this.name=name;
        Score=score;
        this.issafed=issafed;
    }

    public Player(String name)
    {
        this.name=name;
        Score=0;
        issafed=false;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public boolean isIssafed() {
        return issafed;
    }

    public void setIssafed(boolean issafed) {
        this.issafed = issafed;
    }

    public Deck getMydeck() {
        return mydeck;
    }

    public void setMydeck(Deck mydeck) {
        this.mydeck = mydeck;
    }

    public void AddScore(int score)
    {
        Score+= score;
    }

    public void addToHand(Card card)
    {
        mydeck.add(card);
        mydeck.sort();
    }

}
