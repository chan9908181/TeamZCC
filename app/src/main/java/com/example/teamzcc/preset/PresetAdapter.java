package com.example.teamzcc.preset;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import com.example.teamzcc.R;
import com.example.teamzcc.calender.CalenderAdapter;


public class PresetAdapter extends RecyclerView.Adapter<PresetViewHolder> {

    private ArrayList<Preset> presets;
    private PresetClickListener clickListener;


    //    public PresetAdapter(Context context, ArrayList<Preset> presets) {
////        this.inflater = LayoutInflater.from(context);
//        this.presets = presets;
//    }
    public PresetAdapter(ArrayList<Preset> presets, PresetAdapter.PresetClickListener clickListener) {
        this.presets = presets;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View presetView = inflater.inflate(R.layout.preset_box, parent, false);
        return new PresetViewHolder(presetView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetViewHolder holder, int position) {
        Preset preset = presets.get(position);
        holder.presetButton.setText(preset.getActivity());
        holder.presetButton.setBackgroundColor(Color.parseColor(preset.getColor().toLowerCase(Locale.ROOT)));
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }


//    public class PresetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//
//        public Button presetButton;
//
//        public PresetViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.presetButton = (Button) itemView.findViewById(R.id.presetBox);
//            itemView.setOnClickListener(this);
//        }
//
//
//        @Override
//        public void onClick(View view) {
//            clickListener.onPresetClick();
//        }
//
//        @Override
//        public boolean onLongClick(View view) {
//            if (presetButton.getText() =="new Preset"){
//            return false;}
//            return true;
//        }
//    }
//
//    public void setClickListener(PresetClickListener clickListener) {
//        this.clickListener = clickListener;
//    }

    public interface PresetClickListener {
        void onPresetClick(String text);
        boolean onPresetLongClick(View view, String text);
    }

}
