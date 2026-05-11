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

    private final MyMovieData[] originalData;
    private List<MyMovieData> filteredData;
    private final Context context;

    public MyMovieAdapter(Context context, MyMovieData[] data) {
        this.context = context;
        this.originalData = data;
        this.filteredData = new ArrayList<>(Arrays.asList(data));
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

        MyMovieData movie = filteredData.get(position);

        holder.name.setText(movie.getMovieName());
        holder.date.setText(movie.getMovieDate());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getMovieImage();

        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);

        // CLICK ITEM → OPEN DETAILS
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, MovieDetailsActivity.class);

            intent.putExtra("movieId", movie.getMovieId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
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

    // 🔍 SEARCH FILTER
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<MyMovieData> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(Arrays.asList(originalData));
                } else {

                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (MyMovieData movie : originalData) {
                        if (movie.getMovieName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(movie);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredData = (List<MyMovieData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}