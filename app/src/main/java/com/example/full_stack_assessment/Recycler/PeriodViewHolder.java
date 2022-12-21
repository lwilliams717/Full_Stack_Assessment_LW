package com.example.full_stack_assessment.Recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.full_stack_assessment.R;

public class PeriodViewHolder extends RecyclerView.ViewHolder{
    TextView time;
    TextView temp;
    ImageView icon;

    PeriodViewHolder(View view) {
        super(view);
        time = view.findViewById(R.id.text_view_time);
        temp = view.findViewById(R.id.text_view_degree);
        icon = view.findViewById(R.id.image_view_forecast);
    }
}
