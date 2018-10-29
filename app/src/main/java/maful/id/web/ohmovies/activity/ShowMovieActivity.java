package maful.id.web.ohmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import maful.id.web.ohmovies.R;
import maful.id.web.ohmovies.model.Movie;
import maful.id.web.ohmovies.rest.ApiClient;
import maful.id.web.ohmovies.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowMovieActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "4747721b1aabe643d9299dbcfad73933";
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private ImageView ivBackdropImage;
    private ProgressBar progressBar;

    public static String EXTRA_ID = "extra_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        getSupportActionBar().setTitle(R.string.movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        ivBackdropImage = (ImageView) findViewById(R.id.iv_backdrop_image);

        getMovie();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getMovie() {
        int movie_id = getIntent().getIntExtra(EXTRA_ID, 0);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(movie_id, API_KEY);

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                progressBar.setVisibility(View.INVISIBLE);

                Log.d(TAG, "JSON: " + response.body().getTitle());

                tvTitle.setText(response.body().getTitle());
                tvReleaseDate.setText(response.body().getReleaseDate());
                tvOverview.setText(response.body().getOverview());

                Picasso.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/original" + response.body().getBackdropPath())
                        .placeholder(R.color.colorAccent)
                        .into(ivBackdropImage);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
