package com.example.bptracker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BPDao {
    @Insert
    void insert(BPReading bpReading);

    @Query("SELECT * FROM bp_readings ORDER BY dateTime DESC")
    List<BPReading> getAllReadings();

    @Query("SELECT * FROM bp_readings ORDER BY dateTime ASC")
    List<BPReading> getAllReadingsChronological();

    @Query("DELETE FROM bp_readings WHERE id = :id")
    void deleteById(int id);
}