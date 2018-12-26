package com.example.chetan.minimumgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {

           setContentView(R.layout.activity_main);
       //   MySurfaceView surfaceView;
        //  surfaceView = (MySurfaceView) findViewById((R.id.surfaceView));


       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

    }
}
