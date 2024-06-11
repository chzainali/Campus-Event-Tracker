package com.example.campuseventtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.campuseventtracker.R;
import com.example.campuseventtracker.databinding.ActivityEventDetailsBinding;
import com.example.campuseventtracker.model.Events;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {
    ActivityEventDetailsBinding binding;
    Events events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEventDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        events= (Events) getIntent().getSerializableExtra("data");
        if (events!=null){
            binding.imageView.setImageURI(Uri.parse(events.getImage()));
            binding.tvName.setText(events.getName());
            binding.tvDetails.setText(events.getDetails());
            binding.tvType.setText(events.getType());
            binding.tvPhone.setText(new SimpleDateFormat("dd/MM/yyy hh:mm").format(new Date(events.getDate())));
        }
        binding.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}