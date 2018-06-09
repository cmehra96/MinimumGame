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


namespace minimumGame
{
    class Deck
    {
        private List<Cards> deck = new List<Cards>();
        public Deck()
        {
            for(int i=1;i<=13;i++)

            {
                for (int j=1;j<=4;j++)
                {
                    deck.Add(new Cards((Cards.Rank)i, (Cards.SuitType)j, false));
                   
                }
            }
        }

        public Deck(int Card_Current_X, int Card_Current_Y)
        {
                for(int i=1;i<=13;i++)
            {
                for(int j=1;j<=4;j++)
                {
                    deck.Add(new Cards((Cards.Rank)i, (Cards.SuitType)j, false, Card_Current_X,Card_Current_Y));
                }
            }
        }


        public List<Cards> ToList()
        {
            return deck;
        }
    }
}