package com.example.chetan.minimumgame;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck() {

    }

    /**
     * Method to add card in the deck
     *
     * @param card
     */
    public void add(Card card) {
        deck.add(card);
        //sort();
    }

    /**
     * Method to get the top element of the deck
     *
     * @return The Top element of the deck
     */
    public Card getCard() {
        return deck.get(deck.size() - 1);
    }

    /**
     * @param index
     * @return Element at index value
     */
    public Card getCard(int index) {
        return deck.get(index);
    }

    /**
     * Method to get the size of the deck
     *
     * @return Deck size
     */
    public int Count() {
        return deck.size();
    }

    /**
     * Method to remove a single card from deck
     *
     * @return Removed card from the deck.
     */
    public Card Deal() {
        int cardcount = Count();
        Card dealcards = deck.get(cardcount - 1);
        deck.remove(cardcount - 1);
        dealcards.setShowcardface(true);
        return dealcards;

    }


    /**
     * Method to remove a top card from deck
     *
     * @param showcardface set the card to show or hide face
     * @return Removed card from the deck.
     */
    public Card Deal(boolean showcardface) {
        int cardcount = Count();
        Card dealcards = deck.get(cardcount - 1);
        deck.remove(cardcount - 1);
        dealcards.setShowcardface(showcardface);
        return dealcards;
    }

    public void setCurrentCardImage(Bitmap image, int index) {
        getCard(index).setImage(image);
    }

    public void setCurrentCard(Card card, int index) {
        deck.set(index, card);
    }

    /**
     * Method to swap between touch cards of the two decks
     *
     * @param replacedcard Card to be added {@index} of the deck
     * @param index
     * @return Remove the card from @param Index
     */

    public Card swapCard(Card replacedcard, int index) {
        Card removedcard = deck.remove(index);
        deck.add(index, replacedcard);
        return removedcard;
    }

    public Card removeCard(int index, boolean showcardface) {
        Card dealcards = deck.remove(index);
        dealcards.setShowcardface(showcardface);
        return dealcards;

    }

    public void shuffle() {
        Log.d("Deck", "shuffling");
        Collections.shuffle(deck);
    }

    /**
     * Method to sort deck in descending order.
     */

    public void sort() {
        Collections.sort(deck, new Comparator<Card>() {
            @Override
            public int compare(Card lhs, Card rhs) {
                return rhs.compareTo(lhs);
            }
        });
    }

    /**
     * To Compare each
     * Card of the deck
     * with the parameter card
     * and return largest one
     *
     * @param card
     * @return index of the largest card of the deck. As well as whether that
     * card is larger than parameter card.
     */
    public Pair<Integer, Boolean> getLargestCardByRank(Card card) {
        int index = 0;
        Card temp = deck.get(0);
        for (int i = 1; i < Count(); i++) {
            if (deck.get(i).cardRank() > temp.cardRank()) {
                temp = deck.get(i);
                index = i;
            }
        }
        if (temp.cardRank() > card.cardRank())
            return new Pair<>(index, true);
        else
            return new Pair<>(index, false);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Method to refill the empty deck, mainly for DealtDeck
     *
     * @param DiscardedDeck      Deck from which cards to be filled
     * @param dealtDeck_CurrentX update currentX of the card
     * @param dealtDeck_CurrentY update currentY of the card
     */
    public void refill(DiscardedDeck DiscardedDeck, int dealtDeck_CurrentX, int dealtDeck_CurrentY) {
       /* for (Card currentcard: DiscardedDeck.getDeck()) {
            {
                currentcard.setCurrent_X(dealtDeck_CurrentX);
                currentcard.setCurrent_Y(dealtDeck_CurrentY);
                currentcard.setShowcardface(false);
                deck.add(currentcard);

            }
            shuffle();

             //DiscardedDeck.clear();
           //DiscardedDeck.add(Deal(true));
        }
        */
        int count = DiscardedDeck.Count() - 1;
        int i = 0;
        while (count >= 0) {
            Card currentcard = DiscardedDeck.removeCard(count, false);
            currentcard.setCurrent_X(dealtDeck_CurrentX);
            currentcard.setCurrent_Y(dealtDeck_CurrentY);
            currentcard.setShowcardface(false);
            deck.add(currentcard);
            count--;
        }
        shuffle();
        DiscardedDeck.add(Deal(true));
    }

    private void clear() {
        deck.clear();
    }
}
