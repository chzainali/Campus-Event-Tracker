package com.example.campuseventtracker.auth;

import android.os.Bundle;

import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.UsersData;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Toast;


import com.example.campuseventtracker.databinding.ActivityResetPasswordBinding;

import com.example.campuseventtracker.R;

import java.util.List;
import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    DatabaseHelper helper;
    UsersData usersData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        helper = new DatabaseHelper(this);

        binding.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString();
                String phone = binding.phone.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UsersData> list = helper.getAllUsers();
                for (UsersData usersData1 : list) {
                    if (Objects.equals(usersData1.getEmail(), email) && Objects.equals(usersData1.getPhoneNum(), phone)) {
                        usersData = usersData1;
                        break;
                    }
                }
                if (usersData == null) {
                    Toast.makeText(ResetPasswordActivity.this, "No User associated with this Email and Phone", Toast.LENGTH_SHORT).show();
                } else {
                    binding.searchLayout.setVisibility(View.GONE);
                    binding.resetLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.password.getText().toString();
                String confirmPass = binding.conPassword.getText().toString();

                if (password.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPass.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Confirm Password is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPass)) {
                    Toast.makeText(ResetPasswordActivity.this, "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                usersData.setPassword(password);
                int update = helper.updatePassword(usersData);
                if (update > 0) {
                    Toast.makeText(ResetPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

}