package com.example.teamzcc.calender;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamzcc.R;

//the real class of out view holder
public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    public final CalenderAdapter.onItemListener onItemListener;

    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.onItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
