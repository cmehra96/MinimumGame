package com.example.chetan.minimumgame;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        this.name = name;
        Score = 0;
        issafed = false;

    }

    /**
     * Method to set showcard property of each card
     * of the deck to true
     */
    public void showcards() {
        int i = 0;
        while (i < decksize()) {
            mydeck.getCard(i).setShowcardface(true);
            i++;
        }
    }

    /**
     * Method to hide cards by setting it showcardface
     * property to false
     */
    public void hidecards() {
        int i = 0;
        while (i < decksize()) {
            mydeck.getCard(i).setShowcardface(false);
            i++;
        }
    }


}
