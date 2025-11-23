package com.example.bptracker.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BPReading.class}, version = 2, exportSchema = false)
public abstract class BPDatabase extends RoomDatabase {
    public abstract BPDao bpDao();

    private static volatile BPDatabase INSTANCE;

    public static BPDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BPDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BPDatabase.class, "bp_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}