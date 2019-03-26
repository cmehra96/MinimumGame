package com.example.chetan.minimumgame.ScoreCard;

public class Items {

    private String name, score;

    public Items() {
    }

    public Items(String name, String score) {

        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}