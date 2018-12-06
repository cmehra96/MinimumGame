package com.example.chetan.minimumgame;

import android.annotation.SuppressLint;
import android.view.SurfaceHolder;
import android.util.Log;
import android.graphics.Canvas;


public class MySurfaceViewThread extends Thread {
    private MySurfaceView mySurfaceView;
    private SurfaceHolder mySurfaceHolder;
    boolean running;

    public MySurfaceViewThread(SurfaceHolder paramSurfaceHolder, MySurfaceView paramSurfaceView)
    {
        mySurfaceHolder=paramSurfaceHolder;
        mySurfaceView=paramSurfaceView;
    }

    public void setRunning(boolean run){
        running=run;

    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c;
        while(running)
        {
            c=null;
            try{
                c= mySurfaceHolder.lockCanvas();
                mySurfaceView.render(c);
            }
            catch(Exception e)
            {
                Log.e("Thread Class run method","exception",e);
            }

            finally {
                if (c!=null)
                {
                    mySurfaceHolder.unlockCanvasAndPost(c);
                }
            }

        }
    }
}
