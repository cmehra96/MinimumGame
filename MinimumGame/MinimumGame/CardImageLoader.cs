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
using Android.Graphics;

namespace minimumGame
{
   public class CardImageLoader: ImageView
    {
        private Context context;

        public CardImageLoader(Context context) :base(context)
        {
            this.context = context;
        }

        public Bitmap GetBitmapImage(string cardname)
        {

                    
                int resid = context.Resources.GetIdentifier(cardname, "drawable",context.PackageName);
                return BitmapFactory.DecodeResource(context.Resources, resid);
                    

            

        }

    }

}