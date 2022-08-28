package com.example.teamzcc.preset;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamzcc.R;

import org.w3c.dom.Text;

public class PresetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView presetButton;
    private PresetAdapter.PresetClickListener clickListener;

    public PresetViewHolder(@NonNull View itemView, PresetAdapter.PresetClickListener clickListener) {
        super(itemView);
        this.presetButton = (TextView) itemView.findViewById(R.id.presetBox);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View view) {
        clickListener.onPresetClick(presetButton.getText().toString());
    }

    @Override
    public boolean onLongClick(View view) {
        return clickListener.onPresetLongClick(view, presetButton.getText().toString());
    }
}
