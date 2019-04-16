package com.example.chetan.minimumgame;

public enum Rank {
    Ace(1, "ace", "ace"),
    Two(2, "two", "2"),
    Three(3, "three", "3"),
    Four(4, "four", "4"),
    Five(5, "five", "5"),
    Six(6, "six", "6"),
    Seven(7, "seven", "7"),
    Eight(8, "eight", "8"),
    Nine(9, "nine", "9"),
    Ten(10, "ten", "10"),
    Jack(11, "jack", "jack"),
    Queen(12, "queen", "queen"),
    King(13, "king", "king");


    private final int rank;
    private final String name;
    private final String imageName;

    Rank(int rank, String name, String imageName) {
        this.rank = rank;
        this.name = name;
        this.imageName = imageName;

    }

    public String getImageName() {
        return imageName;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}
