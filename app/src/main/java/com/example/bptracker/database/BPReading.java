package com.example.bptracker.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bp_readings")
public class BPReading {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int systolic;
    private int diastolic;
    private int pulse;
    private long dateTime;
    private String notes;

    public BPReading(int systolic, int diastolic, int pulse, long dateTime, String notes) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSystolic() { return systolic; }
    public void setSystolic(int systolic) { this.systolic = systolic; }

    public int getDiastolic() { return diastolic; }
    public void setDiastolic(int diastolic) { this.diastolic = diastolic; }

    public int getPulse() { return pulse; }
    public void setPulse(int pulse) { this.pulse = pulse; }

    public long getDateTime() { return dateTime; }
    public void setDateTime(long dateTime) { this.dateTime = dateTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}