package com.example.teamzcc.squareCell;

import java.util.ArrayList;
import java.util.List;

public class SquareGrid {
    private List<Cell> cells;
    private int size;

    public SquareGrid(int size) {
        this.size = size;
        cells=new ArrayList<>();
        for(int i=0; i<size*size;i++){
            cells.add(new Cell());
        }
    }

    public List<Cell> getCells() {
        return cells;
    }
}
