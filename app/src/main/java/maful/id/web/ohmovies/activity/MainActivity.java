package maful.id.web.ohmovies.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import maful.id.web.ohmovies.R;
import maful.id.web.ohmovies.adapter.MoviesAdapter;
import maful.id.web.ohmovies.model.Movie;
import maful.id.web.ohmovies.model.MoviesResponse;
import maful.id.web.ohmovies.rest.ApiClient;
import maful.id.web.ohmovies.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "4747721b1aabe643d9299dbcfad73933";
    private MoviesAdapter mMoviesAdapter;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.now_playing);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.required_api_key, Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchMovies();
    }

    private void fetchMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getNowPlayingMovies(API_KEY);

        // progressbar
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                movies =  response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());

                mMoviesAdapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                recyclerView.setAdapter(mMoviesAdapter);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
