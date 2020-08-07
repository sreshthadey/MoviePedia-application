package com.example.moviepedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepedia.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerMoviesAdapter extends RecyclerView.Adapter<RecyclerMoviesAdapter.MovieViewHolder> implements Filterable {

    RequestOptions options ;
    private final List<Movie> movies;
    List<Movie> moviesList;
    Context context;

    public RecyclerMoviesAdapter(List<Movie> movies, Context context) {

        Log.d("movie_count", Integer.toString(movies.size()));

        this.movies = movies;
        this.context = context;
        this.moviesList = new ArrayList<>(movies);
        //options = new RequestOptions().centerCrop().placeholder(R.drawable.imageshape).error(R.drawable.imageshape);
    }

    @NonNull
    @Override
    public RecyclerMoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.itemrow, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerMoviesAdapter.MovieViewHolder holder, int position) {
        holder.bind(position);
        Log.e("url",movies.get(position).getImg_url());
        Glide.with(context).load(movies.get(position).getImg_url()).centerCrop().placeholder(R.drawable.imageshape).into(holder.ivimage);
        //Picasso.get().load(movies.get(position).getImg_url()).into(holder.ivimage);

    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Movie> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(moviesList);
            }else {
                for(Movie movie:moviesList){
                    if(movie.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            movies.clear();
            movies.addAll((Collection<? extends Movie>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvname;
        private TextView tvreleasedate;
        private TextView tvrating;
        private TextView tvlang;
        private ImageView ivimage;
        private TextView tvdesc;
        private String name;
        private String desc;

        public MovieViewHolder(@NonNull View itemView) {

            super(itemView);

            tvname = itemView.findViewById(R.id.movie_name);
            tvrating = itemView.findViewById(R.id.rating);
            ivimage = itemView.findViewById(R.id.ivone);
            tvreleasedate = itemView.findViewById(R.id.category);
            tvlang = itemView.findViewById(R.id.studio);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Movie movie = movies.get(position);
            tvname.setText(movie.getTitle());
            tvrating.setText(movie.getRating());
            tvreleasedate.setText("Release Date: " + movie.getRelease_date());
            tvlang.setText("Language: " + movie.getLanguage());
            name = movie.getTitle();
            desc = movie.getDesc();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), tvname.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("mov_name", name);
            intent.putExtra("mov_desc", desc);
            context.startActivity(intent);

        }


    }
}
