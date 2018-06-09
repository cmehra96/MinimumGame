using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Graphics;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;


namespace minimumGame
{
    class Cards
    {

        public int corner_X;
        public int corner_Y;
        public int current_X;
        public int current_Y;
        public int middle_X;
        public int middle_Y;
        public Cards(Rank value, SuitType suit)
        {
            if (!Enum.IsDefined(typeof(SuitType), suit))
                throw new ArgumentOutOfRangeException("suit");
            if (!Enum.IsDefined(typeof(Rank), value))
                throw new ArgumentOutOfRangeException("rank");

            Suit = suit;
            CardValue = value;

        }
        public Cards(Rank value, SuitType suit, bool showcard )
        {
            if (!Enum.IsDefined(typeof(SuitType), suit))
                throw new ArgumentOutOfRangeException("suit");
            if (!Enum.IsDefined(typeof(Rank), value))
                throw new ArgumentOutOfRangeException("rank");

            Suit = suit;
            CardValue = value;
            showcardface = showcard;
            


        }

        public Cards(Rank value, SuitType suit, bool showcard, int current_X,int current_Y)
        {
            if (!Enum.IsDefined(typeof(SuitType), suit))
                throw new ArgumentOutOfRangeException("suit");
            if (!Enum.IsDefined(typeof(Rank), value))
                throw new ArgumentOutOfRangeException("rank");

            Suit = suit;
            CardValue = value;
            showcardface = showcard;
            this.current_X = current_X;
            this.current_Y = current_Y;

        }
        public enum SuitType
        {
            Clubs = 1,
            Spades,
            Hearts,
            Diamonds
        }

        public enum Rank
        {
            Ace = 1,
            Two,
            Three,
            Four,
            Five,
            Six,
            Seven,
            Eight,
            Nine,
            Ten,
            Jack,
            Queen,
            King,
        }

        public Rank CardValue { get; set; }

        public SuitType Suit { get; set; }

        private bool showcardface;
        //public Bitmap image { get; set; }
        //private CardImageLoader getimage;
        private int cardimageid;

        //public Bitmap GetImageFromResource(Context currentcontext)
        //{
        //    string imagename;
        //    Bitmap imageResized;
        //    if ((int)this.CardValue >= 2 && (int)this.CardValue <= 10)

        //        imagename = this.CardValue.ToString();
        //    else
        //        imagename = CardValue.ToString();
        //     imagename = this.Suit.ToString() + imagename;
        //    //image= minimumGame.Resource.Drawable.
        //    getimage = new CardImageLoader(currentcontext);
        //    imagename = imagename.ToLower();
        //    this.image = getimage.GetBitmapImage(imagename);
        //    cardimageid = currentcontext.Resources.GetIdentifier(imagename, "drawable", currentcontext.PackageName);
        //     imageResized=Bitmap.CreateScaledBitmap(image,
        //    (int)(image.Width * 0.5), (int)(image.Height * 0.5), false); 
        //     image=imageResized;
        //    return image;
        //}

       public int GetImageId(Context currentcontext)
        {
            int imagenumber;
            string imagename;
            if (showcardface == false)
            {
                imagename = "BlueBack";
            }
            else
            {
                if ((int)this.CardValue >= 2 && (int)this.CardValue <= 10)

                {
                    imagenumber = (int)this.CardValue;
                    imagename = imagenumber.ToString();
                }
                else
                    imagename = CardValue.ToString();
                imagename = this.Suit.ToString() + imagename;
            }
            imagename = imagename.ToLower(); // because Uppercase resource files dont work sometime in xamarin.
            cardimageid = currentcontext.Resources.GetIdentifier(imagename, "drawable", currentcontext.PackageName);
            return cardimageid;

        }
    }
}