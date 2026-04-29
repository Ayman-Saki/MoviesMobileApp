package com.example.movies_app2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "your api key";
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                fullUrl, null,
                response -> {
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
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        queue.add(request);

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
}