package com.rambabu.rest.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.rambabu.rest.covidtracker.fragments.IndividualCountryData;
import com.rambabu.rest.covidtracker.helper.Countries;

public class CoronaDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_corona_details);
        Intent i = getIntent();
        Countries c = i.getParcelableExtra("data");
        if (c != null) {
            IndividualCountryData fragment = new IndividualCountryData(this);
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", c);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.secondActvityFragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Fragment","Activity Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Fragment","Activity Destroyed");
    }
}