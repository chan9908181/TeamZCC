package com.example.teamzcc.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamzcc.R;
import com.example.teamzcc.squareCell.Cell;
import com.example.teamzcc.squareCell.GridAdapter;
import com.example.teamzcc.squareCell.OnCellClickListener;
import com.example.teamzcc.squareCell.SquareGrid;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DayActivity extends AppCompatActivity implements OnCellClickListener {
    private static final String TAG = "DayActivity";
    RecyclerView gridRecyclerView;
    GridAdapter gridAdapter;
    SquareGrid squareGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Intent intent=getIntent();
        String date=intent.getStringExtra(CalenderActivity.DATE);
        String dayOfMonth=intent.getStringExtra(CalenderActivity.DAY);

        TextView textView=findViewById(R.id.dayTextView);
        textView.setText(date);
        TextView textView1=findViewById(R.id.dayOfMonth);
        textView1.setText(dayOfMonth.substring(0,3));

        Button closeBTN = (Button) findViewById(R.id.closeBTN);
        closeBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        gridRecyclerView=findViewById(R.id.activity_day_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this,12));
        squareGrid=new SquareGrid(12);
        gridAdapter=new GridAdapter(squareGrid.getCells(),this);
        gridRecyclerView.setAdapter(gridAdapter);

    }

    @Override
    public void onCellClick(Cell cell) {
        Toast.makeText(getApplicationContext(),"Cell clicked",Toast.LENGTH_SHORT).show();
    }
}