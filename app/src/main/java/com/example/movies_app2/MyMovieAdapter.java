package com.example.movies_app2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.ViewHolder>
        implements Filterable {

    MyMovieData[] original;
    List<MyMovieData> filtered;
    Context context;

    public MyMovieAdapter(Context context, MyMovieData[] data) {
        this.context = context;
        this.original = data;
        this.filtered = new ArrayList<>(Arrays.asList(data));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyMovieData movie = filtered.get(position);

        holder.name.setText(movie.getMovieName());
        holder.date.setText(movie.getMovieDate());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getMovieImage();

        Glide.with(context).load(imageUrl).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, MovieDetailsactivity.class);
            i.putExtra("movieId", movie.getMovieId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView1);
            date = itemView.findViewById(R.id.textView3);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence c) {

                List<MyMovieData> list = new ArrayList<>();

                if (c == null || c.length() == 0) {
                    list.addAll(Arrays.asList(original));
                } else {
                    String f = c.toString().toLowerCase();

                    for (MyMovieData m : original) {
                        if (m.getMovieName().toLowerCase().contains(f)) {
                            list.add(m);
                        }
                    }
                }

                FilterResults r = new FilterResults();
                r.values = list;
                return r;
            }

            @Override
            protected void publishResults(CharSequence c, FilterResults r) {
                filtered = (List<MyMovieData>) r.values;
                notifyDataSetChanged();
            }
        };
    }
}