package com.example.chetan.minimumgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import icepick.Icepick;
import icepick.State;

import static android.net.http.SslCertificate.restoreState;

public class MainActivity extends Activity {
    MySurfaceView surfaceView;
    MySurfaceViewThread surfaceViewThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_main);


            surfaceView = (MySurfaceView) findViewById((R.id.surfaceView));
            surfaceViewThread = surfaceView.getThread();
            surfaceView.setActivity(this);
            if(savedInstanceState!=null)
            {
                surfaceView.isResumed=true;
            }
           // Icepick.restoreInstanceState(this, savedInstanceState);
            // if(savedInstanceState!=null)
            //    surfaceView.restoreState(savedInstanceState);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
   //   surfaceView.onResume();
        Log.d("Main Activity","Inside on Resume method");
        super.onResume();
    }

    @Override
    protected void onPause() {
      //  surfaceView.onPause();
      Log.d("Main Activity","Inside on Pause method");
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Icepick.saveInstanceState(this, state);

        // surfaceView.saveState(state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to Quit");
        builder.setMessage("Select any one of the options");
        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNeutralButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // startActivity(new Intent(MainActivity.this,MainActivity.class));
                // finish();
                recreate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
