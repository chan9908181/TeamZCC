package com.example.teamzcc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements CalenderAdapter.onItemListener{

    private TextView monthYearText;
    private RecyclerView calenderRecycleView;
    private LocalDate selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate=LocalDate.now();//set the local date as now

    }

    private void initWidgets() {
        //find our views with the ID
        calenderRecycleView=findViewById(R.id.calenderRecyclerView);
        monthYearText=findViewById(R.id.monthYearTV);
    }

    private void setMonthView(){
        //set the textview text date
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth=daysInMonthArray(selectedDate);
        CalenderAdapter calenderAdapter=new CalenderAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),7);//our calender has 7 grids
        calenderRecycleView.setLayoutManager(layoutManager);
        calenderRecycleView.setAdapter(calenderAdapter);

    }


    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonth=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(date);

        int daysInMonths=yearMonth.lengthOfMonth();
        LocalDate firstOfMonth=selectedDate.withDayOfMonth(1);
        int dayOfWeek=firstOfMonth.getDayOfWeek().getValue();

        for(int i=0;i<42;i++){
            if(i<=dayOfWeek ||i>daysInMonths+dayOfWeek){
                daysInMonth.add("");
            }else{
                daysInMonth.add(String.valueOf(i-dayOfWeek));
            }
        }
        return daysInMonth;
    }


    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate =selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate=selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        String message= "Selected Date"+dayText+""+monthYearFromDate(selectedDate);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();//TODO//
    }
}