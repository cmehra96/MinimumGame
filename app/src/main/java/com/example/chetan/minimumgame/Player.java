package com.example.chetan.minimumgame;

import java.util.ArrayList;

public class Player {
    protected Deck mydeck = new Deck();
    protected String name;
    protected int Score;
    protected boolean issafed;

    public Player() {

        Score = 0;
        name = "";
        issafed = false;
    }

    public Player(String name, int score, boolean issafed) {
        this.name = name;
        Score = score;
        this.issafed = issafed;
    }

    public Player(String name) {
        this.name = name;
        Score = 0;
        issafed = false;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        evaluatescore();
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    protected void evaluatescore() {
        int score = 0;
        for (Card c : mydeck.getDeck()
                ) {
            if (c.cardRank() == 14) {       //If card is Ace
                score += 1;
            } else {
                score += c.cardRank();
            }

        }
        AddScore(score);
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

    public void AddScore(int score) {
        Score += score;
    }

    public void addToHand(Card card) {
        mydeck.add(card);

    }

    public int decksize() {
        return mydeck.Count();
    }

    /**
     * @param index
     * @return Element at index value
     */

    public Card getCard(int index) {
        return mydeck.getCard(index);
    }

    public Card removeCard(int index, boolean showcardface) {
        return mydeck.removeCard(index, showcardface);
    }

    public void sortBySuit() {
        mydeck.sortBySuit();
    }

    public void sortByRank() {
        mydeck.sortByRank();
    }

    public void setCurrentCard(Card localcard, int currentiteration) {
        mydeck.setCurrentCard(localcard, currentiteration);
    }

}
