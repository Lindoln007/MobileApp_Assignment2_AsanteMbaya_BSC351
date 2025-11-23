package com.example.bptracker.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bptracker.R;
import com.example.bptracker.adapters.BPAdapter;
import com.example.bptracker.database.BPDatabase;
import com.example.bptracker.database.BPReading;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BPAdapter adapter;
    private BPDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = BPDatabase.getDatabase(this);
        initializeViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReadings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BPAdapter(List.of());
        recyclerView.setAdapter(adapter);
    }

    private void loadReadings() {
        executorService.execute(() -> {
            List<BPReading> readings = db.bpDao().getAllReadings();
            handler.post(() -> adapter.setReadings(readings));
        });
    }
}