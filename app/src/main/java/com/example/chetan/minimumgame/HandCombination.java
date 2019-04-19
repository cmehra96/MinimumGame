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
        tempDeck.sortByRankAsec();

        if (tempDeck.getCard(0).cardRank() == 1)       // If first card is Ace
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
            int nextrank = tempDeck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1)           //if cards suit differ by 1, increment straight
            {
                straightcount++;
            } else if (nextrank == 1 && currentrank == 13)     //specific condition for King and Ace
            {
                straightcount++;
            } else if (nextrank - currentrank != 1)    //if cards suit not equal to 1, reset the straight counter
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
        tempdeck.sortByRankAsec();
        if (tempdeck.getCard(0).cardRank() == 1)       // If first card is Ace
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
            int nextrank = tempdeck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1)           //if cards suit differ by 1, increment straight
            {
                straightcount++;
            } else if (nextrank == 1 && currentrank == 13)     //specific condition for King and Ace
            {
                straightcount++;
            } else if (nextrank - currentrank != 1)    //if cards suit not equal to 1, reset the straight counter
            {
                straightcount = 1;
            }

        }
        if (straightcount == 3)
            return 1;

        if (tempdeck.getCard(0).cardRank() == 1) {
            tempdeck.removeCard(tempdeck.Count() - 1);
        }

        tempdeck.add(card);
        tempdeck.sortByRankAsec();
        if (tempdeck.getCard(0).cardRank() == 1)       // If first card is Ace
        {
            Card tempCard = tempdeck.getCard(0);
            tempdeck.add(tempCard);
        }

        for (int i = 0; i < tempdeck.Count() - 1; i++) {
            if (otherstraight == 3) {
                break;
            }

            int currentrank = tempdeck.getCard(i).cardRank();
            int nextrank = tempdeck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1) {
                otherstraight++;

            } else if (nextrank == 1 && currentrank == 13) {
                otherstraight++;
            } else if (nextrank - currentrank != 1) {
                otherstraight = 1;
            }
        }
        if (otherstraight == 3)
            return -1;

        return 0;
    }

    /**
     * Method to find and remove straight cards from players deck
     *
     * @param deck Deck of player
     * @return Array of removed cards
     */

    public static ArrayList<Card> getStraight(Deck deck) {
        ArrayList<Integer> indexes = new ArrayList<>();
        deck.sortByRankAsec();
        if (deck.getCard(0).cardRank() == 1)
            deck.add((deck.getCard(0)));
        int straight = 1;
        indexes.add(0);
        for (int i = 0; i < deck.Count() - 1; i++) {
            if (straight == 3) {
                break;
            }
            int currentrank = deck.getCard(i).cardRank();
            int nextrank = deck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1) {
                straight++;
                indexes.add(i + 1);

            } else if (nextrank == 1 && currentrank == 13) {
                straight++;
                indexes.add(0);

            } else if (nextrank - currentrank != 1) {
                straight = 1;
                indexes.clear();
                indexes.add(i + 1);
            }
        }

        if (indexes.size() != 0)
            Collections.sort(indexes, Collections.<Integer>reverseOrder());        //Fix array out of bound exception as card always remove in descending order
        if (deck.getCard(0).cardRank() == 1)
            deck.removeCard(deck.Count() - 1);

        ArrayList<Card> removedcards = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            removedcards.add(deck.removeCard(indexes.get(i), true));
        }


        return removedcards;
    }


    /**
     * Create a straight set in player deck, by adding card from discarded deck
     * removimg nonstraight card
     *
     * @param deck Deck of player
     * @param card Discarded Deck card
     * @return a non-straight card.
     */
    public static Card createStraight(Deck deck, Card card) {
        Card removedcard = null;
        int nonstraightcardindex = -1;
        Deck tempdeck = new Deck();
        deck.sortByRankAsec();
        if (deck.getCard(0).cardRank() == 1)
            deck.add((deck.getCard(0)));
        int straight = 1;
        int decksize = deck.Count();
        tempdeck.add(deck.getCard(0));
        for (int i = 0; i < decksize - 1; i++) {
            if (straight == 3)
                break;
            int currentrank = deck.getCard(i).cardRank();
            int nextrank = deck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1) {
                straight++;
                tempdeck.add(deck.getCard(i + 1));

            } else if (nextrank == 1 && currentrank == 13) {
                straight++;
                tempdeck.add(deck.getCard(i + 1));
            } else if (nextrank - currentrank != 1) {
                straight = 1;
                tempdeck.add(card);

                if (isStraight(tempdeck)) {
                    removedcard = deck.removeCard(i + 1);
                    break;
                } else {
                    nonstraightcardindex = i;
                }

                tempdeck.clear();
                tempdeck.add(deck.getCard(i + 1));
            }
        }
        if (removedcard == null) {
            removedcard = deck.removeCard(nonstraightcardindex, true);


        }
        card.setShowcardface(false);
        deck.add(card);
        return removedcard;
    }

    public static boolean isStraight(Deck tempdeck) {
        tempdeck.sortByRankAsec();

        if (tempdeck.getCard(0).cardRank() == 1)       // If first card is Ace
        {
            Card tempCard = tempdeck.getCard(0);
            tempdeck.add(tempCard);
        }
        int straightcount = 1;
        for (int i = 0; i < tempdeck.Count() - 1; i++) {
            if (straightcount == 3) {
                // Log.d(TAG, "is straight worked successfully");
                break;
            }
            int currentrank = tempdeck.getCard(i).cardRank();
            int nextrank = tempdeck.getCard(i + 1).cardRank();
            if (nextrank - currentrank == 1)           //if cards suit differ by 1, increment straight
            {
                straightcount++;
            } else if (nextrank == 1 && currentrank == 13)     //specific condition for King and Ace
            {
                straightcount++;
            } else if (nextrank - currentrank != 1)    //if cards suit not equal to 1, reset the straight counter
            {
                straightcount = 1;
            }
        }

        if (straightcount == 3)
            return true;

        return false;

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

    public static int isThreeOfAKind(Deck deck, Card card) {
        Deck tempdeck = new Deck(deck);
        tempdeck.sortByRankAsec();
        int count = tempdeck.Count();
        for (int i = 0; i < count - 2; i++) {
            if (tempdeck.getCard(i).cardRank() == tempdeck.getCard(i + 1).cardRank() && tempdeck.getCard(i).cardRank() == tempdeck.getCard(i + 2).cardRank())
                return 1;
        }
        for (int i = 0; i < count - 1; i++) {
            if (tempdeck.getCard(i).cardRank() == tempdeck.getCard(i + 1).cardRank() && tempdeck.getCard(i).cardRank() == card.cardRank())
                return -1;
        }

        return 0;

    }

    public static ArrayList<Card> getThreeOfAKind(Deck deck) {
        ArrayList<Card> removedcards = new ArrayList<>();
        deck.sortByRank();
        int count = deck.Count();
        for (int i = 0; i < count - 2; i++) {
            if (deck.getCard(i).cardRank() == deck.getCard(i + 1).cardRank() && deck.getCard(i).cardRank() == deck.getCard(i + 2).cardRank()) {
                removedcards.add(deck.removeCard(i + 2));
                removedcards.add(deck.removeCard(i + 1));
                removedcards.add(deck.removeCard(i));
                break;
            }
        }
        return removedcards;
    }

    public static Card createThreeOfAKind(Deck deck, Card card) {
        Card removedcard = null;
        deck.sortByRank();
        int count = deck.Count();
        for (int i = 0; i < count - 1; i++) {
            if (deck.getCard(i).cardRank() == deck.getCard(i + 1).cardRank() && deck.getCard(i).cardRank() == card.cardRank()) {
                if (i - 1 >= 0)
                    removedcard = deck.removeCard(i - 1, true);
                else
                    removedcard = deck.removeCard(i + 2, true);
                card.setShowcardface(false);
                deck.add(card);
                break;
            }
        }

        return removedcard;
    }

}
