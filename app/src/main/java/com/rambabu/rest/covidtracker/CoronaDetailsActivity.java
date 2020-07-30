package com.rambabu.rest.covidtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rambabu.rest.covidtracker.fragments.IndividualCountryData;
import com.rambabu.rest.covidtracker.helper.Countries;

public class CoronaDetailsActivity extends AppCompatActivity {
    public Countries c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_corona_details);
        Intent i = getIntent();
//        if(savedInstanceState!=null)
//            c = savedInstanceState.getParcelable("COUNTRIES_OBJECT");
//        else{
            c = i.getParcelableExtra("data");
//        }
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("COUNTRIES_OBJECT",c);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}