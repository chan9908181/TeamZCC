package com.example.teamzcc.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import java.time.LocalDate;
import java.util.Date;

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
        final String[] date = {intent.getStringExtra(CalenderActivity.DATE)};
        String dayOfMonth=intent.getStringExtra(CalenderActivity.DAY);

        TextView textView=findViewById(R.id.dayTextView);
        textView.setText(date[0]);
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

        ItemTouchHelper.SimpleCallback simpleCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String[] array=date[0].split("\\.");
;               int day=Integer.parseInt(array[0]);
                int month=Integer.parseInt(array[1]);
                int year=Integer.parseInt(array[2]);
                LocalDate newDate= LocalDate.of(year,month,day);
                newDate=newDate.plusDays(1);
                date[0] = newDate.getDayOfMonth() + "." + newDate.getMonthValue() + "." + newDate.getYear();
                textView.setText(date[0]);
                textView1.setText(newDate.getDayOfWeek().toString().substring(0,3));
            }
        };
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(simpleCallback1);
        itemTouchHelper1.attachToRecyclerView(gridRecyclerView);
        ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String[] array=date[0].split("\\.");
                ;               int day=Integer.parseInt(array[0]);
                int month=Integer.parseInt(array[1]);
                int year=Integer.parseInt(array[2]);
                LocalDate newDate= LocalDate.of(year,month,day);
                newDate=newDate.minusDays(1);
                date[0]= newDate.getDayOfMonth() + "." + newDate.getMonthValue() + "." + newDate.getYear();
                textView.setText(date[0]);
                textView1.setText(newDate.getDayOfWeek().toString().substring(0,3));

            }
        };
        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback2);
        itemTouchHelper2.attachToRecyclerView(gridRecyclerView);
    }

    @Override
    public void onCellClick(Cell cell) {
        Toast.makeText(getApplicationContext(),"Cell clicked",Toast.LENGTH_SHORT).show();
        //TODO
    }
}