package com.example.movies_app2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView title, description;
    ImageView poster;
    Button trailerBtn;

    RequestQueue queue;
    String trailerKey;

    int movieId;

    private static final String API_KEY = "e39426a6e1c8861951b3fbac80590f7d";

    // MAP
    GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailsactivity);

        // UI
        title = findViewById(R.id.textView2);
        description = findViewById(R.id.textView44);
        poster = findViewById(R.id.imageView2);
        trailerBtn = findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);

        // Movie ID
        movieId = getIntent().getIntExtra("movieId", -1);

        if (movieId != -1) {
            loadMovieDetails(movieId);
            loadTrailer(movieId);
        } else {
            Toast.makeText(this, "Movie not found", Toast.LENGTH_SHORT).show();
        }

        // Trailer button
        trailerBtn.setOnClickListener(v -> {
            if (trailerKey != null) {
                Intent i = new Intent(MovieDetailsActivity.this, activityvideoplayer.class);
                i.putExtra("videoUrl", "https://www.youtube.com/embed/" + trailerKey);
                startActivity(i);
            } else {
                Toast.makeText(this, "Trailer not available", Toast.LENGTH_SHORT).show();
            }
        });

        // MAP INIT
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    // 🎬 MOVIE DETAILS
    private void loadMovieDetails(int id) {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        title.setText(response.getString("title"));
                        description.setText(response.getString("overview"));

                        String imageUrl = "https://image.tmdb.org/t/p/w500"
                                + response.getString("poster_path");

                        Glide.with(this).load(imageUrl).into(poster);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    // 🎥 TRAILER
    private void loadTrailer(int id) {

        String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);

                            if (obj.getString("type").equals("Trailer")) {
                                trailerKey = obj.getString("key");
                                break;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Trailer not found", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    // 🗺️ MAP READY
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);

            return;
        }

        mMap.setMyLocationEnabled(true);

        showUserLocation();
    }

    private void showUserLocation() {

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {

            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(current)
                    .title("You are here"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (mMap != null) {
                    onMapReady(mMap);
                }
            }
        }
    }
}