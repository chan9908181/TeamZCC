package com.example.teamzcc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

/**
 * this class is not used.
 * It's replaces by the class Statistics as we decided to use activities instead of fragments
 * for changing between the bottom navigation bar.
 * However, it is kept just in case it will be needed in the future*/

public class StatFragment extends Fragment {

    private View root;

    public StatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_stat, container, false);
        }
        return root;
    }
}