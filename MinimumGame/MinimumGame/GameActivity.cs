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
using Com.Bumptech.Glide; 
namespace minimumGame
{
    [Activity(Label = "GameActivity")]
    public class GameActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.GameActivity);

            Button gamelauncher = FindViewById<Button>(Resource.Id.NewGame);

            gamelauncher.Click += Gamelauncher_Click;

        }

        private void Gamelauncher_Click(object sender, EventArgs e)
        {
            List<Cards> cards = new List<Cards>();

            for (int suit = (int)Cards.SuitType.Clubs; suit <= (int)Cards.SuitType.Diamonds; suit++)
            {
                for (int rank = (int)Cards.Rank.Ace; rank <= (int)Cards.Rank.King; rank++)
                {
                    try
                    {
                        Cards card = new Cards((Cards.Rank)rank, (Cards.SuitType)suit, false);
                        cards.Add(card);
                       // ImageView facedownimage = (ImageView)FindViewById(Resource.Id.FaceDownDeck);
                       // facedownimage.SetImageBitmap(card.image);
                        //facedownimage.SetMaxHeight(100);
                        //facedownimage.SetMaxWidth(100);
                        //facedownimage.SetImageResource(card.cardimageid);
                       //Glide.With(this).Load(card.cardimageid).Into(facedownimage);
                    }
                    catch(Exception ex)
                    { Console.WriteLine(ex.StackTrace);

                    }
                }
               
            }
            
        }
    }
}