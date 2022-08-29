package com.example.teamzcc.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.teamzcc.R;
import com.example.teamzcc.activity.CalenderActivity;
import com.example.teamzcc.activity.SocialActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_statistics);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_calendar:
                        startActivity(new Intent(getApplicationContext(), CalenderActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_statistics:
                        return true;

                    case R.id.navigation_social:
                        startActivity(new Intent(getApplicationContext(), SocialActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }

        });
    }
}