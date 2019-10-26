package com.example.flixter.adapters;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;


import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    public static final int POSTER = 0;
    public static final int BACKDROP = 1;





    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    // inflates layout from XML
    @NonNull
    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        Movie movie = movies.get(viewType);
//        View movieView = null;
//
//        if(!movie.isPopular()){
//             movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
//        } else if(!movie.isPopular()){
//            movieView = LayoutInflater.from(context).inflate(R.layout.popular_item_movie, parent, false);
//        }
//        return new ViewHolder((movieView));
//    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Movie movie = movies.get(viewType);
        View movieView = null;

        if(!movie.isPopular()){
            movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        }

        else if(movie.isPopular()){
            movieView = LayoutInflater.from(context).inflate(R.layout.popular_item_movie, parent, false);
        }
        return new ViewHolder(movieView);
    }



    @Override
    public int getItemCount() {
        return movies.size() ;
    }



    @Override
    public int getItemViewType(int position) {
        Movie movie = movies.get(position);

        if (movie.isPopular()) {
            return POSTER;
        } else {
            return BACKDROP;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get movie position
        // Bind movie data into viewHolder
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;
        TextView tvPopularTitle;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
            tvPopularTitle = itemView.findViewById(R.id.tvPopularTitle);


        }




        public void bind(final Movie movie) {
            String imgURL = movie.getBackdropPath();
            ImageView imgView = null;

            if(movie.isPopular()){
                tvPopularTitle.setText(movie.getTitle());
                imgURL = movie.getBackdropPath();
                imgView = ivBackdrop;
            }

            else if(!movie.isPopular()){
                tvTitle.setText(movie.getTitle());
                tvOverview.setText(movie.getOverview());
                imgURL = movie.getPosterPath();
                imgView = ivPoster;
            }
            Log.i("MovieAdapter","heu" + imgURL);
            Glide.with(context).load(imgURL).into(imgView);




            // FOR YOUTUBE STUFF
             // register click listener on whole container
            container .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // navigate to new activity
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);

                    // using parceler to pass data into detail activity
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }




}
