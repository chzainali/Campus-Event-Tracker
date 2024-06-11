package com.example.campuseventtracker.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.campuseventtracker.R;
import com.example.campuseventtracker.databinding.ActivitySubmitEventBinding;
import com.example.campuseventtracker.db.DatabaseHelper;
import com.example.campuseventtracker.model.Events;
import com.example.campuseventtracker.model.Helper;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SubmitEventActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour, mMinute;
    ActivitySubmitEventBinding binding;
    String image;

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmitEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_color));
        helper = new DatabaseHelper(this);

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker(binding.date);
            }
        });

        binding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuType(v);
            }
        });

        binding.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        binding.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String date = binding.date.getText().toString();
                String type = binding.type.getText().toString();
                String details = binding.details.getText().toString();
                String location = binding.location.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(SubmitEventActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.isEmpty()) {
                    Toast.makeText(SubmitEventActivity.this, "Date is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.isEmpty()) {
                    Toast.makeText(SubmitEventActivity.this, "Type is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (details.isEmpty()) {
                    Toast.makeText(SubmitEventActivity.this, "Details is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (location.isEmpty()) {
                    Toast.makeText(SubmitEventActivity.this, "Location is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (image == null) {
                    Toast.makeText(SubmitEventActivity.this, "Image is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Events events = new Events(formatTime(date), Helper.usersData.getId(), name, type, image, details, location);
                helper.addEvent(events);
                Toast.makeText(SubmitEventActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void showMenuType(View view) {
        PowerMenu powerMenu = new PowerMenu.Builder(this)
                .setTextColor(Color.BLACK)
                .setMenuRadius(10f)
                .setTextGravity(Gravity.CENTER)
                .addItem(new PowerMenuItem("Sports"))
                .addItem(new PowerMenuItem("Education"))
                .addItem(new PowerMenuItem("Medical"))
                .addItem(new PowerMenuItem("Culture"))
                .addItem(new PowerMenuItem("Science"))
                .build();

        powerMenu.setOnMenuItemClickListener((position, item) -> {
            binding.type.setText(item.getTitle());

            powerMenu.dismiss();
        });

        powerMenu.showAsAnchorLeftBottom(view);
    }


    private void ShowDatePicker(EditText editText) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mDay = dayOfMonth;
                        mMonth = monthOfYear + 1;
                        mYear = year;
                        showTimePicker(editText);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimePicker(EditText editText) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        editText.setText(mDay + "/" + mMonth + "/" + mYear + " " + mHour + ":" + mMinute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private Long formatTime(String date) {
        long millis = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            Date d = f.parse(date);
            millis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            try {
                uri = getImageUriFromBitmap(this, MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                binding.logo.setImageURI(uri);
                image = uri.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private Uri getImageUriFromBitmap(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "File", null);
        return Uri.parse(path);
    }

}