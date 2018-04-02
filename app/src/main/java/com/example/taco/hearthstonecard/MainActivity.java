package com.example.taco.hearthstonecard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, String> trad = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //initialisation des traductions
        //types
        trad.put("Sort", "Spell");
        trad.put("Héros", "Hero");
        trad.put("Serviteur", "Minion");
        trad.put("Enchantement", "Enchantment");
        trad.put("Arme", "Weapon");
        trad.put("Pouvoir héroïque", "Hero Power");
        //races
        trad.put("Dragon","Dragon");
        trad.put("Démon","Demon");
        trad.put("Élémentaire","Elemental");
        trad.put("Méca","Mech");
        trad.put("Murloc","Murloc");
        trad.put("Bête","Beast");
        trad.put("Pirate","Pirate");
        trad.put("Totem","Totem");
        //sets
        trad.put("Basique","Basic");
        trad.put("Classique","Classic");
        trad.put("Promo","Promo");
        trad.put("Panthéon","Hall of Fame");
        trad.put("Naxxramas","Naxxramas");
        trad.put("Gobelins et Gnomes","Goblins vs Gnomes");
        trad.put("Mont Rochenoire","Blackrock Mountain");
        trad.put("Le Grand Tournoi","The Grand Tournament");
        trad.put("La Ligue des explorateurs","The League of Explorers");
        trad.put("Murmures des Dieux très anciens","Whispers of the Old Gods");
        trad.put("Une nuit à Karazhan","One Night in Karazhan");
        trad.put("Main basse sur Gadgetzan","Mean Streets of Gadgetzan");
        trad.put("Voyage au centre d’Un’Goro","Journey to Un'Goro");
        trad.put("Chevaliers du Trône de glace","Knights of the Frozen Throne");
        trad.put("Kobolds et Catacombes","Kobolds & Catacombs");
        trad.put("Le Bois maudit","The Witchwood");
        trad.put("Bras de Fer","Tavern Brawl");
        trad.put("Hero Skins","Hero Skins");
        trad.put("Missions","Missions");
        trad.put("Credits","Credits");
        trad.put("System","System");
        trad.put("Debug","Debug");
        //factions
        trad.put("Alliance","Alliance");
        trad.put("Horde","Horde");
        trad.put("Neutral","Neutral");
        //qualités
        trad.put("Gratuit", "Free");
        trad.put("Commune", "Common");
        trad.put("Rare", "Rare");
        trad.put("Épique", "Epic");
        trad.put("Légendaire", "Legendary");
        //classes
        trad.put("Chevalier de la mort","Death Knight");
        trad.put("Druide","Druid");
        trad.put("Chasseur","Hunter");
        trad.put("Mage","Mage");
        trad.put("Paladin","Paladin");
        trad.put("Prêtre","Priest");
        trad.put("Voleur","Rogue");
        trad.put("Chaman","Shaman");
        trad.put("Démoniste","Warlock");
        trad.put("Guerrier","Warrior");
        trad.put("Dream","Dream");
        trad.put("Neutre","Neutral");


        final Spinner spinnerClasse = (Spinner) findViewById(R.id.spinnerClasse);
        final Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        final Spinner spinnerFaction = (Spinner) findViewById(R.id.spinnerFaction);
        final Spinner spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
        final Spinner spinnerQualite = (Spinner) findViewById(R.id.spinnerQualite);
        final Spinner spinnerSet = (Spinner) findViewById(R.id.spinnerSet);
        String url = "https://omgvamp-hearthstone-v1.p.mashape.com/info?locale=frFR";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest requeteInfos = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray classe = (JSONArray) response.get("classes");
                    JSONArray type = (JSONArray) response.get("types");
                    JSONArray faction = (JSONArray) response.get("factions");
                    JSONArray race = (JSONArray) response.get("races");
                    JSONArray qualite = (JSONArray) response.get("qualities");
                    JSONArray set = (JSONArray) response.get("sets");

                    ArrayList<String> listeClasse = new ArrayList<>();
                    ArrayList<String> listeType = new ArrayList<>();
                    ArrayList<String> listeFaction = new ArrayList<>();
                    ArrayList<String> listeRace = new ArrayList<>();
                    ArrayList<String> listeQualite = new ArrayList<>();
                    ArrayList<String> listeSet = new ArrayList<>();

                    listeClasse.add("Recherche par classe");
                    listeType.add("Recherche par type");
                    listeFaction.add("Recherche par faction");
                    listeRace.add("Recherche par race");
                    listeQualite.add("Recherche par qualité");
                    listeSet.add("Recherche par extension");


                    if(classe != null){
                        for (int i=0;i<classe.length();i++){
                            listeClasse.add((String) classe.get(i));
                        }
                    }
                    if(type != null){
                        for (int i=0;i<type.length();i++){
                            listeType.add((String) type.get(i));
                        }
                    }
                    if(faction != null){
                        for (int i=0;i<faction.length();i++){
                            listeFaction.add((String) faction.get(i));
                        }
                    }
                    if(race != null){
                        for (int i=0;i<race.length();i++){
                            listeRace.add((String) race.get(i));
                        }
                    }
                    if(qualite != null){
                        for (int i=0;i<qualite.length();i++){
                            listeQualite.add((String) qualite.get(i));
                        }
                    }
                    if(set != null){
                        for (int i=0;i<set.length();i++){
                            listeSet.add((String) set.get(i));
                        }
                    }
                    ArrayAdapter<String> adaptClasse = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeClasse);
                    ArrayAdapter<String> adaptType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeType);
                    ArrayAdapter<String> adaptFaction = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeFaction);
                    ArrayAdapter<String> adaptRace = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeRace);
                    ArrayAdapter<String> adaptQualite = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeQualite);
                    ArrayAdapter<String> adaptSet = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listeSet);


                    spinnerClasse.setAdapter(adaptClasse);
                    spinnerType.setAdapter(adaptType);
                    spinnerFaction.setAdapter(adaptFaction);
                    spinnerRace.setAdapter(adaptRace);
                    spinnerQualite.setAdapter(adaptQualite);
                    spinnerSet.setAdapter(adaptSet);

                    spinnerClasse.setVisibility(View.VISIBLE);
                    spinnerType.setVisibility(View.VISIBLE);
                    spinnerFaction.setVisibility(View.VISIBLE);
                    spinnerRace.setVisibility(View.VISIBLE);
                    spinnerQualite.setVisibility(View.VISIBLE);
                    spinnerSet.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(getApplicationContext(), "Erreur de chargement des données.", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "EENZNo7ldHmshU7EO3GUSavTQzlkp1emEzLjsnWp41nDAVaw1i");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        requestQueue.add(requeteInfos);

        spinnerClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerClasse.getSelectedItem().toString();
                if(!item.equals("Recherche par classe")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "classes");
                    intent.putExtra("requete", trad.get(spinnerClasse.getSelectedItem().toString()));
                    spinnerClasse.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerType.getSelectedItem().toString();
                if(!item.equals("Recherche par type")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "types");
                    intent.putExtra("requete", trad.get(spinnerType.getSelectedItem().toString()));
                    spinnerType.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerFaction.getSelectedItem().toString();
                if(!item.equals("Recherche par faction")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "factions");
                    intent.putExtra("requete", trad.get(spinnerFaction.getSelectedItem().toString()));
                    spinnerFaction.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerRace.getSelectedItem().toString();
                if(!item.equals("Recherche par race")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "races");
                    intent.putExtra("requete", trad.get(spinnerRace.getSelectedItem().toString()));
                    spinnerRace.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerQualite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerQualite.getSelectedItem().toString();
                if(!item.equals("Recherche par qualité")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("requete", trad.get(spinnerQualite.getSelectedItem().toString()));
                    intent.putExtra("typeRequete", "qualities");
                    spinnerQualite.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerSet.getSelectedItem().toString();
                if(!item.equals("Recherche par extension")){
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "sets");
                    intent.putExtra("requete", trad.get(spinnerSet.getSelectedItem().toString()));
                    spinnerSet.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button rechercheNom = (Button) findViewById(R.id.buttonRechercheNom);
        final EditText texteRechercheNom = (EditText) findViewById(R.id.editTextRechercheNom);

        rechercheNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!texteRechercheNom.getText().toString().matches("")) {
                    Intent intent = new Intent(getApplicationContext(), CardListActivity.class);
                    intent.putExtra("typeRequete", "search");
                    intent.putExtra("requete", texteRechercheNom.getText().toString());
                    texteRechercheNom.setText("");
                    startActivity(intent);
                }
            }
        });
    }
}
