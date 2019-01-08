package com.example.chetan.minimumgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.PorterDuff;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.os.Handler;
import android.view.ViewConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogRecord;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MySurfaceView.class.getSimpleName();  // To get name of class in Logging
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
    private Deck MainPlayer;
    private Deck DeatlDeck;
    private Deck DiscardedDeck;
    private Bitmap BlueBackCard;
    private int DealtDeck_CurrentX;
    private int DealtDeck_CurrentY;
    private int DiscardedDeck_CurrentX;
    private int DiscardedDeck_CurrentY;
    private boolean isLongTouched=false;
    final Handler longpressedhandler= new Handler();
    private Card touchedcard=null;
    private int cardindex=-1;
    private Card replacedcard=null;
    private GestureDetector gestureDetector;
    private long startclicktime;
    private final int MIN_CLICK_DURATION=1000;
    private ArrayList<Card> tempLongtouchList= new ArrayList<>();
    private ArrayList<Integer> tempListindex= new ArrayList<>();
    private ArrayList<Player> Playerlist;
    private final int no_of_players=6;



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
        BlueBackCard = DecodeSampleBitmapFromResource(getResources(), Card.GetBlueBackCardImageId(context), Card_Width, Card_Height);
        MainPlayer = new Deck();
        DeatlDeck = new Deck();
        DiscardedDeck = new Deck();
        DealtDeck_CurrentX = Screen_Center_X - Card_Width;
        DealtDeck_CurrentY = Screen_Center_Y - Card_Height / 2;
        DiscardedDeck_CurrentX= Screen_Center_X+Card_Width;
        DiscardedDeck_CurrentY= Screen_Center_Y- Card_Height/2;
        Playerlist= new ArrayList<Player>(no_of_players);
        initializePlayers();
    }

    private void initializePlayers() {
        for(int i=0;i<no_of_players;i++)
            if(0==i)
                Playerlist.add(new Player("You"));

        else
            Playerlist.add(new Player("Bot" +i));
    }


    private void AllocatedCardList() {
      //  Log.d(TAG, "inside AllocatedCardList method");
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                DeatlDeck.add(new Card(rank, suit, false, DealtDeck_CurrentX, DealtDeck_CurrentY , BlueBackCard));
            }

        }

        DealCards();
    }

    private void DealCards() {
      //  Log.d(TAG, "Inside Deal Card method");
      /*  MainPlayer.add(DeatlDeck.Deal(true));
        MainPlayer.add(DeatlDeck.Deal(true));
        MainPlayer.add(DeatlDeck.Deal(true));
        DiscardedDeck.add(DeatlDeck.Deal(true));
        MainPlayer.sort();
        */
        DeatlDeck.shuffle();
        for(int i =0; i<no_of_players;i++)
        {
            if(0==i)
            {
                Playerlist.get(i).addToHand(DeatlDeck.Deal(true));
                Playerlist.get(i).addToHand(DeatlDeck.Deal(true));

            }
            else
            {
                Playerlist.get(i).addToHand(DeatlDeck.Deal(false));
                Playerlist.get(i).addToHand(DeatlDeck.Deal(false));


            }
        }
    }

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
     * @param e: to determine which type of event has performed.
     */
    public void swapSingleTouchCard(MotionEvent e) {
       // Log.d(TAG,"Inside swapSingle Touch Method");
        float lasttouched_X, lasttouched_Y;
        Card localcard;
        int index = -1;
        lasttouched_X = e.getX();
        lasttouched_Y = e.getY();

        // Code for long touch and single touch swap
        if(((lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X < (DiscardedDeck_CurrentX + DiscardedDeck.getCard().getImage().getWidth())))==false &&isLongTouched) // Main Player Deck, card  is touched
        {
       //     Log.d(TAG,"Inside Long Touch Add Card condition");
            addTouchedCardToLongTouched(e);
        }
        else if (isLongTouched==false && touchedcard==null)
        {
         //   Log.d(TAG,"Inside Single Touch Card Condition");
            index = cardTouched((int) lasttouched_X, (int) lasttouched_Y);
            if (index > -1) {
                touchedcard = MainPlayer.getCard(index);
                cardindex = index;
            }
        }
        else if(lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X < (DiscardedDeck_CurrentX + DiscardedDeck.getCard().getImage().getWidth())) //if touched card is Discard deck
        {
            if(touchedcard!=null)       // to replace with single card
            {
             //   Log.d(TAG,"Inside Single Touch Card Swap Conditon ");
                replacedcard = DiscardedDeck.Deal(true);
                Card swapcard = MainPlayer.swapCard(replacedcard, cardindex);
             //   Log.d(TAG,String.valueOf(swapcard.getCurrent_X()));
             //   Log.d(TAG,String.valueOf(swapcard.getCurrent_Y()));
                swapcard.setCurrent_X(DiscardedDeck_CurrentX);
                swapcard.setCurrent_Y(DiscardedDeck_CurrentY);
            //    Log.d(TAG,String.valueOf(swapcard.getCurrent_X()));
            //    Log.d(TAG,String.valueOf(swapcard.getCurrent_Y()));
                DiscardedDeck.add(swapcard);
                touchedcard = null;
                cardindex = -1;

            }
            else if(isLongTouched)
            {
                int size=tempListindex.size()-1;
                int i=0;
                Card Discarddeckcard;
                Discarddeckcard=DiscardedDeck.Deal(true);
               while(i<=size)
               {
                   Card removecard=MainPlayer.removeCard(tempListindex.get(i));
                   //  tempLongtouchList.remove(i);
                    removecard.setCurrent_X(DiscardedDeck_CurrentX);
                    removecard.setCurrent_Y(DiscardedDeck_CurrentY);
                    DiscardedDeck.add(removecard);
                    i++;

               }
               tempListindex.clear();
              // tempLongtouchList.clear();
               MainPlayer.add(Discarddeckcard);
               isLongTouched=false;
            }
        }

    }

    private int cardTouched(int lasttouched_x, int lasttouched_y) {
        int index=0;
        Card localcard=null;
        while (index<MainPlayer.Count())
        {
            localcard=MainPlayer.getCard(index);
            if(lasttouched_x>= localcard.getCurrent_X() && lasttouched_x<(localcard.getCurrent_X()+localcard.getImage().getWidth())) //&& (lasttouched_y>=localcard.getCurrent_Y() &&lasttouched_y <(localcard.getCurrent_Y()+localcard.getImage().getWidth())))
            {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void addTouchedCardToLongTouched(MotionEvent event)
    {

        float lasttouched_X, lasttouched_Y;
        int index=-1;
        lasttouched_X=event.getX();
        lasttouched_Y=event.getY();
        index=cardTouched((int)lasttouched_X,(int)lasttouched_Y);
        isLongTouched=true;
        if(index>-1)
        {
            //tempLongtouchList.add(MainPlayer.getCard(index));
            tempListindex.add(index);

        }




    }

    public void render(Canvas canvas)
    {
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        drawDealtDeck(canvas);
        setDiscardedDeck();
        if(DiscardedDeck.Count()==0) {          //To add card in discarded deck only first time

        }
        drawDiscardedDeck(canvas);
        setMainPlayer();
        DrawMainPlayerDeck(canvas);

    }
    private void drawDealtDeck (Canvas canvas){
        Card localcard = DeatlDeck.getCard();
        canvas.drawBitmap(localcard.getImage(), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);
    }

    private void drawDiscardedDeck(Canvas canvas) {
        //Log.d(TAG,"Inside Draw Discarded deck");
        Card localcard= DiscardedDeck.getCard();
        canvas.drawBitmap(localcard.getImage(),localcard.getCurrent_X(),localcard.getCurrent_Y(),null);
     //  Log.d(TAG,"discarded dec currentx");
       // Log.d(TAG,String.valueOf(localcard.getCurrent_X()));
     //   Log.d(TAG,String.valueOf(localcard.getCurrent_Y()));
    }

    private void setDiscardedDeck() {
   // Log.d(TAG,"Inside set Discarded Deck");
    Card localcard;
    Bitmap localimage;
    localcard=DiscardedDeck.getCard();
    localimage= DecodeSampleBitmapFromResource(getResources(),localcard.GetImageId(context),Card_Width,Card_Height);
    localcard.setImage(localimage);
    localcard.setCurrent_X(DiscardedDeck_CurrentX);
    localcard.setCurrent_Y(DiscardedDeck_CurrentY);
    //DiscardedDeck.add(localcard);

    }


    private void setMainPlayer ()
        {
        //    Log.d(TAG, "Inside Set Main Player Method");
            Card localcard = null;
            Bitmap localimage = null;
            int currentiteration = 0;
            int Card_Gap= Screen_Width/10;
            int Down_Card_Gap = 0;
            int Down_Card_Gap_positive = 0;
            int Down_Card_Gap_negative = 0;
         /*   MainPlayer.sort();
            while (currentiteration < MainPlayer.Count()) {
                localcard = MainPlayer.getCard(currentiteration);
                localimage = DecodeSampleBitmapFromResource(getResources(), localcard.GetImageId(context), Card_Width, Card_Height);
                localcard.setImage(localimage);
                localcard.setCurrent_Y(Screen_Height - localcard.getImage().getHeight());
                MainPlayer.setCurrentCard(localcard, currentiteration);
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
            */




        }

        private void DrawMainPlayerDeck (Canvas canvas)
        {
           // Log.d(TAG, " Inside Draw Main Player Deck");
            Card localcard;
            int currentiteration = 0;
            while (currentiteration < MainPlayer.Count()) {
                localcard = MainPlayer.getCard(currentiteration);
                canvas.drawBitmap(localcard.getImage(), localcard.getCurrent_X(), localcard.getCurrent_Y(), null);
                currentiteration++;
            }

        }


        private Bitmap DecodeSampleBitmapFromResource (Resources res,int resId,
        int reqWidth, int reqHeight){

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        private int calculateInSampleSize (BitmapFactory.Options options,int reqWidth, int reqHeight)
        {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
           if (height > reqHeight || width > reqWidth) {


                int heightratio= (int)Math.round((double)height/reqHeight);
                int widthratio= (int)Math.round((double)width/reqWidth);
                inSampleSize= heightratio < widthratio ? widthratio : heightratio;
            }

              return inSampleSize;
        }
    }

/**
 * Class to implements Touch events
 */
    class GestureListener extends GestureDetector.SimpleOnGestureListener{
    private static final String TAG = GestureListener.class.getSimpleName();  // To get name of class in Logging
        MySurfaceView mySurfaceView;
        public GestureListener(MySurfaceView paramMySurfaceView)
        {
            mySurfaceView=paramMySurfaceView;
        }

    @Override
    public void onLongPress(MotionEvent e) {

       //  Log.d(TAG,"Inside Long Pressed event");
            mySurfaceView.addTouchedCardToLongTouched(e);
    }


    @Override
    public boolean onDown(MotionEvent e) {

             return  false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mySurfaceView.swapSingleTouchCard(e);
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG,"Inside On Double Tap event");
        return false;
    }
}

