package com.rambabu.rest.covidtracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rambabu.rest.covidtracker.CoronaDetailsActivity;
import com.rambabu.rest.covidtracker.R;
import com.rambabu.rest.covidtracker.helper.Countries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> implements Filterable{
    private Context context;
    private List<Countries> countriesList;
    public List<Countries> searchCountriesList;
    public CountryAdapter(Context context, List<Countries> countriesList) {
        this.context = context;
        this.countriesList = countriesList;
        searchCountriesList = new ArrayList<>();

//        Log.i("Hello Constructor 2",countriesList.get(1).toString());
//        Log.i("Hello constructor",searchCountriesList.get(1).toString());
    }
    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.country_name_single_row_layout,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {
        final Countries country = countriesList.get(position);
        holder.countryName.setText(country.getCountry());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,country.getCountry()+" is clicked",Toast.LENGTH_SHORT).show();
//                Log.i("Helllo","executed");
                Intent i= new Intent(context,CoronaDetailsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("data",country);
                context.startActivity(i);


            }
        });

        holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,country.getCountry(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        if(position==searchCountriesList.size()-1){
            Toast.makeText(context,"Bottom Reached",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        View mview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.textViewCountryName);
            mview = itemView;
        }
    }

// This method will be called while searching for the country
    @Override
    public Filter getFilter() {
        return countriesData;
    }

    private Filter countriesData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Countries> filteredCountries = new ArrayList<>();
//            Log.e("Hello1",searchCountriesList.get(1).toString());
            if (constraint == null || constraint.length() == 0) {
//                Log.e("under Filter",constraint.toString());
                filteredCountries.addAll(searchCountriesList);
            } else {
                String searchQuery = constraint.toString().toLowerCase().trim();
//                Log.e("not zero",searchQuery);
//                Log.e("Hello",searchCountriesList.get(1).getCountry().toString());

                for (Countries c : searchCountriesList) {
//                    Log.e("print1",c.toString());
                    if (c.getCountry().toLowerCase().contains(searchQuery)) {
//                        Log.e("print2",c.toString());
                        filteredCountries.add(c);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredCountries;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countriesList.clear();
            countriesList.addAll((Collection<? extends Countries>) results.values);
            notifyDataSetChanged();
        }
    };
}
