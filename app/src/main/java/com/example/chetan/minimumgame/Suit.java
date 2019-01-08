package com.example.chetan.minimumgame;

public enum Suit {
        Clubs(1,"clubs" ,"♣"),
        Spades(2,"spades","♠"),
        Hearts(3,"hearts","♥"),
        Diamond(4,"diamonds","♦");
        private final int value;
        private final String name;
        private final String symbol;

        Suit(int value, String name, String symbol)
        {
            this.value=value;
            this.name=name;
            this.symbol = symbol;
        }

        public String getName()
        {
            return name;
        }

    public int getValue() {
        return value;
    }

}
