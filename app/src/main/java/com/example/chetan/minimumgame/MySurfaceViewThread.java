package com.example.chetan.minimumgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.util.Log;
import android.graphics.Canvas;


public class MySurfaceViewThread extends Thread { //implements Choreographer.FrameCallback {
    private static final String TAG = MySurfaceViewThread.class.getSimpleName();  // To get name of class in Logging
    /**
     * Time per frame for 60 FPS
     */
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);
    boolean running;
    private MySurfaceView mySurfaceView;
    private SurfaceHolder mySurfaceHolder;
    private Choreographer myChoreographer = null;
    private Looper myLooper = null;
    boolean pause =false;



    public MySurfaceViewThread(SurfaceHolder paramSurfaceHolder, MySurfaceView paramSurfaceView) {
        mySurfaceHolder = paramSurfaceHolder;
        mySurfaceView = paramSurfaceView;
    }

    public void setRunning(boolean run) {
        running = run;

    }

    public SurfaceHolder getMySurfaceHolder() {
        return mySurfaceHolder;
    }

    /*
        @Override
        public void run() {
            Log.d(TAG,"Inside run method");
            Looper.prepare();
            myChoreographer=Choreographer.getInstance();
            myChoreographer.postFrameCallbackDelayed(this,0);
            myLooper=Looper.myLooper();
            Looper.loop();
            myChoreographer=null;
            Log.d(TAG,"Exiting rendering");

        }

        @Override
        public void doFrame(long l) {
            Log.d(TAG,"imside do frame method");
            Canvas c = null;
            try {
                c = mySurfaceHolder.lockCanvas();
                mySurfaceView.render(c);
            } catch (Exception e) {
                Log.e("Thread Class run method", "exception", e);
            } finally {
                if (c != null) {
                    mySurfaceHolder.unlockCanvasAndPost(c);
                }


            }
        }

        public void stopThread()
        {
            myLooper.quit();
        }
        */
    @SuppressLint("WrongCall")

    @Override
    public void run() {
        Canvas c;
        long frameStarttime = 0;
        long frameTime;
        while (running) {
            if (mySurfaceHolder == null) {
                return;   // To fix Surface not found error;
            }
            c = null;
            try {
                frameStarttime = System.nanoTime();
                c = mySurfaceHolder.lockCanvas();
                Thread.sleep(100);
                synchronized (mySurfaceHolder) {
                    if(!pause)
                    mySurfaceView.render(c);
                }

                //  mySurfaceView.render(c);
            } catch (Exception e) {
                // Log.e("Thread Class run method","exception",e);
            } finally {
                if (c != null) {
                    mySurfaceHolder.unlockCanvasAndPost(c);
                }
            }

            // calculate the time required to draw the frame in ms
            frameTime = (System.nanoTime() - frameStarttime) / 1000000;

            if (frameTime < MAX_FRAME_TIME) {
                try {
                    Thread.sleep(MAX_FRAME_TIME - frameTime);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

        }

    }

    public synchronized void restoreState(Bundle savedState)
    {
        synchronized (mySurfaceHolder)
        {

        }
    }

    public Bundle saveState(Bundle map)
    {
        return null;
    }



    public void onPause() {
        synchronized (mySurfaceHolder)
        {
            pause=true;
        }
    }

    public void onResume()
    {
        synchronized (mySurfaceHolder)
        {
            pause=false;
        }
    }
}
