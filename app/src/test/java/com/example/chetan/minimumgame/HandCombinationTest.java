package com.example.chetan.minimumgame;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HandCombinationTest {


    @Test
    public void isStraightwithPlayer() {
        Deck testdeck = new Deck();
        testdeck.add(new Card(Rank.King, Suit.Clubs));
        testdeck.add(new Card(Rank.Queen, Suit.Spades));
        testdeck.add(new Card(Rank.Ace, Suit.Diamond));
        ArrayList<Integer> tempListindex = new ArrayList<>();
        tempListindex.add(0);
        tempListindex.add(1);
        tempListindex.add(2);
        boolean result = HandCombination.isStraight(testdeck, tempListindex);
        boolean expected = false;
        assertEquals(expected, result);
    }

    @Test
    public void isStraightWithAIPlayer() {
        Deck testdeck = new Deck();
        testdeck.add(new Card(Rank.King, Suit.Clubs));
        testdeck.add(new Card(Rank.Queen, Suit.Spades));
        testdeck.add(new Card(Rank.Two, Suit.Diamond));
        Card tempcard = new Card(Rank.Jack, Suit.Diamond);
        int result = HandCombination.isStraight(testdeck, tempcard);
    }

    @Test
    public void isThreeOfAKind() {
    }
}
