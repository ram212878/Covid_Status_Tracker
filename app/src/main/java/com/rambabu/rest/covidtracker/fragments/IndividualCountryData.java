package com.rambabu.rest.covidtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rambabu.rest.covidtracker.R;
import com.rambabu.rest.covidtracker.adapter.TimeLineAdapter;
import com.rambabu.rest.covidtracker.helper.Countries;
import com.rambabu.rest.covidtracker.helper.timelinehelper.TimeLine;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndividualCountryData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualCountryData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private String code;
    public List<TimeLine> timeLines;
    TimeLineAdapter  timeLineAdapter;
    RequestQueue queue;
    public static final String QUEUE_TAG = "QUEUE";
    View view;
    public Context context;

    public IndividualCountryData(Context context)
    {
        // Required empty public constructor
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndividualCountryData.
     */
    // TODO: Rename and change types and number of parameters
    public IndividualCountryData newInstance(String param1, String param2) {
        IndividualCountryData fragment = new IndividualCountryData(context);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_individual_country_data, container, false);
        Countries c;
        Bundle bundle = getArguments();

        // finding the Recycler view
        recyclerView = view.findViewById(R.id.timeLineRecyclerView);

        //Attaching the Layout Manager To the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (bundle != null) {
            c = bundle.getParcelable("data");
            assert c != null;
            code = c.getCountryCode();
            setData(c,view);
        }
        Button btn = view.findViewById(R.id.timeLineButton);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        timeLines = new ArrayList<>();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(code);
                Toast.makeText(getContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
                showTimeLine(code);
            }
        });
        c=null;
        return view;
    }

    private void showTimeLine(String countryCode) {
        System.out.println(countryCode);
        queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        String url = String.format("https://covid19-api.org/api/timeline/%s", countryCode);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response",response.toString());
                Gson json = new Gson();
                timeLines.clear();
                timeLines = json.fromJson(response.toString(), new TypeToken<List<TimeLine>>() {}.getType());

                timeLineAdapter = new TimeLineAdapter(context,timeLines);
                recyclerView.setAdapter(timeLineAdapter);
                timeLineAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error",error.toString());
            }
        });
        request.setTag(QUEUE_TAG);
        queue.add(request);
    }

//This method is used to get the views on then fragment and set the value in the respective views

        private void setData(Countries c,View view) {
//        Log.i("setData",c.toString());
        TextView countryName,totalCases,totalDeaths,totalRecovered,newCases,newRecovered,newDeaths,lastUpdated;
        countryName = view.findViewById(R.id.fragmentCountryName);
        totalCases = view.findViewById(R.id.fragmentTotalCases);
        totalRecovered = view.findViewById(R.id.fragmentTotalRecovered);
        totalDeaths = view.findViewById(R.id.fragmentTotalDeaths);
        newCases =view.findViewById(R.id.fragmentNewCases);
        newRecovered =view.findViewById(R.id.fragmentNewRecovered);
        newDeaths =view.findViewById(R.id.fragmentNewDeaths);
        lastUpdated = view.findViewById(R.id.fragmentLastUpdated);

        countryName.setText(c.getCountry());
        totalCases.setText(c.getTotalConfirmed());
        totalRecovered.setText(c.getTotalRecovered());
        totalDeaths.setText(c.getTotalDeaths());
        newCases.setText(c.getNewConfirmed());
        newRecovered.setText(c.getNewRecovered());
        newDeaths.setText(c.getNewDeaths());
        lastUpdated.setText(c.getDate());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (queue != null ) {
            queue.cancelAll(QUEUE_TAG);
        }
        Log.i("Fragment","Destroyed");
    }

    @Override
    public void onStop() {
        super.onStop();

//        if(queue!= null) {
//            timeLines.clear();
            Log.i("Fragment", "Stopped");
//            queue.cancelAll(QUEUE_TAG);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment","resumed");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("Fragment","Attached");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment","Detached");
    }
}