package com.example.taco.hearthstonecard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardViewActivity extends AppCompatActivity {
    private class ImageGetter implements Html.ImageGetter {
        public Drawable getDrawable(String source) {
            int id;
            Drawable d;
            if (source.equals("mana.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.mana, null);
            } else if (source.equals("pv.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.pv, null);
            } else if (source.equals("atq.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.atq, null);
            } else if (source.equals("Alliance.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.alliance, null);
            } else if (source.equals("Horde.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.horde, null);
            } else {
                return null;
            }
            d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            return d;
        }
    };

    private Map<String, String> trad = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        getSupportActionBar().hide();

        //initialisation des traductions
        //types
        trad.put("Spell", "Sort");
        trad.put("Hero", "Héros");
        trad.put("Minion", "Serviteur");
        trad.put("Enchantment", "Enchantement");
        trad.put("Weapon", "Arme");
        trad.put("Hero Power", "Pouvoir héroïque");
        //races
        trad.put("Dragon","Dragon");
        trad.put("Demon","Démon");
        trad.put("Elemental","Élémentaire");
        trad.put("Mech","Méca");
        trad.put("Murloc","Murloc");
        trad.put("Beast","Bête");
        trad.put("Pirate","Pirate");
        trad.put("Totem","Totem");
        //sets
        trad.put("Basic","Basique");
        trad.put("Classic","Classique");
        trad.put("Promo","Promotion");
        trad.put("Hall of Fame","Panthéon");
        trad.put("Naxxramas","Naxxramas");
        trad.put("Goblins vs Gnomes","Gobelins et Gnomes");
        trad.put("Blackrock Mountain","Mont Rochenoire");
        trad.put("The Grand Tournament","Le Grand Tournoi");
        trad.put("The League of Explorers","La Ligue des explorateurs");
        trad.put("Whispers of the Old Gods","Murmures des Dieux très anciens");
        trad.put("One Night in Karazhan","Une nuit à Karazhan");
        trad.put("Mean Streets of Gadgetzan","Main basse sur Gadgetzan");
        trad.put("Journey to Un'Goro","Voyage au centre d’Un’Goro");
        trad.put("Knights of the Frozen Throne","Chevaliers du Trône de glace");
        trad.put("Kobolds & Catacombs","Kobolds et Catacombes");
        trad.put("The Witchwood","Le Bois maudit");
        trad.put("Tavern Brawl","Bras de Fer");
        trad.put("Hero Skins","Modèles de Héros");
        trad.put("Missions","Missions");
        trad.put("Credits","Crédits");

        //récupération des parametres du intent
        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("donnees");

        //déclaration des champs xml
        final ImageView imageViewImageCarte = (ImageView) findViewById(R.id.apercuCarte);
        final TextView textViewTitreCarte = (TextView) findViewById(R.id.nomCarte);
        final TextView textViewType = (TextView) findViewById(R.id.typeCarte);
        final TextView textViewCarac = (TextView) findViewById(R.id.caracCarte);
        final TextView textViewTexte = (TextView) findViewById(R.id.texteCarte);
        final TextView textViewAmbiance = (TextView) findViewById(R.id.ambianceCarte);
        final Button buttonRetour = (Button) findViewById(R.id.retour);

        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            JSONObject donneesCarte = new JSONObject(jsonData);

            String imageCarte = donneesCarte.optString("img", null);
            String nomCarte = donneesCarte.optString("name", null);
            String rarity = donneesCarte.optString("rarity", null);
            String type = donneesCarte.optString("type", null);
            String faction = donneesCarte.optString("faction", null);
            String cardSet = donneesCarte.optString("cardSet", null);
            String cost = donneesCarte.optString("cost", null);
            String attack = donneesCarte.optString("attack", null);
            String health = donneesCarte.optString("health", null);
            String text = donneesCarte.optString("text", null);
            String flavor = donneesCarte.optString("flavor", null);
            String race = donneesCarte.optString("race", null);
            String durability = donneesCarte.optString("durability", null);

            Picasso.with(getApplicationContext()).load(imageCarte).placeholder(R.drawable.carte_chargement).error(R.drawable.carte_inconnue).into(imageViewImageCarte);

            if (nomCarte != null) {
                textViewTitreCarte.setText(nomCarte);
            }

            if (rarity != null) {
                if (rarity.equals("Legendary")) {
                    textViewTitreCarte.setTextColor(Color.parseColor("#fbb101"));
                } else if (rarity.equals("Epic")) {
                    textViewTitreCarte.setTextColor(Color.parseColor("#cd07f2"));
                } else if (rarity.equals("Rare")) {
                    textViewTitreCarte.setTextColor(Color.parseColor("#0776f2"));
                } else if (rarity.equals("Common")) {
                    textViewTitreCarte.setTextColor(Color.parseColor("#b4b4b4"));
                }
            }

            if (type != null) {
                String res = "";
                if ((faction != null)&&(!faction.equals("Neutral"))) {
                    res+="<img src='"+faction+".png'>";
                }
                res+=trad.get(type);
                textViewType.setText(Html.fromHtml(res, 0, new ImageGetter(), null));
            }

            if ((cost != null) || (attack != null) || (health != null) || (race != null) || (durability != null) || (cardSet != null)) {
                String res = "";
                if (cost != null) {
                    res+="<img src='mana.png' height='12'>"+cost+" ";
                }
                if (attack != null) {
                    res+="<img src='atq.png' height='12'>"+attack+" ";
                }
                if (health != null) {
                    res+="<img src='pv.png' height='12'>"+health;
                } else if (durability != null) {
                    res+="<img src='pv.png' height='12'>"+durability;
                }
                if (race != null) {
                    if (!res.equals("")) {
                        res+="<br>";
                    }
                    res+="Race : "+trad.get(race);
                }
                if (cardSet != null) {
                    if (!res.equals("")) {
                        res+="<br>";
                    }
                    res+="Ensemble : "+trad.get(cardSet);
                }
                if (!res.equals("")) {
                    textViewCarac.setText(Html.fromHtml(res, 0, new ImageGetter(), null));
                }
            }

            if (text != null) {
                text = text.replace("\\n", "\n");
                text = text.replace("_", " ");
                textViewTexte.setText(Html.fromHtml(text, 0, null, null));
            }

            if (flavor != null) {
                flavor = flavor.replace("_", " ");
                flavor = flavor.replace("\\n", "\n");
                textViewAmbiance.setText(flavor);
            }
        } catch (JSONException e) {
            Toast erreur = Toast.makeText(getApplicationContext(), "Erreur avec les données de cette carte", Toast.LENGTH_SHORT);
            erreur.show();
            Log.i("----------------------", e.getMessage());
            finish();
        }
    }
}
