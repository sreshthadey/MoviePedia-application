package com.example.moviepedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviepedia.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "6cf4ba4e5f6cf060c0ad4bbd82bc224a";
    private RecyclerMoviesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SomeTask().execute(API_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class SomeTask extends AsyncTask<String, Void, List<Movie>>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Loading all movies", Toast.LENGTH_LONG).show();

            Log.e("opx_call", "opx is called");
        }

        @Override
        protected List<Movie> doInBackground(String... values) {

            Log.e("dib_call", "dib is called");

            String apiKey = values[0];
            List<Movie> movies = new ArrayList<>();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/popular?api_key=6cf4ba4e5f6cf060c0ad4bbd82bc224a&language=en-US&page=1")
                    .get()
                    .build();

            try {

                Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                String jsonString = responseBody.string();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray titlesArray = jsonObject.getJSONArray("results");
                //JSONArray jsonArray = new JSONArray(jsonString);

                Log.d("jsonstring", jsonString);
                for(int i =0; i<titlesArray.length(); i++) {
                    JSONObject jsonObject1 = titlesArray.getJSONObject(i);
                    Log.d("jsononject", jsonObject1.getString("original_title"));
                    movies.add(new Movie(jsonObject1));
                }


                //JSONArray titlesArray = jsonObject.getJSONArray("titles");

                //for(int i = 0; i < titlesArray.length(); i++)
                //movies.add(new Movie(jsonObject));




                return movies;
            } catch(IOException | JSONException e) {
                Log.i("crash_report", "JSONException was thrown", e);
                Log.i("crash", "exception occured");
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            if(movies == null)
                Log.d("movies_null", "Nigga is null");

            Log.d("opx_movie_count", Integer.toString(movies.size()));

            RecyclerView recyclerView = findViewById(R.id.rv);
            adapter = new RecyclerMoviesAdapter(movies, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.addItemDecoration(
                    new DividerItemDecoration(
                            recyclerView.getContext(),
                            DividerItemDecoration.VERTICAL
                    )
            );
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

        }

    }
}