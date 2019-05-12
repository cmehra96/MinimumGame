package com.example.chetan.minimumgame;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    MySurfaceView surfaceView;
    MySurfaceViewThread surfaceViewThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_main);

            surfaceView = (MySurfaceView) findViewById((R.id.surfaceView));
            surfaceViewThread= surfaceView.getThread();
            surfaceView.setActivity(this);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        surfaceView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        surfaceView.onPause();
        super.onPause();
    }


}
