package com.example.taco.hearthstonecard;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private List<String> data = new ArrayList<>();
    private List<JSONObject> donneesCartes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        getSupportActionBar().hide();
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
                        data.add(donnees.getString("name"));
                    }
                    listeCard = (ListView) findViewById(R.id.listeCard);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, data);
                    listeCard.setAdapter(adapter);
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
