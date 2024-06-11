package com.example.campuseventtracker.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.campuseventtracker.MainActivity;
import com.example.campuseventtracker.R;
import com.example.campuseventtracker.databinding.ActivityLoginBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.Helper;
import com.example.campuseventtracker.model.UsersData;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper helper;
    List<UsersData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        setContentView(binding.getRoot());

        helper=new DatabaseHelper(this);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list = helper.getAllUsers();
                String name = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                if (name.isEmpty()){
                    binding.email.setError("Email is Empty");
                    return;
                }
                if (password.isEmpty()){
                    binding.password.setError("Password is Empty");
                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getEmail().equals(name) && list.get(i).getPassword().equals(password)){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Helper.usersData=list.get(i);
                        finish();
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }
}