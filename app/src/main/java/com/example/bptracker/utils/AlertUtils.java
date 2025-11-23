package com.example.bptracker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import com.example.bptracker.R;

public class AlertUtils {

    public static boolean isHighBP(int systolic, int diastolic) {
        return systolic >= 140 || diastolic >= 90;
    }

    public static boolean isCriticalBP(int systolic, int diastolic) {
        return systolic >= 180 || diastolic >= 120;
    }

    public static void showHighBPAlert(Context context, int systolic, int diastolic) {
        String message;
        String title;

        if (isCriticalBP(systolic, diastolic)) {
            title = "CRITICAL BLOOD PRESSURE!";
            message = "Your reading of " + systolic + "/" + diastolic + " is in hypertensive crisis range. Seek immediate medical attention!";
        } else {
            title = "High Blood Pressure";
            message = "Your reading of " + systolic + "/" + diastolic + " indicates high blood pressure. Please consult your doctor.";
        }

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public static int getBPColor(int systolic, int diastolic) {
        if (isCriticalBP(systolic, diastolic)) {
            return Color.parseColor("#FFCDD2"); // Light red
        } else if (isHighBP(systolic, diastolic)) {
            return Color.parseColor("#FFF9C4"); // Light yellow
        } else if (systolic < 90 || diastolic < 60) {
            return Color.parseColor("#E3F2FD"); // Light blue (low BP)
        } else {
            return Color.parseColor("#E8F5E8"); // Light green (normal)
        }
    }
}