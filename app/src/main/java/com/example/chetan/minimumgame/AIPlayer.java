package com.example.chetan.minimumgame;


import android.os.AsyncTask;
import android.util.Log;

import java.util.Random;

public class AIPlayer extends Player {

    private static final String TAG = AIPlayer.class.getSimpleName();  // To get name of class in Logging
    private boolean wonRound;
    private int playerindex;    //
    private int callpercent = 0;

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


    public int getCallPercent(PlayerList playerList, int currentaiplayer) {
        Log.d(TAG,"Inside get call percent method");
        calculatePercentValue(playerList, currentaiplayer);
        playerindex = currentaiplayer;
        Log.d(TAG,"Get Call percent method executed succesfully");
        return callpercent;
    }

    private void calculatePercentValue(PlayerList playerList, int currentaiplayer) {
        Log.d("Thread", "Inside Evaluator method");
        new Evaluator().execute(playerList);
    }

    private class Evaluator extends AsyncTask<PlayerList, Void, Void> {

        @Override
        protected Void doInBackground(PlayerList... playerLists) {
            Log.d(TAG,"Inside evaluator class percent calculate method");
            PlayerList players = new PlayerList(playerLists[0]);
            Player currentplayer = players.get(playerindex);
            int cardcount = currentplayer.decksize();
            int index = 0;
            int no_of_players = players.size();
            int currentplayerlastroundscore = currentplayer.getPreviousroundscore();
            Card roundcard = currentplayer.getCurrentroundcard();
            int temp_percent = 0;
            if (cardcount <= 2) {
                if (currentplayer.evaluatescore() <= 3)
                    callpercent = 100;
                return null;
            }
            while (index < no_of_players) {
                if (index == playerindex)       //if index is current player;
                    continue;
                Player otherplayer = players.get(index);
                int otherplayercards = otherplayer.decksize();
                int otherplayerlastroundscore = otherplayer.getPreviousroundscore();
                if (cardcount < otherplayercards)  // if current player card is less than other player card
                {
                    if ((currentplayerlastroundscore + roundcard.cardRank()) <= otherplayerlastroundscore) // if last round won by current player
                    {
                        temp_percent += 100;
                    } else if ((currentplayerlastroundscore + roundcard.cardRank()) <= otherplayercards + 2) {
                        temp_percent += new Random().nextInt(51) + 50; // [0,50] +50 => [50,100] //random percent from 50 to 100
                    } else {
                        temp_percent += new Random().nextInt(101);
                    }
                } else {

                }
                index++;
            }
            callpercent= temp_percent/no_of_players;
            Log.d(TAG,"Call percent"+callpercent);
            return null;
        }
    }

}
