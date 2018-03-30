package com.example.taco.hearthstonecard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CardListActivity extends AppCompatActivity {
    private ListView listeCard;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        getSupportActionBar().hide();
        Intent mainIntent = getIntent();

        String typeRequete = mainIntent.getStringExtra("typeRequete");
        String requete = mainIntent.getStringExtra("requete");

        data.add(typeRequete);
        data.add(requete);


        listeCard = (ListView) findViewById(R.id.listeCard);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, data);
        listeCard.setAdapter(adapter);

    }
}
