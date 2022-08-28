package com.example.teamzcc.calender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamzcc.R;

import java.util.ArrayList;

//this class is for creating an adapter to illustrate the calender view in the recycle view we have
public class CalenderAdapter extends RecyclerView.Adapter<CalenderViewHolder> {
    private final ArrayList<String> daysOfMonth;//attribute to record the number of months
    private final onItemListener onItemListener;

    public CalenderAdapter(ArrayList<String> daysOfMonth, CalenderAdapter.onItemListener onItemListener) {//constructor
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this creates the calender holder, we need the holder because it saves the view that we do not have to always create a new view when we are zooming in and out
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//Instantiates a layout XML file into its corresponding View objects
        View view = inflater.inflate(R.layout.calender_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);//our height of each boxes are one sixth of the whole phone, we can modulate and change it
        return new CalenderViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        //set the text of each month to display the data at the specified position, we use this method to update the view.
        holder.dayOfMonth.setText(daysOfMonth.get(position));

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface onItemListener {
        void onItemClick(int position, String dayText);
    }
}
