package com.example.campuseventtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.campuseventtracker.activities.EventDetailsActivity;
import com.example.campuseventtracker.activities.MyEventsActivitty;
import com.example.campuseventtracker.activities.SubmitEventActivity;
import com.example.campuseventtracker.adapter.RecyclerAdapter;
import com.example.campuseventtracker.auth.LoginActivity;
import com.example.campuseventtracker.databinding.ActivityMainBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.ClickListener;
import com.example.campuseventtracker.model.Events;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RecyclerAdapter adapter;
    DatabaseHelper helper;
    List<Events> list;
    List<Events> filterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));

        helper = new DatabaseHelper(this);
        list = helper.getAllEvents();
        filterList = new ArrayList<>();
        adapter = new RecyclerAdapter(list, this, new ClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
                intent.putExtra("data", list.get(pos));
                startActivity(intent);
            }
        });
        binding.rvEvents.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvEvents.setAdapter(adapter);

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.myEvents) {
                    startActivity(new Intent(MainActivity.this, MyEventsActivitty.class));
                    return true;
                } else if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finishAffinity();
                    return true;
                } else {
                    startActivity(new Intent(MainActivity.this, SubmitEventActivity.class));
                    return true;
                }
            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    adapter.setList(list);
                } else {
                    filterList.clear();
                    for (Events events : list) {
                        if (events.getName().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT))) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                }
            }
        });

        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuType(v);
            }
        });
    }

    private void showMenuType(View view) {
        PowerMenu powerMenu = new PowerMenu.Builder(this)
                .setTextColor(Color.BLACK)
                .setMenuRadius(10f)
                .setTextGravity(Gravity.CENTER)
                .addItem(new PowerMenuItem("All"))
                .addItem(new PowerMenuItem("Sports"))
                .addItem(new PowerMenuItem("Education"))
                .addItem(new PowerMenuItem("Medical"))
                .addItem(new PowerMenuItem("Culture"))
                .addItem(new PowerMenuItem("Science"))
                .build();

        powerMenu.setOnMenuItemClickListener((position, item) -> {
            filterList = new ArrayList<>();
            switch (position) {
                case 0:
                    adapter.setList(list);
                    break;
                case 1:
                    for (Events events : list) {
                        if (Objects.equals(events.getType(), "Sports")) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                    break;
                case 2:
                    for (Events events : list) {
                        if (Objects.equals(events.getType(), "Education")) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                    break;
                case 3:
                    for (Events events : list) {
                        if (Objects.equals(events.getType(), "Medical")) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                    break;
                case 4:
                    for (Events events : list) {
                        if (Objects.equals(events.getType(), "Culture")) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                    break;
                case 5:
                    for (Events events : list) {
                        if (Objects.equals(events.getType(), "Science")) {
                            filterList.add(events);
                        }
                    }
                    adapter.setList(filterList);
                    break;
            }
            powerMenu.dismiss();
        });

        powerMenu.showAsAnchorLeftBottom(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.myEvents) {
            startActivity(new Intent(MainActivity.this, MyEventsActivitty.class));
            return true;
        } else if (item.getItemId() == R.id.logout) {
            finish();
            return true;
        } else {
            startActivity(new Intent(MainActivity.this, SubmitEventActivity.class));
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = helper.getAllEvents();
        adapter.setList(list);
    }
}