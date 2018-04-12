package com.example.asimyamin.quotesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // Volly library is used to fetch data from internet
    /*Steps
    * 1-> add dependency in app level gradle
    * 2-> add Internet Permission in manifest
    * 3-> using request queue
    * 4-> add the url or api key in String from
    * 5-> make Method of loadQueue e-g*/

    RequestQueue queue;

    String url="http://quotesondesign.com/wp-json/posts?filter[posts_per_page]=1&filter[orderby]=rand";


    TextView quotes,auther;
    ProgressBar bar;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quotes=findViewById(R.id.quote);
        auther=findViewById(R.id.auther);
        btnNext=findViewById(R.id.nextQuote);
        bar=findViewById(R.id.progressBar);
        queue= Volley.newRequestQueue(this);

        loadQueue();

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        loadQueue();
    }

    public void loadQueue(){


        bar.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);

        JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {

                    JSONObject object=response.getJSONObject(0);
                    String title= object.getString("title");
                    String content=object.getString("content");
                    int id=object.getInt("ID");

                    //for remove tag e-g //,>,< etc

                    content=android.text.Html.fromHtml(content).toString();

                    quotes.setText(content);
                    auther.setText(title);

                    bar.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Some thing Wrong!!!",Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);

            }
        });

        queue.add(req);
        queue.start();
    }


}
