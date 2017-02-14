package es.cice.moviedbfinalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.cice.moviedbfinalproject.adapters.FilmsAdapter;
import es.cice.moviedbfinalproject.model.Film;
import es.cice.moviedbfinalproject.model.setupDB;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private Context ctx;
    private ActionBar aBar;
    private FilmsAdapter adapter;
    private EditText searchET;
    private String BaseUrlImage;
    private static final String api_key = "857ef84cbaec1f89f981c0ac344c4630";
    private static final String urlConfig="https://api.themoviedb.org/3/configuration?api_key=" + api_key;
   // private String SizeImage=

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;


        //para obtener la baseurl de las imágenes y el size https://api.themoviedb.org/3/configuration?api_key=<<api_key>>
//url base images--->http://d3gtl9l2a4fn1j.cloudfront.net/t/p/
        //https://api.themoviedb.org/3/genre/movie/list?api_key=<<api_key>>
        //api-key:857ef84cbaec1f89f981c0ac344c4630

     /*   Spinner spinner = (Spinner) findViewById(R.id.spGenero);
// Create an ArrayAdapter using the string array and a default spinner layout
         ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
           R.array.generos, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
         adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter2);*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.includedToolbar);
        setSupportActionBar(toolbar);
        aBar = getSupportActionBar();
        aBar.setTitle("PELÍCULAS");
        getBaseUrlImage();

        getMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu()...");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void getMovies() {
        TheMovieDBAsyncTask at = new TheMovieDBAsyncTask();
        at.execute("https://api.themoviedb.org/3/movie/popular?api_key=857ef84cbaec1f89f981c0ac344c4630");

    }
    public void getBaseUrlImage(){
        TheMovieDBConfigAsyncTask at = new TheMovieDBConfigAsyncTask();
        Log.d(TAG, urlConfig);
        at.execute(urlConfig);

    }
    public class TheMovieDBConfigAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            BaseUrlImage="";
            BufferedReader in = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer data = new StringBuffer();
                //Insertar los datos obtenidos con in en el StringBuffer
                String line = null;
                while ((line = in.readLine()) != null) {
                    data.append(line);

                }
                Gson gson=new Gson();
                setupDB setupdb =gson.fromJson(data, setupDB.class);

                BaseUrlImage= setupdb.getImages().getBaseUrl();


                /*JSONObject jsonObj = new JSONObject(data.toString());
                JSONArray results = jsonObj.getJSONArray("images");
                BaseUrlImage=results.getJSONObject(0).getString("base_url");
                //BaseUrlImage=results.getString("base_url");
                Log.d(TAG, "URL BASE ...." + BaseUrlImage);
                return BaseUrlImage;*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public class TheMovieDBAsyncTask extends AsyncTask<String, Void, List<Film>> {
        //parámetro de entrada la URL
        @Override
        protected List<Film> doInBackground(String... urls) {

            //devuelve la lista de títulos
            BufferedReader in = null;

            //List<String> movieList = new ArrayList<>();
            List<Film> movieList = new ArrayList<>();

            //Retrofit evita tener que gestionar la conexion http
            //Retrofit no está disponible en android, hay que añadirla
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer data = new StringBuffer();
                //Insertar los datos obtenidos con in en el StringBuffer
                String line = null;
                while ((line = in.readLine()) != null) {
                    data.append(line);

                }
                JSONObject jsonObj = new JSONObject(data.toString());
                JSONArray results = jsonObj.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject jsonMovie = results.getJSONObject(i);
                    String title = jsonMovie.getString("original_title");
                    Log.d(TAG, title);
                    movieList.add(new Film(title, "1989", 5, ""));
                }
                return movieList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Film> movieList) {
            //Rellena el listView

            //FilmsAdapter adapter=new FilmsAdapter(ctx,R.layout.film_row,movieList);
            // ListView listFilmsLV= (ListView) findViewById(R.id.listFilmsLV);
            //listFilmsLV.setAdapter(adapter);

            /*ListView filmsLV= (ListView) findViewById(R.id.filmsLV);
            adapter=new FilmsAdapter(ctx,R.layout.film_row,movieList);
            filmsLV.setAdapter(adapter);*/


            RecyclerView filmRV = (RecyclerView) findViewById(R.id.filmRV);
            adapter = new FilmsAdapter(ctx, movieList);
            filmRV.setAdapter(adapter);
            filmRV.setLayoutManager(new LinearLayoutManager(ctx));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.generoIT:
                Log.d(TAG, "Género item...");
                aBar.setDisplayShowCustomEnabled(true);
                aBar.setCustomView(R.layout.genero_layout);
                aBar.setDisplayShowTitleEnabled(false);
                return true;
            case R.id.buscarIT:
                Log.d(TAG, "Search item...");
                aBar.setDisplayShowCustomEnabled(true);
                aBar.setCustomView(R.layout.search_layout);
                aBar.setDisplayShowTitleEnabled(false);

                searchET = (EditText) aBar.getCustomView().findViewById(R.id.searchET);
                searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                        if (action == EditorInfo.IME_ACTION_SEARCH) {
                            CharSequence searchText = searchET.getText();
                            Log.d(TAG, "search: " + searchText);
                            InputMethodManager imn =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imn.hideSoftInputFromWindow(searchET.getWindowToken(), 0);
                            aBar.setDisplayShowCustomEnabled(false);
                            aBar.setDisplayShowTitleEnabled(true);
                            //empezar la busqueda
                            adapter.getFilter().filter(searchText);
                            return true;
                        }
                        return false;
                    }
                });

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT);
                searchET.requestFocus();
                break;
        }


                return super.onOptionsItemSelected(item);
        }



    }




