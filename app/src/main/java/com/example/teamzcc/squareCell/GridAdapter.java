package com.example.teamzcc.squareCell;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamzcc.R;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private List<Cell> cells;
    private OnCellClickListener onCellClickListener;

    public GridAdapter(List<Cell> cells, OnCellClickListener onCellClickListener) {
        this.cells = cells;
        this.onCellClickListener = onCellClickListener;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell_day,parent,false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.bind(cells.get(position));
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextview;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextview=itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell) {
            itemView.setBackgroundColor(Color.WHITE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCellClickListener.onCellClick(cell);
                }
            });
        }
    }
}
