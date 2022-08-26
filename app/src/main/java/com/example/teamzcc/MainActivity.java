package com.example.teamzcc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements CalenderAdapter.onItemListener, EditPresetDialogFragment.EditPresetDialogListener {

    private TextView monthYearText;
    private RecyclerView calenderRecycleView;
    private LocalDate selectedDate;

    //preset attributes
    private Set<Preset> presets = new HashSet<>();
    //current display density
    private float DENSITY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();//set the local date as now
        setMonthView();
        //populates the preset bar on create, needs a permanent mean of storage in the future
        DENSITY = getApplicationContext()
                .getResources()
                .getDisplayMetrics()
                .density;
        for (Preset p : presets) {
            updatePresetBar(p);
        }

        //opens preset editor popup window, which is a dialog
        Button addPresetButton = (Button) findViewById(R.id.addPreset);
        addPresetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPresetEditor();
            }
        });
    }

    //calender View Methods
    private void initWidgets() {
        //find our views with the ID
        calenderRecycleView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        //set the textview text date
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);//our calender has 7 grids
        calenderRecycleView.setLayoutManager(layoutManager);
        //here the layoutmanager would automatically place our dates in to a 7 column calender
        calenderRecycleView.setAdapter(calenderAdapter);

    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        //create the string array of each blank and dates of a month
        ArrayList<String> daysInMonth = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonths = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 0; i < 42; i++) {
            if (i <= dayOfWeek || i > daysInMonths + dayOfWeek) {
                daysInMonth.add("");
            } else {
                daysInMonth.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonth;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        //implement a function where we can zoom in for each date.
        String message = "Selected Date" + dayText + "" + monthYearFromDate(selectedDate);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();//TODO//set the schedule
    }


    //Preset related stuff below
    //creates preset editor and handles events that are delivered back
    // https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public void createPresetEditor() {
        DialogFragment dialog = new EditPresetDialogFragment();
        dialog.show(getSupportFragmentManager(), "edit preset");
    }

    @Override
    public void onPresetCancelClick(EditPresetDialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onPresetSaveClick(EditPresetDialogFragment dialog) {
        Preset temp = dialog.tempPreset;
        presets.add(temp);
        updatePresetBar(temp);
        dialog.dismiss();
    }

    private void updatePresetBar(Preset p) {
        LinearLayout.LayoutParams dimensions = new LinearLayout.LayoutParams((int)DENSITY*150, (int)DENSITY*50);
        dimensions.setMargins((int)DENSITY*10,(int)DENSITY*10,(int)DENSITY*10,(int)DENSITY*10);
        LinearLayout presetTopRow = (LinearLayout) findViewById(R.id.presetTopRow);
//        LinearLayout presetBottomRow = (LinearLayout) findViewById(R.id.presetBottomRow);
        Button newPreset = new Button(this);
        newPreset.setLayoutParams(dimensions);
        newPreset.setText(p.getActivity());
        newPreset.setBackgroundColor(Color.parseColor(p.getColor().toLowerCase(Locale.ROOT)));
        presetTopRow.addView(newPreset);
    }
}