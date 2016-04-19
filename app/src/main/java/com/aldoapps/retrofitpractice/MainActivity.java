package com.aldoapps.retrofitpractice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.retrofitpractice.data.DiscoverResponse;
import com.aldoapps.retrofitpractice.data.Movie;
import com.aldoapps.retrofitpractice.net.TmdbApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultText = (TextView) findViewById(R.id.result_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMovie();
            }
        });
    }

    private void fetchMovie(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);

        Call<DiscoverResponse> call = tmdbApi.discoverMovies("popularity.desc",
                "your api key");
        call.enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                StringBuilder sb = new StringBuilder();
                for(Movie movie : response.body().getMovies()){
                    sb.append("Judul Film: ");
                    sb.append(movie.getTitle());
                    sb.append("\n");
                }
                mResultText.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
