package com.example.chetan.minimumgame;

public enum Suit {
        Clubs(1,"clubs"),
        Spades(2,"spades"),
        Hearts(3,"hearts"),
        Diamond(4,"diamonds");
        private final int value;
        private final String name;

        Suit(int value,String name)
        {
            this.value=value;
            this.name=name;
        }

        public String getName()
        {
            return name;
        }


}
