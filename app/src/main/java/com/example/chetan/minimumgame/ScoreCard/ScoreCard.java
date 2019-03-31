package com.example.chetan.minimumgame.ScoreCard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.chetan.minimumgame.MainActivity;
import com.example.chetan.minimumgame.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreCard extends AppCompatActivity {

    private List<Items> itemsList = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        //Initialize and create a new adapter with layout named list found in activity_main layout
        getActionBar().setDisplayHomeAsUpEnabled(false); //Disable default back button of activity
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, itemsList);
        listView.setAdapter(adapter);
        ArrayList playername = getIntent().getStringArrayListExtra("playersname");
        ArrayList playerscore = getIntent().getIntegerArrayListExtra("playersscore");
        int i = 0;
        while (i < playername.size()) //Add to the Items array
        {
            Items items = new Items();
            items.setName(playername.get(i).toString());
            items.setScore(playerscore.get(i).toString());
            itemsList.add(items);
            i++;
        }
        //All done, so notify the adapter to populate the list using the Items Array
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
      //  Intent intent= new Intent(this, MainActivity.class);
      //  startActivity(intent);
     //   this.finish();
      //  super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
