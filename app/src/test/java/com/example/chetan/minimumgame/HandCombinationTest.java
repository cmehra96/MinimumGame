package com.example.chetan.minimumgame;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HandCombinationTest {


    @Test
    public void isStraightwithPlayer() {
        Deck testdeck = new Deck();
        testdeck.add(new Card(Rank.Nine, Suit.Clubs));
        testdeck.add(new Card(Rank.Eight, Suit.Spades));
        testdeck.add(new Card(Rank.Seven, Suit.Diamond));
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
        /*testdeck.add(new Card(Rank.King, Suit.Clubs));
        testdeck.add(new Card(Rank.Queen, Suit.Spades));
        testdeck.add(new Card(Rank.Three, Suit.Diamond));
        Card tempcard = new Card(Rank.Ace, Suit.Diamond); */ // Test Case 1: pass
       /* testdeck.add(new Card(Rank.Three,Suit.Diamond));
        testdeck.add(new Card(Rank.Four,Suit.Diamond));
        testdeck.add(new Card(Rank.Eight,Suit.Diamond));
        testdeck.add(new Card(Rank.Nine,Suit.Diamond));
        testdeck.add(new Card(Rank.Queen,Suit.Diamond));
        Card tempcard= new Card(Rank.Ten,Suit.Diamond);*/ //Test Case 2 : pass
        Card tempcard = new Card(Rank.Ten, Suit.Diamond);

        int result = HandCombination.isStraight(testdeck, tempcard);

        if (result == 1) {
            ArrayList<Card> removedcards = HandCombination.getStraight(testdeck);
        } else if (result == -1) {
            Card removedcard = HandCombination.createStraight(testdeck, tempcard);
        }

    }

    @Test
    public void isThreeOfAKind() {
        Deck testdeck = new Deck();
        Card card = new Card();
        testdeck.add(new Card(Rank.Ten, Suit.Diamond));
        testdeck.add(new Card(Rank.Ten, Suit.Diamond));
        testdeck.add(new Card(Rank.King, Suit.Diamond));
        testdeck.add(new Card(Rank.King, Suit.Diamond));
        //testdeck.add(new Card(Rank.King, Suit.Diamond));
        Card tempcard = new Card(Rank.King, Suit.Diamond);
        int result = HandCombination.isThreeOfAKind(testdeck, tempcard);
        ArrayList<Card> threeofkindcards = new ArrayList<>();
        if (result == 1)
            threeofkindcards = HandCombination.getThreeOfAKind(testdeck);
        else if (result == -1)
            card = HandCombination.createThreeOfAKind(testdeck, tempcard);

    }

}
