package com.rambabu.rest.covidtracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rambabu.rest.covidtracker.adapter.CountryAdapter;
import com.rambabu.rest.covidtracker.fragments.AboutFragment;
import com.rambabu.rest.covidtracker.helper.CoronaDetail;
import com.rambabu.rest.covidtracker.helper.Countries;
import com.rambabu.rest.covidtracker.helper.FormatNumber;
import com.rambabu.rest.covidtracker.helper.Global;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    public TextView globalTotalCase,globalTotalDeaths,globalTotalRecovered,globalNewDeaths,loadingLabel;
    public List<Countries> list;
    public RecyclerView recyclerView;
    private CoronaDetail detail= new CoronaDetail();
    private CountryAdapter countryAdapter;
    private Handler handler = new Handler();
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"on create");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();
        loadData();

//        Log.i(TAG,"After loaData call");
        countryAdapter = new CountryAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(countryAdapter);
    }

    private void init() {
//Instantiating the text view of global data to update the records
        progressBar = findViewById(R.id.loadingProgressBarMain);
        loadingLabel = findViewById(R.id.loadingLabel);

        globalTotalCase = findViewById(R.id.globalTotalCases);
        globalTotalDeaths = findViewById(R.id.globalTotalDeaths);
        globalTotalRecovered = findViewById(R.id.globalTotalRecovered);
        globalNewDeaths = findViewById(R.id.globalNewDeaths);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar.setVisibility(View.VISIBLE);
        loadingLabel.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    //    This method wil be used to load the data from the internet
    public void loadData() {
        if(list.size()<=0){
            progressBar.setVisibility(View.VISIBLE);
            loadingLabel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(list.size()!=0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            loadingLabel.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.covid19api.com/summary";
        Log.i(TAG,"RequestQueue Initiated");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                Gson json = new Gson();

                detail = json.fromJson(response.toString(),CoronaDetail.class);
//                Log.i(TAG, detail.toString());
                list.addAll(Arrays.asList(detail.getCountries()));
                countryAdapter.searchCountriesList.clear();
                countryAdapter.searchCountriesList.addAll(list);
//                Log.i("Item", String.valueOf(countryAdapter.getItemCount()));
                setGlobalData(detail);
                countryAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response",error.toString());

            }
        });
        queue.add(request);

    }

    private void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
        loadingLabel.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    // This method will be used to set the Global Covid data
    private void setGlobalData(CoronaDetail detail) {
        Global globalData;
        globalData = detail.getGlobal();
        FormatNumber fn = new FormatNumber();
        globalTotalCase.setText(fn.getFormatedNumber(globalData.getTotalConfirmed()));
        globalTotalRecovered.setText(fn.getFormatedNumber(globalData.getTotalRecovered()));
        globalTotalDeaths.setText(fn.getFormatedNumber(globalData.getTotalDeaths()));
        globalNewDeaths.setText(fn.getFormatedNumber(globalData.getNewDeaths()));
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        final String TAGMENU = "onCreateOptionsMenu Method";
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i(TAGMENU,"Under ON QUERY TEXT CHANGE METHOD");
                countryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
// T
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.refreshIcon:
                loadData();
//                setGlobalData(detail);
                return true;

            case R.id.aboutIcon:
                AboutFragment aboutFragment = new AboutFragment();
                FrameLayout frameLayout = findViewById(R.id.about_fragment_container);
                frameLayout.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
                frameLayout.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.about_fragment_container,aboutFragment)
                        .commit();
                return true;

            case R.id.exitIcon:
                finishAffinity();
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    public void closeFragment(View view) {
        AboutFragment aboutFragment =(AboutFragment) getSupportFragmentManager()

                .findFragmentById(R.id.about_fragment_container);
//        View view1 = findViewById(R.id.mainActivitylinearlayout);
//        view1.setAlpha(1);
        if(aboutFragment!=null)
            getSupportFragmentManager()
            .beginTransaction()
            .remove(aboutFragment)
            .commit();
        FrameLayout frameLayout = findViewById(R.id.about_fragment_container);
        frameLayout.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
        frameLayout.getLayoutParams().width = FrameLayout.LayoutParams.WRAP_CONTENT;
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"on paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"on Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"on Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"on Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"on Destroy");
    }
}
