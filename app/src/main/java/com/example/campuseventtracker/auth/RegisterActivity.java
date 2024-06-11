package com.example.campuseventtracker.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.campuseventtracker.R;
import com.example.campuseventtracker.databinding.ActivityRegisterBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.UsersData;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        helper = new DatabaseHelper(this);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String phone = binding.phone.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                if (name.isEmpty()) {
                    binding.name.setError("Name is Empty");
                    return;

                }
                if (phone.isEmpty()) {
                    binding.phone.setError("Phone is Empty");
                    return;
                }
                if (email.isEmpty()) {
                    binding.email.setError("Email is Empty");
                    return;
                }
                if (password.isEmpty()) {
                    binding.password.setError("Password is Empty");
                    return;
                }
                UsersData data = new UsersData(name, email, password, phone);
                helper.register(data);
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finishAffinity();
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finishAffinity();
            }
        });
    }
}