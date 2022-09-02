package com.example.teamzcc.preset;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import com.example.teamzcc.R;

public class PresetAdapter extends RecyclerView.Adapter<PresetViewHolder> {

    private ArrayList<Preset> presets;
    private PresetClickListener clickListener;

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


    public interface PresetClickListener {
        void onPresetClick(String text);
        boolean onPresetLongClick(View view, String text);
    }

}
