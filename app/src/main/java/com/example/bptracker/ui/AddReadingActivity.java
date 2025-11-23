package com.example.bptracker.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bptracker.R;
import com.example.bptracker.database.BPDatabase;
import com.example.bptracker.database.BPReading;
import com.example.bptracker.utils.AlertUtils;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddReadingActivity extends AppCompatActivity {
    private EditText etSystolic, etDiastolic, etPulse, etNotes;
    private TextView tvDateTime;
    private Calendar selectedDateTime;
    private BPDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);

        db = BPDatabase.getDatabase(this);
        selectedDateTime = Calendar.getInstance();

        initializeViews();
        setupDateTimePicker();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void initializeViews() {
        etSystolic = findViewById(R.id.etSystolic);
        etDiastolic = findViewById(R.id.etDiastolic);
        etPulse = findViewById(R.id.etPulse);
        etNotes = findViewById(R.id.etNotes);
        tvDateTime = findViewById(R.id.tvDateTime);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnPickDateTime = findViewById(R.id.btnPickDateTime);

        btnSave.setOnClickListener(v -> saveReading());
        btnPickDateTime.setOnClickListener(v -> showDateTimePicker());

        updateDateTimeDisplay();
    }

    private void setupDateTimePicker() {
        tvDateTime.setOnClickListener(v -> showDateTimePicker());
    }

    private void showDateTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    showTimePicker();
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    updateDateTimeDisplay();
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateTimeDisplay() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault());
        tvDateTime.setText(sdf.format(selectedDateTime.getTime()));
    }

    private void saveReading() {
        String systolicStr = etSystolic.getText().toString();
        String diastolicStr = etDiastolic.getText().toString();
        String pulseStr = etPulse.getText().toString();

        if (systolicStr.isEmpty() || diastolicStr.isEmpty() || pulseStr.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int systolic = Integer.parseInt(systolicStr);
        int diastolic = Integer.parseInt(diastolicStr);
        int pulse = Integer.parseInt(pulseStr);
        String notes = etNotes.getText().toString();

        if (AlertUtils.isHighBP(systolic, diastolic)) {
            AlertUtils.showHighBPAlert(this, systolic, diastolic);
        }

        BPReading reading = new BPReading(systolic, diastolic, pulse, selectedDateTime.getTimeInMillis(), notes);

        executorService.execute(() -> {
            db.bpDao().insert(reading);
            handler.post(() -> {
                Toast.makeText(AddReadingActivity.this, "Reading saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}