package com.example.teamzcc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamzcc.calender.CalenderAdapter;
import com.example.teamzcc.databinding.ActivityMainBinding;
import com.example.teamzcc.preset.EditPresetDialogFragment;
import com.example.teamzcc.preset.Preset;
import com.example.teamzcc.preset.PresetAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements CalenderAdapter.onItemListener
        , PresetAdapter.PresetClickListener
        , EditPresetDialogFragment.EditPresetDialogListener {

    private TextView monthYearText;
    private RecyclerView calenderRecycleView;
    private LocalDate selectedDate;

    //preset attributes
    private ArrayList<Preset> presets = new ArrayList<>();
    private PresetAdapter presetAdapter;
    private RecyclerView presetRecyclerView;

    //current display density
    private float DENSITY;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();//set the local date as now
        setMonthView();

        //dummy presets for demonstration
        presets.add(new Preset("new Preset", "gray"));
        presets.add(new Preset("Work out", "red"));
        presets.add(new Preset("Sleep", "blue"));
        presets.add(new Preset("Shit", "yellow"));
        presets.add(new Preset("Shopping at Lidl", "green"));
        presets.add(new Preset("weird", "teal"));

        setPresetBar();

        //changing between activities in navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_calendar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_statistics:
                        startActivity(new Intent(getApplicationContext(), Statistics.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_calendar:
                        return true;

                    case R.id.navigation_social:
                        startActivity(new Intent(getApplicationContext(), Social.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }

        });
    }


    //for fragments in switching between the bottom nagivation bars
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linearLayout2, fragment);
        fragmentTransaction.commit();

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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();//TODO: set the schedule

    }


    //Preset related stuff below
    //creates preset editor and handles events that are delivered back
    // https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public void createPresetEditor(Preset currentPreset) {
        DialogFragment dialog = new EditPresetDialogFragment();
        if (currentPreset != null) {
            Bundle data = new Bundle();
            data.putString("activity", currentPreset.getActivity());
            data.putString("color", currentPreset.getColor());
            dialog.setArguments(data);
        }
        dialog.show(getSupportFragmentManager(), "edit preset");
    }

    @Override
    public void onPresetEditorCancelClick(EditPresetDialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onPresetEditorSaveClick(EditPresetDialogFragment dialog) {
        Preset temp = dialog.tempPreset;
        presets.add(temp);
        setPresetBar();
        dialog.dismiss();
    }

    private void setPresetBar() {
        presetRecyclerView = (RecyclerView) findViewById(R.id.preset_recycler);
        presetAdapter = new PresetAdapter(presets, this);
        presetRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        presetRecyclerView.setAdapter(presetAdapter);
    }

    @Override
    public void onPresetClick(String text) {
        if (Objects.equals(text, "new Preset")) {
            createPresetEditor(null);
        } else {
            //TODO: for actual presets, clicking means choosing them
        }
    }

    @Override
    public boolean onPresetLongClick(View view, String text) {
        if (Objects.equals(text, "new Preset")) {
            return true;
        } else {
            //TODO: for actual presets, long clicking should call popup menu
            popupPresetMenu(view, text);
            return true;
        }
    }

    private void popupPresetMenu(View view, String activity) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.menu_preset_longpress, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.edit) {
                    //undefined behaviour if there are multiple presets with the same name
                    for (Preset p :
                            presets) {
                        if (p.getActivity().equals(activity)) {
                            createPresetEditor(p);
                            break;
                        }
                    }

                    return true;
                } else if (menuItem.getItemId() == R.id.delete) {
                    //undefined behaviour if there are multiple presets with the same name
                    for (Preset p :
                            presets) {
                        if (p.getActivity().equals(activity)) {
                            presets.remove(p);
                            break;
                        }
                    }
                    setPresetBar();
                    return true;
                }
                menu.dismiss();
                return true;
            }
        });
        menu.show();
    }
}