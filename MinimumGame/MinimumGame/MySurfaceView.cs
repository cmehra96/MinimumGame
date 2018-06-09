using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Content.Res;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Com.Bumptech.Glide;

namespace minimumGame
{
    class MySurfaceView : SurfaceView, ISurfaceHolderCallback
    {
        ISurfaceHolder holder;
        MySurfaceViewThread thread;
        Context context;
        Deck FaceDownDeck;
       
        DisplayMetrics metrics;
        int Screen_Center_X;
        int Screen_Center_Y;
        int Screen_Width;
        int Screen_Height;
        int Screen_Top_Middle_X;
        int Screen_Top_Middle_Y;
        int Screen_Bottom_Middle_X;
        int Screen_Bottom_Middle_Y;
        float density;
        int Card_Width;
        int Card_Height;
        int Down_Card_Gap;
        Deck ShowedCardDeck;
        Deck MainPlayer;
        public MySurfaceView(Context context):base(context)
        {
            this.context = context;
            metrics = Resources.DisplayMetrics;
            
            Init();

        }

        public MySurfaceView(Context context, IAttributeSet attrs):base(context, attrs)
        {
         this.context=context;
            metrics = Resources.DisplayMetrics;

            Init();
        }
        private void Init()
        {
            Console.WriteLine("Init method start");
           // SurfaceView surfaceview = this;
            holder = Holder;
            holder.AddCallback(this);
            this.thread = new MySurfaceViewThread(holder,this);
            Focusable=true;
           

        }

        public void SurfaceChanged(ISurfaceHolder holder, [GeneratedEnum] Format format, int width, int height)
        {
            //throw new NotImplementedException();
        }

        public void SurfaceCreated(ISurfaceHolder holder)
        {
            this.thread.SetRunning(true);
            this.thread.Start();
            Initializevariable();


        }

        private void Initializevariable()
        {
            Screen_Width = metrics.WidthPixels;
            Screen_Height = metrics.HeightPixels;
            density = metrics.Density;
            Card_Width = (int)(125.0F * density);
            Card_Height = (int)(93.0F * density);
            Screen_Center_X = Screen_Width / 2;
            Screen_Center_Y = Screen_Height / 2;
            Screen_Top_Middle_X = Screen_Center_X - Card_Width;
            Screen_Top_Middle_Y = Screen_Center_Y - Card_Height;
            Screen_Bottom_Middle_X = Screen_Center_X - Card_Width;
            Screen_Bottom_Middle_Y = Screen_Height - Card_Width;
            FaceDownDeck = new Deck(Screen_Center_X - Card_Width/2, Screen_Center_Y- Card_Height/2);
        }

        public void SurfaceDestroyed(ISurfaceHolder holder)
        {
           // throw new NotImplementedException();
        }

        public  void Render(Canvas paramCanvas)
        {

            try
            {
                // paramCanvas.DrawColor(Android.Graphics.Color.Blue);


                int i = 0;
                Down_Card_Gap = 0;
                foreach (Cards localcard in FaceDownDeck.ToList())
                {

                    Bitmap localimage = DecodeSampledBitmapFromResource(Resources, localcard.GetImageId(context), Card_Width, Card_Height);
                    // Bitmap localimage = BitmapFactory.DecodeResource(Resources, localcard.GetImageId(context)); 
                   
                    Bitmap rotatedimage = RotateBitmap(localimage, 180);
                   
                    paramCanvas.DrawBitmap(rotatedimage, (Screen_Center_X - Card_Width / 2)+Down_Card_Gap, (Screen_Height - Card_Height), null);
                    //   paramCanvas.DrawBitmap(localimage, (Screen_Center_X - Card_Width / 2), (Screen_Center_Y - Card_Height), null);
                    
                    
                    if (i++ == 3)
                    { break; }
                    if (Down_Card_Gap > 0)
                    {
                        Down_Card_Gap += Card_Width / 2; 
                    }
                    else
                    {
                        Down_Card_Gap -= Card_Width / 2;
                    }
                    Down_Card_Gap *= -1;
                }
            }   
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.ToString());
            }

        }

        private Bitmap DecodeSampledBitmapFromResource(Resources resources, int cardid, int card_Width, int card_Height)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.InJustDecodeBounds = true;
            Bitmap image = BitmapFactory.DecodeResource(resources, cardid,options);
            options.InSampleSize = CalculateInSampleSize(options, card_Width, card_Height);
            options.InJustDecodeBounds = false;
            return BitmapFactory.DecodeResource(resources, cardid, options);
        }

        private int CalculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
        {

            // Raw height and width of image
           int width = options.OutWidth;
            int height = options.OutHeight;
            int samplesize = 1;
            if(height > reqHeight || width > reqWidth)
            {
                // Calculate ratios of height and width to requested height and width
                int heightratio = (int)Math.Round((double)height / reqHeight);
                int widthratio = (int)Math.Round((double)width / reqWidth);
                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                samplesize = heightratio < widthratio ? widthratio : heightratio;
               
            }
            return samplesize;
        }

        private Bitmap RotateBitmap(Bitmap localimage, float angle)
        {
         
            Matrix matrix = new Matrix();
            matrix.PostRotate(angle);
            
            Bitmap resized= Bitmap.CreateBitmap(localimage, 0, 0, localimage.Width, localimage.Height, matrix, true);
            localimage.Recycle();
            return resized;

        }
    }
}