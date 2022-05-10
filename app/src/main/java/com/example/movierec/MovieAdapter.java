package com.example.movierec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title_tv,overview_tv,Genres,rating;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title_tv = itemView.findViewById(R.id.title_tv);
            overview_tv = itemView.findViewById(R.id.overview_tv);
            Genres = itemView.findViewById(R.id.Genres);
            rating = itemView.findViewById(R.id.rating);
        }
    }

}
