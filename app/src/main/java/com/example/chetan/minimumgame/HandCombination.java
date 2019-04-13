package com.example.chetan.minimumgame;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class HandCombination {

    private static final String TAG = HandCombination.class.getSimpleName();  // To get name of class in Logging

    /**
     * Method to find whether selected touched
     * cards are straight cards or not.
     *
     * @param mydeck        Deck of Player
     * @param tempListindex indexes of all the touched cards of the player deck
     * @return true if selected card belongs to straight else false
     */
    public static boolean isStraight(Deck mydeck, ArrayList<Integer> tempListindex) {

        //  Log.d(TAG, "Inside is straight method");
        if (tempListindex.size() != 3)
            return false;
        //    Log.d(TAG, "Touched cards more than 3");
        Deck tempDeck = new Deck();
        int i = 0;
        while (i < tempListindex.size()) {
            tempDeck.add(mydeck.getCard(tempListindex.get(i)));
            i++;
        }
        tempDeck.sortByRank();

        if (tempDeck.getCard(0).cardRank() == 14)       // If first card is Ace
        {
            Card tempCard = tempDeck.getCard(0);
            tempDeck.add(tempCard);
        }
        int straightcount = 1;
        for (i = 0; i < tempDeck.Count() - 1; i++) {
            if (straightcount == 3) {
                // Log.d(TAG, "is straight worked successfully");
                break;
            }
            int currentrank = tempDeck.getCard(i).cardRank();
            if (currentrank - tempDeck.getCard(i + 1).cardRank() == 1)           //if cards suit differ by 1, increment straight
            {
                straightcount++;
            } else if (currentrank == 2 && tempDeck.getCard((i + 1)).cardRank() == 14)     //specific condition for Two and Ace
            {
                straightcount++;
            } else if (currentrank - tempDeck.getCard(i + 1).cardRank() != 1)    //if cards suit not equal to 1, reset the straight counter
            {
                straightcount = 1;
            }
        }

        if (straightcount == 3)
            return true;

        return false;
    }

    /**
     * Method to find if straight set exist using combination player deck and Discarded Deck card
     *
     * @param deck Deck of player
     * @param card Discarded Deck top card
     * @return 1 if straight exist in deck belongs, -1 if straight exist using combination of deck and @card parameter, 0 if no straight exist.
     */
    public static int isStraight(Deck deck, Card card) {
        Deck tempdeck = new Deck(deck);
        tempdeck.sortByRank();
        if (tempdeck.getCard(0).cardRank() == 14)       // If first card is Ace
        {
            Card tempCard = tempdeck.getCard(0);
            tempdeck.add(tempCard);
        }
        int straightcount = 1;    //To count number of straight cards from player deck
        int otherstraight = 1;    // To count if straight belongs with {@card}
        for (int i = 0; i < tempdeck.Count() - 1; i++)  //To find if deck contains a straight
        {
            if (straightcount == 3) {
                break;
            }
            int currentrank = tempdeck.getCard(i).cardRank();
            if (currentrank - tempdeck.getCard(i + 1).cardRank() == 1) {
                straightcount++;

            } else if (currentrank == 2 && tempdeck.getCard(i + 1).cardRank() == 14) {
                straightcount++;
            } else if (currentrank - tempdeck.getCard(i + 1).cardRank() != 1) {
                straightcount = 1;
            }

        }
        if (straightcount == 3)
            return 1;

        if (tempdeck.getCard(0).cardRank() == 14) {
            tempdeck.removeCard(tempdeck.Count() - 1);
        }

        tempdeck.add(card);
        tempdeck.sortByRank();
        if (tempdeck.getCard(0).cardRank() == 14)       // If first card is Ace
        {
            Card tempCard = tempdeck.getCard(0);
            tempdeck.add(tempCard);
        }

        for (int i = 0; i < tempdeck.Count() - 1; i++) {
            if (otherstraight == 3) {
                break;
            }

            int currentrank = tempdeck.getCard(i).cardRank();
            if (currentrank - tempdeck.getCard(i + 1).cardRank() == 1) {
                otherstraight++;

            } else if (currentrank == 2 && tempdeck.getCard(i + 1).cardRank() == 14) {
                otherstraight++;
            } else if (currentrank - tempdeck.getCard(i + 1).cardRank() != 1) {
                otherstraight = 1;
            }
        }
        if (otherstraight == 3)
            return -1;

        return 0;
    }


    public static ArrayList<Card> getStraight(Deck deck) {
        boolean acetwocombo = false;
        ArrayList<Integer> indexes = new ArrayList<>();
        deck.sortByRank();
        if (deck.getCard(0).cardRank() == 14)
            deck.add((deck.getCard(0)));
        int straight = 1;
        indexes.add(0);
        for (int i = 0; i < deck.Count() - 1; i++) {
            if (straight == 3) {
                break;
            }
            int currentrank = deck.getCard(i).cardRank();
            if (currentrank - deck.getCard(i + 1).cardRank() == 1) {
                straight++;
                indexes.add(i + 1);

            } else if (currentrank == 2 && deck.getCard(i + 1).cardRank() == 14) {
                straight++;
                indexes.add(0);

            } else if (currentrank - deck.getCard(i + 1).cardRank() != 1) {
                straight = 1;
                indexes.clear();
                indexes.add(i + 1);
            }
        }

        if (indexes.size() != 0)
            Collections.sort(indexes, Collections.<Integer>reverseOrder());        //Fix array out of bound exception as card always remove in descending order
        if (deck.getCard(0).cardRank() == 14)
            deck.removeCard(deck.Count() - 1);

        ArrayList<Card> removedcards = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            removedcards.add(deck.removeCard(indexes.get(i)));
        }


        return removedcards;
    }

    /**
     * Method to check whether selected card of
     * player is Three of a kind or not
     *
     * @param mydeck        Deck of Player
     * @param tempListindex indexes of touched cards
     * @return True if touch cards are three of kind else false
     */
    public static boolean isThreeOfAKind(Deck mydeck, ArrayList<Integer> tempListindex) {
        if (mydeck.getCard(tempListindex.get(0)).cardRank() == mydeck.getCard(tempListindex.get(1)).cardRank() && mydeck.getCard(tempListindex.get(0)).cardRank() == mydeck.getCard(tempListindex.get(2)).cardRank())
            return true;
        return false;
    }


}
