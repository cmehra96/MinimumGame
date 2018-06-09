using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using System.Threading;
using Android.Graphics;

namespace minimumGame
{
    class MySurfaceViewThread:BaseThread
    {
        private MySurfaceView mysurfaceview;
        private ISurfaceHolder myThreadSurfaceHolder;
        bool running;

        public MySurfaceViewThread(ISurfaceHolder paramSurfaceHolder, MySurfaceView paramSurfaceView)
        {
            mysurfaceview = paramSurfaceView;
            myThreadSurfaceHolder = paramSurfaceHolder;

        }
        public override void RunThread()
        {
            Canvas c;
            while (running)
            {
                c = null;
                try
                {
                    c = myThreadSurfaceHolder.LockCanvas(null);
                    mysurfaceview.Render(c);


                }
                finally
                {
                    if (c != null)
                    {
                        myThreadSurfaceHolder.UnlockCanvasAndPost(c);   
                    }
                    running = false;
                }
            }

        }
        public override void SetRunning(bool paramBoolean)
        {
            running = paramBoolean;
        }
    }


    abstract class BaseThread
    {
        private Thread _thread;

        protected BaseThread()
        {
            _thread = new Thread(new ThreadStart(this.RunThread));
        }

        // Thread methods / properties
        public void Start() { _thread.Start(); }
       // public void Join()  _thread.Join();
        //public bool IsAlive => _thread.IsAlive;

        // Override in base class
        public abstract void RunThread();
        public abstract void SetRunning(bool param);
    }

}