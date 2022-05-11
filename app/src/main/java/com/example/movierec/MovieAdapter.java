package com.example.movierec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter( Context context, List<Movie> movies){
        this.context = context;
        movieList = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.rating.setText(movie.getRating().toString());
        holder.overview_tv.setText(movie.getOverview());
        holder.Genres.setText(movie.getGenres());
        holder.title_tv.setText(movie.getTitle());
        Glide.with(context).load(movie.getPoster()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , gg.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", movie.getTitle());
                bundle.putString("overview", movie.getOverview());
                bundle.putString("genres", movie.getGenres());
                bundle.putString("poster", movie.getPoster());
                bundle.putDouble("rating" , movie.getRating());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title_tv,overview_tv,Genres,rating;
        ConstraintLayout constraintLayout;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title_tv = itemView.findViewById(R.id.title_tv);
            overview_tv = itemView.findViewById(R.id.overview_tv);
            Genres = itemView.findViewById(R.id.Genres);
            rating = itemView.findViewById(R.id.rating);
            constraintLayout =itemView.findViewById(R.id.main_layout);
        }
    }

}
