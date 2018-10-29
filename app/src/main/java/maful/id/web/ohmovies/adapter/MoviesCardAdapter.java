package maful.id.web.ohmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import maful.id.web.ohmovies.R;
import maful.id.web.ohmovies.activity.ShowMovieActivity;
import maful.id.web.ohmovies.model.Movie;
import maful.id.web.ohmovies.support.ItemClickListener;

public class MoviesCardAdapter extends RecyclerView.Adapter<MoviesCardAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitle;
        TextView releaseDate;
        ImageView imageView;

        private ItemClickListener itemClickListener;

        public MovieViewHolder(View v) {
            super(v);
            movieTitle = (TextView) v.findViewById(R.id.title);
            releaseDate = (TextView) v.findViewById(R.id.tv_release_date);
            imageView = (ImageView) v.findViewById(R.id.img_item_photo);

            v.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public MoviesCardAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesCardAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesCardAdapter.MovieViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.releaseDate.setText(movies.get(position).getReleaseDate());

        Picasso.with(context)
                .load(movies.get(position).getPosterPath())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ShowMovieActivity.class);
                intent.putExtra(ShowMovieActivity.EXTRA_ID, movies.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
