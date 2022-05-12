package com.example.movierec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();
        
        fetchMovies();


    }

    private void fetchMovies()  {

       String url ="https://api.jsonserve.com/VGN71w";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)   {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String overview = jsonObject.getString("overview");
                        String genres = jsonObject.getString("genres");
                        String poster = jsonObject.getString("poster");
                        Double rating = jsonObject.getDouble("rating");

                        Movie movie = new Movie(title, overview, genres, poster, rating);
                        movieList.add(movie);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieList);
                    recyclerView.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
        requestQueue.add(jsonArrayRequest);
    }
}