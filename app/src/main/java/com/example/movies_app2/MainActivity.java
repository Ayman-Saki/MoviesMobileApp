package com.example.movies_app2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String API_KEY = "e39426a6e1c8861951b3fbac80590f7d";
    private static final String URL =
            "https://api.themoviedb.org/3/movie/popular";

    RecyclerView recyclerView;
    MyMovieAdapter adapter;
    EditText search;

    MyMovieData[] movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        search = findViewById(R.id.editTextSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue queue = Volley.newRequestQueue(this);

        String fullUrl = URL + "?api_key=" + API_KEY;

        queue.add(createMovieRequest(fullUrl));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}

            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                if (adapter != null) adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private JsonObjectRequest createMovieRequest(String url) {
        return new JsonObjectRequest(Request.Method.GET, url, null,
                this::handleResponse,
                this::handleError);
    }

    private void handleResponse(JSONObject response) {
        try {
            JSONArray results = response.getJSONArray("results");

            movies = new MyMovieData[results.length()];

            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);

                movies[i] = new MyMovieData(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("release_date"),
                        obj.getString("poster_path")
                );
            }

            adapter = new MyMovieAdapter(MainActivity.this, movies);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Log.e(TAG, "Error parsing movie data", e);
        }
    }

    private void handleError(VolleyError error) {
        Log.e(TAG, "Volley request failed", error);
    }
}