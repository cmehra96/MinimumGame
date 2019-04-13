package com.example.chetan.minimumgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.PorterDuff;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.os.Handler;

import com.example.chetan.minimumgame.ScoreCard.ScoreCard;
import com.example.chetan.minimumgame.ScoreCard.ScoreCardPopup;

import java.util.ArrayList;
import java.util.Collections;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MySurfaceView.class.getSimpleName();  // To get name of class in Logging
    final Handler longpressedhandler = new Handler();
    private final int MIN_CLICK_DURATION = 1000;
    private final int no_of_players = 2;
    private final int no_of_CPU_players = no_of_players - 1;
    MainActivity parent;
    MyButton callminimum;
    DiscardedDeck discardedDeck;
    private Context context;
    private DisplayMetrics metrics;
    private MySurfaceViewThread thread;
    private int Screen_Width;
    private int Screen_Height;
    private float density;
    private int Card_Width;
    private int Card_Height;
    private int Screen_Center_X;
    private int Screen_Center_Y;
    private int Screen_Bottom_Middle_X;
    private int Screen_Bottom_Middle_Y;
    //  private Deck MainPlayer;
    private Deck DeatlDeck;
    //private Deck DiscardedDeck;
    // private Deck Top_Center_Player;
    private Bitmap BlueBackCard;
    private int DealtDeck_CurrentX;
    private int DealtDeck_CurrentY;
    private int DiscardedDeck_CurrentX;
    private int DiscardedDeck_CurrentY;
    private boolean isLongTouched = false;
    private Card touchedcard = null;
    private int cardindex = -1;
    private Card replacedcard = null;
    private GestureDetector gestureDetector;
    private long startclicktime;
    private ArrayList<Card> tempLongtouchList = new ArrayList<>();
    private ArrayList<Integer> tempListindex = new ArrayList<>();
    private Player player;
    private ArrayList<AIPlayer> AIPlayerlist;
    private int current_player;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        metrics = getResources().getDisplayMetrics();
        setWillNotDraw(false);
        gestureDetector = new GestureDetector(context, new GestureListener(this));
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        metrics = getResources().getDisplayMetrics();
        setWillNotDraw(false);
        gestureDetector = new GestureDetector(context, new GestureListener(this));
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defstyles) {

        super(context, attrs, defstyles);
        this.context = context;
        metrics = getResources().getDisplayMetrics();
        setWillNotDraw(false);
        gestureDetector = new GestureDetector(context, new GestureListener(this));
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        getHolder().addCallback(this);
        thread = new MySurfaceViewThread(getHolder(), this);
        setFocusable(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Log.d(TAG, "Inside Surface Created method");
        initializevariable();
        AllocatedCardList();
        thread.setRunning(true);
        thread.start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    private void initializevariable() {
        Screen_Width = getWidth();
        Screen_Height = getHeight();
        density = metrics.density;
        Card_Width = (int) (125.0F * density);
        Card_Height = (int) (93.0F * density);
        Screen_Center_X = Screen_Width / 2;
        Screen_Center_Y = Screen_Height / 2;
        Screen_Bottom_Middle_X = Screen_Center_X - Card_Width;
        Screen_Bottom_Middle_Y = Screen_Height - Card_Height;
        DeatlDeck = new Deck();
        DealtDeck_CurrentX = Screen_Center_X - Card_Width;
        DealtDeck_CurrentY = Screen_Center_Y - Card_Height / 2;
        DiscardedDeck_CurrentX = Screen_Center_X + Card_Width;
        DiscardedDeck_CurrentY = Screen_Center_Y - Card_Height / 2;
        AIPlayerlist = new ArrayList<AIPlayer>(no_of_CPU_players);
        current_player = 0;
        callminimum = new MyButton(Screen_Width, Screen_Height, Screen_Width / 9, Screen_Height / 5, (BitmapFactory.decodeResource(getResources(), R.drawable.call_button_up)));
        discardedDeck = new DiscardedDeck(DiscardedDeck_CurrentX, DiscardedDeck_CurrentY);
        initializePlayers();
    }

    private void initializePlayers() {
        player = new Player("You");
        for (int i = 0; i < no_of_CPU_players; i++) {
            AIPlayerlist.add(new AIPlayer("Bot" + i));
        }
    }


    private void AllocatedCardList() {
        //  Log.d(TAG, "inside AllocatedCardList method");
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                DeatlDeck.add(new Card(rank, suit, true, DealtDeck_CurrentX, DealtDeck_CurrentY));
            }

        }

        DealCards();
    }

    private void DealCards() {
        //  Log.d(TAG, "Inside Deal Card method");

        DeatlDeck.shuffle();
        player.addToHand(DeatlDeck.Deal(true));
        player.addToHand(DeatlDeck.Deal(true));
        player.addToHand(DeatlDeck.Deal(true));
        for (int i = 0; i < no_of_CPU_players; i++) {
            AIPlayerlist.get(i).addToHand(DeatlDeck.Deal(false));
            AIPlayerlist.get(i).addToHand(DeatlDeck.Deal(false));
            AIPlayerlist.get(i).addToHand(DeatlDeck.Deal(false));
        }
        discardedDeck.add(DeatlDeck.Deal(true));
    }

    /**
     * @param event
     * @return
     */
    // @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (thread.getMySurfaceHolder()) {
            //   Log.d(TAG, "Inside Touch Event");
            gestureDetector.onTouchEvent(event);
        }
        return true;


    }

    /**
     * Method to swap Main Player card with
     * either Dealt Deck or with Discarded
     * Deck, this method will check whether
     * user has touched Main Player deck,
     * Dealt Deck or Discarded Deck, if
     * user has touched Main Deck using
     * long touch it will add those touch
     * cards to temporary deck, till
     * user does not touch Discarde/
     * Dealt Deck
     *
     * @param e: to determine which type of event has performed.
     */
    public void swapSingleTouchCard(MotionEvent e) {
        Log.d(TAG, "Inside swapSingle Touch Method");
        float lasttouched_X, lasttouched_Y;
        Card localcard;
        int index = -1;
        lasttouched_X = e.getX();
        lasttouched_Y = e.getY();

        if (callminimum.getBtn_rect().contains(lasttouched_X, lasttouched_Y)) {
            showdown();
        }
        // Code for long touch and single touch swap
        if ((((lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X < (DiscardedDeck_CurrentX + discardedDeck.getTopCard().getImage().getWidth()))) == false
                && !((lasttouched_X >= DealtDeck_CurrentX && lasttouched_X < DealtDeck_CurrentX + DeatlDeck.getCard().getImage().getWidth())))
                && isLongTouched) // Main Player Deck, card  is touched
        {
            //     Log.d(TAG,"Inside Long Touch Add Card condition");
            addTouchedCardToLongTouched(e);
        } else if (isLongTouched == false && touchedcard == null) {
            Log.d(TAG, "Inside Single Touch Card Condition");
            index = cardTouched((int) lasttouched_X, (int) lasttouched_Y);
            if (index > -1) {
                touchedcard = player.getCard(index);
                cardindex = index;
            }
        } else if (lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X <= (DiscardedDeck_CurrentX + discardedDeck.getTopCard().getImage().getWidth())) //if touched card is Discard deck
        {
            if (touchedcard != null)       // to replace with single card
            {
                Log.d(TAG, "Inside Single Touch Card Discarded Deck Swap Conditon ");
                swapFromDiscardedDeck(player.getMydeck(), cardindex, true);
                touchedcard = null;
                cardindex = -1;
            } else if (isLongTouched) {
                Collections.sort(tempListindex, Collections.<Integer>reverseOrder());        //Fix array out of bound exception as card always remove in descending order
                int size = tempListindex.size() - 1;
                int i = 0;
                Card Discarddeckcard;
                Discarddeckcard = discardedDeck.Deal(true);
                while (i <= size)           // removal of card in sort ascending order
                {
                    Card removecard = player.removeCard(tempListindex.get(i), true);
                    discardedDeck.add(removecard);
                    i++;
                }
                tempListindex.clear();
                // tempLongtouchList.clear();
                player.addToHand(Discarddeckcard);
                isLongTouched = false;
            }
            current_player++;
        } else if (lasttouched_X >= DealtDeck_CurrentX && lasttouched_Y <= (DealtDeck_CurrentX + DeatlDeck.getCard().getImage().getWidth())) // if touched card is Dealt Deck
        {
            Log.d(TAG, "Inside Single Touch Dealt Deck Swap condtion");
            if (touchedcard != null) {
                swapFromDealtDeck(player.getMydeck(), cardindex, true);
                touchedcard = null;
                cardindex = -1;
            } else if (isLongTouched) {
                Collections.sort(tempListindex, Collections.<Integer>reverseOrder());        //Fix array out of bound exception as card always remove in descending order
                if ((HandCombination.isStraight(player.getMydeck(), tempListindex)) == false) {
                    Log.d(TAG, "Touched Cards are not straight set retry now");
                    tempListindex.clear();
                    return;
                }

                Log.d(TAG, "Inside Dealt Deck long touch swap");

                int size = tempListindex.size() - 1;
                int i = 0;
                Card Discarddeckcard;
                Discarddeckcard = DeatlDeck.Deal(true);
                while (i <= size)           // removal of card in sort ascending order
                {
                    Card removecard = player.removeCard(tempListindex.get(i), true);
                    discardedDeck.add(removecard);
                    i++;
                }
                tempListindex.clear();
                player.addToHand(Discarddeckcard);
                isLongTouched = false;
            }
            current_player++;
        }


    }

    /**
     * @param lasttouched_x Xcordinates of touched card
     * @param lasttouched_y Y cordinates to touched card
     * @return if touched belongs to a card, then index of card else -1
     */
    private int cardTouched(int lasttouched_x, int lasttouched_y) {
        int index = 0;
        Card localcard = null;
        while (index < player.decksize()) {
            localcard = player.getCard(index);
            if (lasttouched_x >= localcard.getCurrent_X() && lasttouched_x < (localcard.getCurrent_X() + localcard.getImage().getWidth()) && (lasttouched_y >= localcard.getCurrent_Y() && lasttouched_y <= (localcard.getCurrent_Y() + localcard.getImage().getWidth()))) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Add index of touch card in temporary array
     * and later use that array for multiple cards
     * removal
     *
     * @param event
     */
    public void addTouchedCardToLongTouched(MotionEvent event) {
        Log.d(TAG, "Inside long touch add card method");
        float lasttouched_X, lasttouched_Y;
        int index = -1;
        lasttouched_X = event.getX();
        lasttouched_Y = event.getY();
        index = cardTouched((int) lasttouched_X, (int) lasttouched_Y);
        isLongTouched = true;
        if (index > -1) {
            //tempLongtouchList.add(MainPlayer.getCard(index));
            tempListindex.add(index);
        }
    }

    /**
     * Program to show cards of all players
     * when a person will call minimum.
     */
    public void showdown() {
        Log.d(TAG, "Inside showdown method");
        int i = 0;
        ArrayList<String> playernames = new ArrayList<String>();
        ArrayList<Integer> playerscore = new ArrayList<Integer>();
        playerscore.add(player.getScore());
        playernames.add(player.getName());
        while (i < no_of_CPU_players) {
            AIPlayerlist.get(i).showcards();
            playerscore.add(AIPlayerlist.get(i).getScore());
            playernames.add(AIPlayerlist.get(i).getName());
            i++;

        }
        /* Score Card from Activity class
        Intent intent = new Intent(this.context, ScoreCard.class);
        intent.putStringArrayListExtra("playersname", playernames);
        intent.putIntegerArrayListExtra("playersscore", playerscore);
        context.startActivity(intent);
        */

        // Score Card from pop up
        ScoreCardPopup scoreCardPopup = new ScoreCardPopup(playernames, playerscore, context);
        scoreCardPopup.showScoreCard();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawDealtDeck(canvas);
        //setDiscardedDeck();
        drawDiscardedDeck(canvas);
        setMainPlayer();
        DrawMainPlayerDeck(canvas);
        SetTopCenterPlayerDeck();
        DrawTopCenterPlayerDeck(canvas);
        startGame(canvas);
    }

    /*
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        drawDealtDeck(canvas);
      //  setDiscardedDeck();
        drawDiscardedDeck(canvas);
      //  setMainPlayer();
        DrawMainPlayerDeck(canvas);
      //  SetTopCenterPlayerDeck();
        DrawTopCenterPlayerDeck(canvas);
        // startGame(canvas);


    }
    */


    private void startGame(Canvas canvas) {
        Log.d(TAG, "Inside Start Game method");
        if (current_player == no_of_players)
            current_player = 0;

        if (current_player == 0) {
            callminimum.draw(canvas);
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Toast.makeText(parent, "Your turn", 0).show();
                }
            });
        } else {
            AIPlayer currentaiplayer = AIPlayerlist.get(current_player - 1);
            if (currentaiplayer.getMydeck().Count() < 3) {
                pickBestCard(currentaiplayer.getMydeck(), discardedDeck.getTopCard());  //pick AI player deck,
            } else {


            }
            current_player++;
        }


    }

    /**
     * Method to pick best card based upon the largest card
     * and swap that card with either dealt deck or discard
     * deck
     *
     * @param playerdeck
     * @param card
     */
    private void pickBestCard(Deck playerdeck, Card card) {
        Log.d(TAG, "Inside pick best card method");
        Pair<Integer, Boolean> result = playerdeck.getLargestCardByRank(card);
        if (result.second == true) {
            swapFromDiscardedDeck(playerdeck, result.first, false);
        } else
            swapFromDealtDeck(playerdeck, result.first, false);

    }

    private void swapFromDiscardedDeck(Deck playerdeck, int cardindex, boolean showcardface) {
        Log.d(TAG, "inside swap from discarded deck");
        Card temp1 = discardedDeck.Deal(showcardface);
        Card temp = playerdeck.removeCard(cardindex, true);
        discardedDeck.add(temp);
        playerdeck.add(temp1);


    }

    private void swapFromDealtDeck(Deck playerdeck, int cardindex, boolean showcardface) {
        Log.d(TAG, "Inside swap from dealt deck");
        Card temp = DeatlDeck.Deal(showcardface);
        Card temp1 = playerdeck.removeCard(cardindex, true);
        playerdeck.add(temp);
        discardedDeck.add(temp1);
        Log.d(TAG, "Dealt Deck count" + DeatlDeck.Count());
        if (DeatlDeck.Count() == 0) {
            Log.d(TAG, "Dealt Deck empty refilling");
            DeatlDeck.refill(discardedDeck, DealtDeck_CurrentX, DealtDeck_CurrentY);
        }

    }

    private void drawDealtDeck(Canvas canvas) {
        Card localcard = DeatlDeck.getCard();
        canvas.drawBitmap(localcard.getImage(context, Card_Width, Card_Height), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);
    }


    private void drawDiscardedDeck(Canvas canvas) {
        //Log.d(TAG,"Inside Draw Discarded deck");
        Card localcard = discardedDeck.getTopCard();
        canvas.drawBitmap(localcard.getImage(context, Card_Width, Card_Height), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);

    }

    private void setMainPlayer() {
        //    Log.d(TAG, "Inside Set Main Player Method");
        Card localcard = null;
        Bitmap localimage = null;
        int currentiteration = 0;
        int Card_Gap = Screen_Width / 10;
        int Down_Card_Gap = 0;
        int Down_Card_Gap_positive = 0;
        int Down_Card_Gap_negative = 0;
        player.sortBySuit();
        //  Log.d(TAG,"Main Player Deck size"+MainPlayer.Count());
        while (currentiteration < player.decksize()) {
            localcard = player.getCard(currentiteration);
            localcard.setCurrent_Y(Screen_Height - localcard.getImage(context, Card_Width, Card_Height).getHeight());
            player.setCurrentCard(localcard, currentiteration);
            currentiteration++;
            if (Down_Card_Gap >= 0) {
                Down_Card_Gap_positive = Down_Card_Gap;
                localcard.setCurrent_X(Screen_Center_X + Down_Card_Gap_positive);
                Down_Card_Gap += Card_Gap;
            } else {
                Down_Card_Gap_negative = Down_Card_Gap;
                localcard.setCurrent_X(Screen_Center_X + Down_Card_Gap_negative);
            }
            Down_Card_Gap *= -1;

        }


    }

    private void DrawMainPlayerDeck(Canvas canvas) {
        // Log.d(TAG, " Inside Draw Main Player Deck");
        Card localcard;
        int currentiteration = 0;
        while (currentiteration < player.decksize()) {
            localcard = player.getCard(currentiteration);
            canvas.drawBitmap(localcard.getImage(context, Card_Width, Card_Height), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);
            currentiteration++;
        }


    }

    private void SetTopCenterPlayerDeck() {
        Log.d(TAG, "Inside Set Top Center Player Deck");
        Card localcard;
        Bitmap localimage = null;
        int currentiteration = 0;
        int Down_Card_Gap = 0;
        int Down_Card_Gap_Postive = 0;
        int Down_Card_Gap_negative = 0;
        int Card_Gap = Screen_Width / 10;
        int cards = AIPlayerlist.get(0).decksize();
        while (currentiteration < cards) {
            localcard = AIPlayerlist.get(0).getCard(currentiteration);
            localcard.setCurrent_Y(0);      //Y-Axis =0
            AIPlayerlist.get(0).setCurrentCard(localcard, currentiteration);
            currentiteration++;
            if (Down_Card_Gap >= 0) {
                Down_Card_Gap_Postive = Down_Card_Gap;
                localcard.setCurrent_X(Screen_Center_X + Down_Card_Gap_Postive);
                Down_Card_Gap += Card_Gap;
            } else {
                Down_Card_Gap_negative = Down_Card_Gap;
                localcard.setCurrent_X(Screen_Center_X + Down_Card_Gap_negative);
            }
            Down_Card_Gap *= -1;
        }
    }

    private void DrawTopCenterPlayerDeck(Canvas canvas) {
        Card localcard;
        int currentiteration = 0;
        int cardcount = AIPlayerlist.get(0).decksize();
        while (currentiteration < cardcount) {
            localcard = AIPlayerlist.get(0).getCard(currentiteration);
            canvas.drawBitmap(localcard.getImage(context, Card_Width, Card_Height), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);
            currentiteration++;
        }
    }

    public void setActivity(MainActivity mainActivity) {
        parent = mainActivity;

    }


}


/**
 * Class to implements Touch events
 */
class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = GestureListener.class.getSimpleName();  // To get name of class in Logging
    MySurfaceView mySurfaceView;

    public GestureListener(MySurfaceView paramMySurfaceView) {
        mySurfaceView = paramMySurfaceView;
    }

    @Override
    public void onLongPress(MotionEvent e) {

        //  Log.d(TAG,"Inside Long Pressed event");
        mySurfaceView.addTouchedCardToLongTouched(e);
    }


    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mySurfaceView.swapSingleTouchCard(e);
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "Inside On Double Tap event");
        return false;
    }
}

