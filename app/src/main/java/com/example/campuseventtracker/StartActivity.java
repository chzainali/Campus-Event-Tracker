package com.example.campuseventtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuseventtracker.auth.LoginActivity;
import com.example.campuseventtracker.auth.RegisterActivity;
import com.example.campuseventtracker.databinding.ActivityStartBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.UsersData;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    ActivityStartBinding binding;
    Animation fromTop;
    Animation fromBottom;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        helper=new DatabaseHelper(this);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        binding.ivLogo.setAnimation(fromTop);
        binding.tvAppName.setAnimation(fromBottom);
        List<UsersData> list=helper.getAllUsers();

        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.isEmpty()){
                            showRegisterDialog();
                        }else {
                            showLoginDialog();
                        }
                    }
                });

            } catch (InterruptedException e) {
                Toast.makeText(StartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        thread.start();

    }

    private void showRegisterDialog() {

        final Dialog dialog = new Dialog(StartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        TextView tvLogin = dialog.findViewById(R.id.tvLogin);
        Button btnRegister = dialog.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();

            }
        });

        dialog.show();
    }

    private void showLoginDialog() {

        final Dialog dialog = new Dialog(StartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        TextView tvRegister = dialog.findViewById(R.id.tvRegister);
        Button btnLogin = dialog.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                finish();
            }
        });

        dialog.show();
    }

}