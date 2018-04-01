package com.example.taco.hearthstonecard;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardViewActivity extends AppCompatActivity {

    Map<String, String> trad = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        getSupportActionBar().hide();

        //initialisation des traductions
        trad.put("Spell", "Sort");
        trad.put("Hero", "Héros");
        trad.put("Minion", "Serviteur");
        trad.put("Enchantment", "Enchantement");
        trad.put("Weapon", "Arme");
        trad.put("Hero Power", "Pouvoir héroïque");

        //récupération des parametres du intent
        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("donnees");

        //déclaration des champs xml
        final ImageView imageViewImageCarte = (ImageView) findViewById(R.id.apercuCarte);
        final TextView textViewTitreCarte = (TextView) findViewById(R.id.nomCarte);

        try {
            JSONObject donneesCarte = new JSONObject(jsonData);

            String imageCarte = donneesCarte.optString("img", null);
            String nomCarte = donneesCarte.optString("name", null);
            String rarity = donneesCarte.optString("rarity", null);

            Picasso.with(getApplicationContext()).load(imageCarte).placeholder(R.drawable.carte_chargement).error(R.drawable.carte_inconnue).into(imageViewImageCarte);

            if (nomCarte != null) {
                textViewTitreCarte.setText(nomCarte);
            }
            textViewTitreCarte.setTextColor(Color.parseColor("#fbb101"));
            if (rarity != null) {
                if (rarity == "Legendary") {
                    textViewTitreCarte.setTextColor(Color.parseColor("#fbb101"));
                } else if (rarity == "Epic") {
                    textViewTitreCarte.setTextColor(Color.parseColor("#cd07f2"));
                } else if (rarity == "Rare") {
                    textViewTitreCarte.setTextColor(Color.parseColor("#0776f2"));
                } else if (rarity == "Common") {
                    textViewTitreCarte.setTextColor(Color.parseColor("#909090"));
                }
            }
        } catch (JSONException e) {
            Toast erreur = Toast.makeText(getApplicationContext(), "Erreur avec les données de cette carte", Toast.LENGTH_SHORT);
            erreur.show();
            Log.i("----------------------", e.getMessage());
            finish();
        }
    }
}
