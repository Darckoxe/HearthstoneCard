package com.example.taco.hearthstonecard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        String url = "https://omgvamp-hearthstone-v1.p.mashape.com/info";

        JsonObjectRequest requeteInfos = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*on response*/
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
    }
}
