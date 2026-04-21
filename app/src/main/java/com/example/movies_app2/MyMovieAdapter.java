package com.example.movies_app2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.ViewHolder>{
    MyMovieData[] myMovieData;
    Context context;

    public MyMovieAdapter(MainActivity activity, MyMovieData[] myMovieData) {
        this.context = activity;
        this.myMovieData = myMovieData;
    }

    @NonNull
    @Override
    public MyMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.movies_list,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMovieAdapter.ViewHolder holder, int position) {
        final MyMovieData myMovieDataList =  myMovieData [position];
        holder.textViewName.setText(myMovieDataList.getMovieName());
        holder.textViewDate.setText(myMovieDataList.getMoviDate());
        holder.movieImage.setImageResource(myMovieDataList.getMovieImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, myMovieDataList.getMovieName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        

    }

    @Override
    public int getItemCount() {
        return myMovieData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.imageView);
            textViewName=itemView.findViewById(R.id.textView1);
            textViewDate=itemView.findViewById(R.id.textView3);

        }
    }
}
