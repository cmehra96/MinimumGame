package com.example.chetan.minimumgame;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Deck {
    private ArrayList<Card> deck= new ArrayList<>() ;

    public  Deck()
    {

    }

    /**
     * Method to add card in the deck
     * @param card
     */
    public void add(Card card) {
        deck.add(card);
    }

    /**
     * Method to get the top element of the deck
     * @return The Top element of the deck
     */
    public Card getCard()
    {
        return deck.get(deck.size()-1);
    }

    /**
     *
     * @param index  
     * @return Element at index value
     */
    public  Card getCard(int index)
    {
        return deck.get(index);
    }

    /**
     * Method to get the size of the deck
     * @return Deck size
     */
    public  int Count()
    {
        return deck.size();
    }

    /**
     * Method to remove a single card from deck
     * @return Removed card from the deck.
     */
    public  Card Deal()
    {
        int cardcount= Count();
        Card dealcards= deck.get(cardcount-1);
        deck.remove(cardcount-1);
        dealcards.setShowcardface(true);
        return dealcards;

    }
    /**
     * Method to remove a single card from deck
     * @return Removed card from the deck.
     */
    public  Card Deal(boolean showcardface)
    {
        int cardcount= Count();
        Card dealcards= deck.get(cardcount-1);
        deck.remove(cardcount-1);
        dealcards.setShowcardface(showcardface);
        return dealcards;
    }

    public  void setCurrentCardImage(Bitmap image, int index)
    {
        getCard(index).setImage(image);
    }

    public  void setCurrentCard(Card card, int index)
    {
      deck.set(index,card);
    }

    /**
     * Method to swap between touch cards of the two decks
     * @param replacedcard Card to be added {@index} of the deck
     * @param index
     * @return Remove the card from @param Index
     */

    public Card swapCard(Card replacedcard, int index) {
        Card removedcard= deck.remove(index);
        deck.add(index,replacedcard);
        return removedcard;
    }

    public void removeCard(int index)
    {
        Card removecard=deck.remove(index);
    }
}
