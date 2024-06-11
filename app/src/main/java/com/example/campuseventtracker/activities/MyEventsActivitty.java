package com.example.campuseventtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.campuseventtracker.R;
import com.example.campuseventtracker.adapter.RecyclerAdapter;
import com.example.campuseventtracker.databinding.ActivityMyEventsActivittyBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.ClickListener;
import com.example.campuseventtracker.model.Events;
import com.example.campuseventtracker.model.Helper;

import java.util.List;

public class MyEventsActivitty extends AppCompatActivity {
    ActivityMyEventsActivittyBinding binding;
    RecyclerAdapter adapter;
    List<Events> list;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyEventsActivittyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        helper = new DatabaseHelper(this);
        list = helper.getEventsByUserId(Helper.usersData.getId());

        list.addAll(helper.getFavoriteEventsByUserId(Helper.usersData.getId()));
        adapter = new RecyclerAdapter(list, this, new ClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = (new Intent(MyEventsActivitty.this, EventDetailsActivity.class));
                intent.putExtra("data",list.get(pos));
                startActivity(intent);
            }
        });
        binding.rvEvents.setLayoutManager(new GridLayoutManager(this,2));
        binding.rvEvents.setAdapter(adapter);

        binding.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}