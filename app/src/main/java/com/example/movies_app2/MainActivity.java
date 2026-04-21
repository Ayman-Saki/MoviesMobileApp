package com.example.movies_app2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rv=findViewById(R.id.recycleView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyMovieData[] myMovieData = new MyMovieData[] {
                new MyMovieData("Once Upon A Time In Hollywood","2018 film",R.drawable.movie5),
                new MyMovieData("Project Hail Mary","2025 film",R.drawable.movie4),
                new MyMovieData("Big Hero Six","2018 film",R.drawable.movie12),
                new MyMovieData("Arrival","2013 film",R.drawable.movie3),
                new MyMovieData("Babylon","2023 film",R.drawable.movie8),
                new MyMovieData("La La Land","2017 film",R.drawable.movie7),
                new MyMovieData("The Batman","2018 film",R.drawable.movie9),
                new MyMovieData("Your Name","2018 film",R.drawable.movie11),

        };
        MyMovieAdapter MA =new MyMovieAdapter(MainActivity.this, myMovieData);
        rv.setAdapter(MA);
    }
}