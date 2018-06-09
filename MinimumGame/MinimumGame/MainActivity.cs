using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Views;
using System;
using System.Diagnostics;
namespace minimumGame
{
    [Activity(Label = "MinimumGame", MainLauncher = true, Icon = "@drawable/icon", Theme ="@style/AppTheme")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            try
            {
                // Set our view from the "main" layout resource
                RequestedOrientation = Android.Content.PM.ScreenOrientation.Landscape;
                SetContentView(Resource.Layout.Main);
                MySurfaceView surfaceview1;
                surfaceview1 = (MySurfaceView)FindViewById(Resource.Id.surfaceView);
            }
            catch(Exception e)
            {
               System.Diagnostics.Debug.WriteLine(e.ToString());
            
            }

        
        }
    }
}

