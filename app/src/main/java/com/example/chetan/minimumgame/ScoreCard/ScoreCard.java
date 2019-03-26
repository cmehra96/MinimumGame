package com.example.chetan.minimumgame.ScoreCard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
}
