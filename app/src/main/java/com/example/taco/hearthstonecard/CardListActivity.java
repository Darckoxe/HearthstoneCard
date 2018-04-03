package com.example.taco.hearthstonecard;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardListActivity extends AppCompatActivity {
    private ListView listeCard;
    private Map<String, String> trad = new HashMap<>();
    private List<Map<String,String>> data = new ArrayList<>();
    private List<JSONObject> donneesCartes = new ArrayList<>();
    private class ImageGetter implements Html.ImageGetter {
        public Drawable getDrawable(String source) {
            int id;
            Drawable d;
            if (source.equals("Druid.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.druid, null);
            } else if (source.equals("Hunter.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.hunter, null);
            } else if (source.equals("Mage.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.mage, null);
            } else if (source.equals("Paladin.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.paladin, null);
            } else if (source.equals("Priest.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.priest, null);
            } else if (source.equals("Rogue.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.rogue, null);
            } else if (source.equals("Shaman.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.shaman, null);
            } else if (source.equals("Warlock.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.warlock, null);
            } else if (source.equals("Warrior.png")) {
                d = ResourcesCompat.getDrawable(getResources(), R.drawable.warrior, null);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        getSupportActionBar().hide();

        trad.put("Spell", "Sort");
        trad.put("Hero", "Héros");
        trad.put("Minion", "Serviteur");
        trad.put("Enchantment", "Enchantement");
        trad.put("Weapon", "Arme");
        trad.put("Hero Power", "Pouvoir héroïque");

        Intent mainIntent = getIntent();
        final String typeRequete = mainIntent.getStringExtra("typeRequete");
        String requete = mainIntent.getStringExtra("requete");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://omgvamp-hearthstone-v1.p.mashape.com/cards/";
        url+=typeRequete+"/"+requete+"?locale=frFR";
        JsonArrayRequest requeteInfos = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i =0;i<response.length();i++){
                        JSONObject donnees = response.getJSONObject(i);
                        donneesCartes.add(donnees);
                        Map<String, String> datum = new HashMap<String, String>(2);
                        datum.put("nom", donnees.getString("name"));
                        datum.put("carac", trad.get(donnees.getString("type")));
                        data.add(datum);
                    }
                    listeCard = (ListView) findViewById(R.id.listeCard);
                    SimpleAdapter adapteur = new SimpleAdapter(getApplicationContext(), data, android.R.layout.simple_list_item_2, new String[] {"nom","carac"}, new int[] {android.R.id.text1, android.R.id.text2}){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
                            String rarity = donneesCartes.get(position).optString("rarity", null);
                            String classe = donneesCartes.get(position).optString("playerClass", null);
                            String faction = donneesCartes.get(position).optString("faction", null);
                            tv.setTextColor(Color.WHITE);
                            tv.setTextAppearance(R.style.AudioFileInfoOverlayText);
                            tv2.setTextColor(Color.WHITE);

                            String typeTV2 = "";

                            if ((classe != null)&&(!classe.equals("Neutral"))&&(!classe.equals("Death Knight"))&&(!classe.equals("Dream"))) {
                                typeTV2+="<img src='"+classe+".png'> ";
                            }
                            if ((faction != null)&&(!faction.equals("Neutral"))) {
                                typeTV2+="<img src='"+faction+".png'> ";
                            }
                            typeTV2+=trad.get(donneesCartes.get(position).optString("type", null));
                            tv2.setText(Html.fromHtml(typeTV2, 0, new ImageGetter(), null));
                            if (rarity != null) {
                                if (rarity.equals("Legendary")) {
                                    tv.setTextColor(Color.parseColor("#fbb101"));
                                } else if (rarity.equals("Epic")) {
                                    tv.setTextColor(Color.parseColor("#cd07f2"));
                                } else if (rarity.equals("Rare")) {
                                    tv.setTextColor(Color.parseColor("#0776f2"));
                                } else if (rarity.equals("Common")) {
                                    tv.setTextColor(Color.parseColor("#b4b4b4"));
                                }
                            }
                            return view;
                        }
                    };
                    listeCard.setAdapter(adapteur);
                    listeCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intentVoirCarte = new Intent(getApplicationContext(), CardViewActivity.class);
                            intentVoirCarte.putExtra("donnees", donneesCartes.get(i).toString());
                            startActivity(intentVoirCarte);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(getApplicationContext(), "Les cartes que vous avez demandé n'existent pas", Toast.LENGTH_SHORT);
                errorToast.show();
                finish();
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

    }
}
