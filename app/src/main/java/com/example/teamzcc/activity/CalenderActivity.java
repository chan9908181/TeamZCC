package com.example.teamzcc.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.teamzcc.R;
import com.example.teamzcc.calender.CalenderAdapter;
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
public class CalenderActivity extends AppCompatActivity implements CalenderAdapter.onItemListener
        , PresetAdapter.PresetClickListener
        , EditPresetDialogFragment.EditPresetDialogListener {

    private TextView monthYearText;
    private RecyclerView calenderRecycleView;
    private LocalDate selectedDate;

    //preset attributes
    private ArrayList<Preset> presets = new ArrayList<>();
    private PresetAdapter presetAdapter;
    private RecyclerView presetRecyclerView;
    private Preset selectedPreset;
    private int selectedPresetPosition;

    public static final String DATE = "com.example.teamzcc.example.DAY_TEXT";
    public static final String DAY = "com.example.teamzcc.example.DAY";


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
                        startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_calendar:
                        return true;

                    case R.id.navigation_social:
                        startActivity(new Intent(getApplicationContext(), SocialActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });
    }


    //for fragments in switching between the bottom nagivation bars
    private void replaceFragment(Fragment fragment) {
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
        ItemTouchHelper.SimpleCallback simpleCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        };
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(simpleCallback1);
        itemTouchHelper1.attachToRecyclerView(calenderRecycleView);
        ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        };
        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback2);
        itemTouchHelper2.attachToRecyclerView(calenderRecycleView);
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
        Intent intent = new Intent(getApplicationContext(), DayActivity.class);
        String date = dayText + "." + selectedDate.getMonthValue() + "." + selectedDate.getYear();
        intent.putExtra(DATE, date);
        LocalDate dayOfMonth = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), Integer.parseInt(dayText));
        intent.putExtra(DAY, dayOfMonth.getDayOfWeek().toString());
        startActivity(intent);

    }


    //Preset related stuff below
    //creates preset editor and handles events that are delivered back
    // https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public void createPresetEditor(Preset currentPreset) {
        DialogFragment dialog = new EditPresetDialogFragment();
        Bundle data = new Bundle();
        if (currentPreset != null) {
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
        Preset newPreset = dialog.getNewPreset();
        Boolean edited = dialog.getEdited();
        if (edited) {
            Preset oldPreset = dialog.getOldPreset();
            for (int i = 0; i < presets.size(); i++) {
                Preset p = presets.get(i);
                if (p.equals(oldPreset)) {
                    presets.set(i, newPreset);
                    break;
                }
            }
        } else {
            presets.add(newPreset);
        }
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
            //short clicking new preset opens the preset editor to create a new preset
            createPresetEditor(null);
        } else {
            //for actual presets, clicking means choosing them
            int count = 0;
            for (Preset p : presets) {
                if (p.getActivity().equals(text)) {
                    //if the clicked preset has already been selected, reverse the selection so that nothing is selected
                    if (selectedPreset != null && p.getActivity().equals(selectedPreset.getActivity())) {
                        RecyclerView.ViewHolder view = ((RecyclerView) findViewById(R.id.preset_recycler)).findViewHolderForAdapterPosition(selectedPresetPosition);
                        resizePreset(view.itemView, SpringAnimation.SCALE_X, 1f).start();
                        resizePreset(view.itemView, SpringAnimation.SCALE_Y, 1f).start();
                        selectedPreset = null;
                        selectedPresetPosition = -1;
                        break;
                    }
                    //if no preset is currently selected, select the one that was clicked
                    if (selectedPreset == null) {
                        selectedPreset = p;
                        selectedPresetPosition = count;
                        RecyclerView.ViewHolder view = ((RecyclerView) findViewById(R.id.preset_recycler)).findViewHolderForAdapterPosition(selectedPresetPosition);
                        resizePreset(view.itemView, SpringAnimation.SCALE_X, 0.8f).start();
                        resizePreset(view.itemView, SpringAnimation.SCALE_Y, 0.8f).start();
                        break;
                    }
                    //if there is a already selected preset and another one is clicked, unselect the old one and select the new one
                    if (selectedPreset != null && !p.getActivity().equals(selectedPreset.getActivity())) {
                        RecyclerView.ViewHolder view = ((RecyclerView) findViewById(R.id.preset_recycler)).findViewHolderForAdapterPosition(selectedPresetPosition);
                        resizePreset(view.itemView, SpringAnimation.SCALE_X, 1f).start();
                        resizePreset(view.itemView, SpringAnimation.SCALE_Y, 1f).start();
                        selectedPreset = p;
                        selectedPresetPosition = count;
                        view = ((RecyclerView) findViewById(R.id.preset_recycler)).findViewHolderForAdapterPosition(selectedPresetPosition);
                        resizePreset(view.itemView, SpringAnimation.SCALE_X, 0.8f).start();
                        resizePreset(view.itemView, SpringAnimation.SCALE_Y, 0.8f).start();
                        break;
                    }
                    break;
                } else {
                    ++count;
                }
            }
        }
    }


    @Override
    public boolean onPresetLongClick(View view, String text) {
        if (Objects.equals(text, "new Preset")) {
            //long clicking new preset does nothing
            return true;
        } else {
            //for actual presets, long clicking should call popup menu
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
                    for (Preset p : presets) {
                        if (p.getActivity().equals(activity)) {
                            createPresetEditor(p);
                            break;
                        }
                    }

                    return true;
                } else if (menuItem.getItemId() == R.id.delete) {
                    //undefined behaviour if there are multiple presets with the same name
                    for (Preset p : presets) {
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

    private SpringAnimation resizePreset(View view, FloatPropertyCompat<View> springAnimationType, Float finalPosition) {
        SpringAnimation animation = new SpringAnimation(view, springAnimationType);
        SpringForce spring = new SpringForce();
        spring.setFinalPosition(finalPosition);
        animation.setSpring(spring);
        return animation;
    }
    //https://medium.com/@anitaa_1990/android-how-to-change-the-height-of-a-view-using-animation-9aad81369781
//    public static void slideView(View view,
//                                 int currentHeight,
//                                 int newHeight) {
//
//        ValueAnimator slideAnimator = ValueAnimator
//                .ofInt(currentHeight, newHeight)
//                .setDuration(500);
//
//        /* We use an update listener which listens to each tick
//         * and manually updates the height of the view  */
//
//        slideAnimator.addUpdateListener(animation1 -> {
//            Integer value = (Integer) animation1.getAnimatedValue();
//            view.getLayoutParams().height = value.intValue();
//            view.requestLayout();
//        });
//
//        /*  We use an animationSet to play the animation  */
//
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animationSet.play(slideAnimator);
//        animationSet.start();

}
