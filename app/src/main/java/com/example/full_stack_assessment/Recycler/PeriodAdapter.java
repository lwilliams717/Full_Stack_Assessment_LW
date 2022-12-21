package com.example.full_stack_assessment.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.full_stack_assessment.Data.Forecast.Period;
import com.example.full_stack_assessment.ForecastActivity;
import com.example.full_stack_assessment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PeriodAdapter extends RecyclerView.Adapter<PeriodViewHolder>{
    private final List<Period> periods;
    private final ForecastActivity forecastActivity;
    Picasso picasso;

    public PeriodAdapter(List<Period> list, ForecastActivity fa) {
        this.periods = list;
        forecastActivity = fa;
    }

    @NonNull
    @Override
    public PeriodViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_card, parent, false);
        return new PeriodViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull PeriodViewHolder holder, int position) {

        holder.time.setText( periods.get(position).getName());
        holder.temp.setText( String.valueOf(periods.get(position).getTemperature()));

        int resId = forecastActivity.forecastToIcon(periods.get(position).getShortForecast());
        if(resId > 0){
            holder.icon.setImageResource(resId);
        }
        else {
            //in the JSON, some items do not have a normal shortForecast instead a desc
            //using Picasso to retrieve the image url for some of the periods
            picasso = Picasso.get();
            picasso.load(periods.get(position).getIcon())
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.icon);
        }

    }

    @Override
    public int getItemCount() {
        return periods.size();
    }

}
