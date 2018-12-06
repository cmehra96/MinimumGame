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

import java.util.ArrayList;
import java.util.Calendar;
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
        Log.d(TAG, "Inside Surface Created method");
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
    }


    private void AllocatedCardList() {
        Log.d(TAG, "inside AllocatedCardList method");
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                DeatlDeck.add(new Card(rank, suit, false, DealtDeck_CurrentX, DealtDeck_CurrentY , BlueBackCard));
            }

        }
        DealCards();
    }

    private void DealCards() {
        Log.d(TAG, "Inside Deal Card method");
        MainPlayer.add(DeatlDeck.Deal(true));
        MainPlayer.add(DeatlDeck.Deal(true));
       // MainPlayer.add(DeatlDeck.Deal(true));

    }

   // @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
         Log.d(TAG,"Inside Touch Event");
        float lasttouched_X, lasttouched_Y;
        Card localcard;
        int index=-1;
        Log.d(TAG,"Inside OnTouch event");
        gestureDetector.onTouchEvent(event);
        return true;

      /*  if(event.getAction()==MotionEvent.ACTION_DOWN)
                longpressedhandler.postDelayed(longpressedrunnable,ViewConfiguration.getLongPressTimeout());
        if((event.getAction()==MotionEvent.ACTION_MOVE)||(event.getAction()==MotionEvent.ACTION_UP))
            {
                longpressedhandler.removeCallbacks(longpressedrunnable);
                swapSingleTouchCard(event);
            }

        return super.onTouchEvent(event);
        */
        //Working code till now :)
       /*switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                longpressedhandler.postDelayed(longpressedrunnable,500);
                break;

            case MotionEvent.ACTION_MOVE:

           case MotionEvent.ACTION_UP:
               longpressedhandler.removeCallbacks(longpressedrunnable);
               if(!isLongTouched)
               {
                   swapSingleTouchCard(event);

               }

               break;

        }
        return true;
        */

        /*switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
              //  startclicktime=Calendar.getInstance().getTimeInMillis();
                break;

            case  MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                long clickDuration=Calendar.getInstance().getTimeInMillis()-startclicktime;
                if(clickDuration>=MIN_CLICK_DURATION)
                {
                    Log.d(TAG,"Inside long press");
                }
                else
                {
                    Log.d(TAG,"Inside single click ");
                    swapSingleTouchCard(event);
                }
                break;
        }
        return true;

*/



         //  if(event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE||event.getAction()==MotionEvent.ACTION_UP)
            //    swapSingleTouchCard(event);

       // return true;

        //  return true;

       // if(gestureDetector.onTouchEvent(event))
      //  {
      //      return true;
       // }


    }
        // Code for Long touch event
    Runnable longpressedrunnable= new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"Inside long pressed event");
            isLongTouched=true;
        }
    };


    public void swapSingleTouchCard(MotionEvent e) {
        float lasttouched_X, lasttouched_Y;
        Card localcard;
        int index = -1;
        lasttouched_X = e.getX();
        lasttouched_Y = e.getY();

        if (((lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X < (DiscardedDeck_CurrentX + DiscardedDeck.getCard().getImage().getWidth())))==false &&isLongTouched) {

            addTouchedCardToLongTouched(e);
        }
        else {
            if (touchedcard == null)       // To find single touch card
            {
                index = cardTouched((int) lasttouched_X, (int) lasttouched_Y);
                if (index > -1) {
                    touchedcard = MainPlayer.getCard(index);
                    cardindex = index;
                }
            } else if (touchedcard != null && ((lasttouched_X >= DiscardedDeck_CurrentX && lasttouched_X < (DiscardedDeck_CurrentX + DiscardedDeck.getCard().getImage().getWidth()))))  //&& (lasttouched_Y>=DiscardedDeck_CurrentY && lasttouched_Y<(DiscardedDeck_CurrentY+ DiscardedDeck.getCard().getImage().getWidth()))))
            {

                replacedcard = DiscardedDeck.Deal(true);
                Card swapcard = MainPlayer.swapCard(replacedcard, cardindex);
                swapcard.setCurrent_X(DiscardedDeck_CurrentX);
                swapcard.setCurrent_Y(DiscardedDeck_CurrentY);
                DiscardedDeck.add(swapcard);
                touchedcard = null;
                cardindex = -1;
            }
        }
        // Code for long touch and single touch swap
        if(isLongTouched &&)
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
            tempLongtouchList.add(MainPlayer.getCard(index));
        }




    }

 /* @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        drawDealtDeck(canvas);

        if(DiscardedDeck.Count()==0) {          //To add card in discarded deck only first time
            setDiscardedDeck();
        }
        drawDiscardedDeck(canvas);
        setMainPlayer();
        DrawMainPlayerDeck(canvas);
    }
*/
    public  void render(Canvas canvas)
    {
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        drawDealtDeck(canvas);

        if(DiscardedDeck.Count()==0) {          //To add card in discarded deck only first time
            setDiscardedDeck();
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
        Log.d(TAG,"Inside Draw Discarded deck");
        Card localcard= DiscardedDeck.getCard();
        canvas.drawBitmap(localcard.getImage(),localcard.getCurrent_X(),localcard.getCurrent_Y(),null);

    }

    private void setDiscardedDeck() {
    Log.d(TAG,"Inside set Discarded Deck");
    Card localcard;
    Bitmap localimage;
    localcard=DeatlDeck.Deal(true);
    localimage= DecodeSampleBitmapFromResource(getResources(),localcard.GetImageId(context),Card_Width,Card_Height);
    localcard.setImage(localimage);
    localcard.setCurrent_X(DiscardedDeck_CurrentX);
    localcard.setCurrent_Y(DiscardedDeck_CurrentY);
    DiscardedDeck.add(localcard);

    }


    private void setMainPlayer ()
        {
            Log.d(TAG, "Inside Set Main Player Method");
            Card localcard = null;
            Bitmap localimage = null;
            int currentiteration = 0;
            int Down_Card_Gap = 0;
            int Down_Card_Gap_positive = 0;
            int Down_Card_Gap_negative = 0;
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
                    Down_Card_Gap += 75;
                } else {
                    Down_Card_Gap_negative = Down_Card_Gap;
                    localcard.setCurrent_X(Screen_Center_X + Down_Card_Gap_negative);
                }
                Down_Card_Gap *= -1;

            }

        }

        private void DrawMainPlayerDeck (Canvas canvas)
        {
            Log.d(TAG, " Inside Draw Main Player Deck");
            Card localcard;
            int currentiteration = 0;
            while (currentiteration < MainPlayer.Count()) {
                localcard = MainPlayer.getCard(currentiteration);
                canvas.drawBitmap(localcard.getImage(), localcard.getCurrent_X(), localcard.getCurrent_X(), null);
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

        Log.d(TAG,"Inside Long Pressed event");
            mySurfaceView.addTouchedCardToLongTouched(e);
    }


    @Override
    public boolean onDown(MotionEvent e) {

      // don't return false here or else none of the other
      // gestures will work

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

