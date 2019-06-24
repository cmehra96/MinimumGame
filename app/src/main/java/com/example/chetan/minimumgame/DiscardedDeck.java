package com.example.chetan.minimumgame;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class to implement all the
 * operations of DiscardedDeck
 */
public class DiscardedDeck implements Parcelable {

    private ArrayList<Card> deck = new ArrayList<>();
    private int current_X;
    private int current_Y;

    public DiscardedDeck(int current_X, int current_Y) {
        this.current_X = current_X;
        this.current_Y = current_Y;
    }


    public void add(Card card) {
        card.setCurrent_X(current_X);
        card.setCurrent_Y(current_Y);
        card.setShowcardface(true);
        deck.add(card);
    }

    public Card getTopCard() {
        return deck.get(deck.size() - 1);
    }

    public Card Deal(boolean showcardface) {
        int cardcount = Count();
        Card dealcards = deck.get(cardcount - 1);
        deck.remove(cardcount - 1);
        dealcards.setShowcardface(showcardface);
        return dealcards;
    }

    public Card removeCard(int index, boolean showcardface) {
        Card dealcards = deck.remove(index);
        dealcards.setShowcardface(showcardface);
        return dealcards;

    }

    public int Count() {
        return deck.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.deck);
        dest.writeInt(this.current_X);
        dest.writeInt(this.current_Y);
    }

    protected DiscardedDeck(Parcel in) {
        this.deck = in.createTypedArrayList(Card.CREATOR);
        this.current_X = in.readInt();
        this.current_Y = in.readInt();
    }

    public static final Parcelable.Creator<DiscardedDeck> CREATOR = new Parcelable.Creator<DiscardedDeck>() {
        @Override
        public DiscardedDeck createFromParcel(Parcel source) {
            return new DiscardedDeck(source);
        }

        @Override
        public DiscardedDeck[] newArray(int size) {
            return new DiscardedDeck[size];
        }
    };
}
