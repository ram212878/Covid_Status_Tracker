package com.rambabu.rest.covidtracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.rambabu.rest.covidtracker.R;
import com.rambabu.rest.covidtracker.helper.FormatDateAndTime;
import com.rambabu.rest.covidtracker.helper.timelinehelper.TimeLine;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    public Context context;
    private List<TimeLine> timeLines;
    public TimeLineAdapter(Context context, List<TimeLine> timeLines) {
//        Log.i("Under TimeLine Adapter","DONE");
        this.context = context;
        this.timeLines = timeLines;
//        Log.i("Under TimeLine Adapter",timeLines.get(1).toString());
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.time_line_holder,parent,false);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        final TimeLine data = timeLines.get(position);

        FormatDateAndTime dt = new FormatDateAndTime(data.getLast_update());

        holder.lastUpdated.setText(String.format(" : %s %s", dt.getDate(), dt.getTime()));
        holder.cases.setText(data.getCases());
        holder.deaths.setText(data.getDeaths());
        holder.recovered.setText(data.getRecovered());
        if(position==getItemCount()-1) {
            holder.incresedBy.setText(timeLines.get(position).getCases());
        }
        else{
            Integer difference = Integer.parseInt(data.getCases()) - Integer.parseInt(timeLines.get(position + 1).getCases());
            holder.incresedBy.setText(String.valueOf(difference));
        }
    }

    @Override
    public int getItemCount() {
//        Log.d("SIZE", String.valueOf(timeLines.size()));
        return timeLines.size();

    }

    public static class TimeLineViewHolder extends RecyclerView.ViewHolder {
        TextView cases,deaths,recovered,incresedBy,lastUpdated;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            cases= itemView.findViewById(R.id.timeLineCasesData);
            deaths = itemView.findViewById(R.id.timeLineDeathsData);
            recovered  = itemView.findViewById(R.id.timeLineRecoveredData);
            incresedBy = itemView.findViewById(R.id.timeLineIncreasedByData);
            lastUpdated = itemView.findViewById(R.id.timeLineLastUpdatedData);

        }
    }
}
