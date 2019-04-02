package com.example.chetan.minimumgame.ScoreCard;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;

import com.example.chetan.minimumgame.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreCardPopup {


    private List<Items> itemsList = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ArrayList<String> playernames = new ArrayList<>();
    private ArrayList<Integer> playerscore = new ArrayList<>();
    Context context;

    public ScoreCardPopup(ArrayList<String> playernames, ArrayList<Integer> playerscore) {
        this.playernames = playernames;
        this.playerscore = playerscore;
    }

    public ScoreCardPopup(ArrayList<String> playernames, ArrayList<Integer> playerscore, Context context) {
        this.playernames = playernames;
        this.playerscore = playerscore;
        this.context = context;
    }

    public void showScoreCard() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_score_card);
        //Initialize and create a new adapter with layout named list found in activity_main layout
        listView = (ListView) dialog.findViewById(R.id.list);
        adapter = new CustomListAdapter(context, itemsList);
        listView.setAdapter(adapter);
        int i = 0;
        while (i < playernames.size()) //Add to the Items array
        {
            Items items = new Items();
            items.setName(playernames.get(i).toString());
            items.setScore(playerscore.get(i).toString());
            itemsList.add(items);
            i++;
        }
        //All done, so notify the adapter to populate the list using the Items Array
        adapter.notifyDataSetChanged();
        dialog.show();

    }
}
