package com.example.chetan.minimumgame;

import android.annotation.SuppressLint;
import android.view.SurfaceHolder;
import android.util.Log;
import android.graphics.Canvas;


public class MySurfaceViewThread extends Thread {
    private MySurfaceView mySurfaceView;
    private SurfaceHolder mySurfaceHolder;
    boolean running;

    /**
     * Time per frame for 60 FPS
     */
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);

    public MySurfaceViewThread(SurfaceHolder paramSurfaceHolder, MySurfaceView paramSurfaceView)
    {
        mySurfaceHolder=paramSurfaceHolder;
        mySurfaceView=paramSurfaceView;
    }

    public void setRunning(boolean run){
        running=run;

    }

    public SurfaceHolder getMySurfaceHolder()
    {
        return mySurfaceHolder;
    }
    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c;
        long frameStarttime= 0;
        long frameTime;
        while(running)
        {
            if(mySurfaceHolder==null)
            {
                return;   // To fix Surface not found error;
            }
            c=null;
            try{
                frameStarttime=System.nanoTime();
                c= mySurfaceHolder.lockCanvas();
                Thread.sleep(100);
                synchronized (mySurfaceHolder) {
                   mySurfaceView.render(c);
                }
            }
            catch(Exception e)
            {
               // Log.e("Thread Class run method","exception",e);
            }

            finally {
                if (c!=null)
                {
                    mySurfaceHolder.unlockCanvasAndPost(c);
                }
            }

            // calculate the time required to draw the frame in ms
            frameTime = (System.nanoTime() - frameStarttime) / 1000000;

            if (frameTime < MAX_FRAME_TIME){
                try {
                    Thread.sleep(MAX_FRAME_TIME - frameTime);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

        }

    }
}
