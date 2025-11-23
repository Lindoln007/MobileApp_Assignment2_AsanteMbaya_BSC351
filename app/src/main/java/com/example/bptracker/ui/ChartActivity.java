package com.example.bptracker.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bptracker.R;
import com.example.bptracker.database.BPDatabase;
import com.example.bptracker.database.BPReading;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartActivity extends AppCompatActivity {
    private LineChart chart;
    private TextView tvNoData;
    private BPDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private XAxis xAxis;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        db = BPDatabase.getDatabase(this);
        chart = findViewById(R.id.chart);
        tvNoData = findViewById(R.id.tvNoData);

        setupChart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChartData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void setupChart() {
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);

        xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);

        chart.getAxisRight().setEnabled(false);
    }

    private void loadChartData() {
        executorService.execute(() -> {
            List<BPReading> readings = db.bpDao().getAllReadingsChronological();
            handler.post(() -> {
                if (readings != null && !readings.isEmpty()) {
                    tvNoData.setVisibility(View.GONE);
                    chart.setVisibility(View.VISIBLE);
                    updateChart(readings);
                } else {
                    chart.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    private void updateChart(List<BPReading> readings) {
        List<Entry> systolicEntries = new ArrayList<>();
        List<Entry> diastolicEntries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (int i = 0; i < readings.size(); i++) {
            BPReading reading = readings.get(i);
            systolicEntries.add(new Entry(i, reading.getSystolic()));
            diastolicEntries.add(new Entry(i, reading.getDiastolic()));
            dates.add(sdf.format(new Date(reading.getDateTime())));
        }

        LineDataSet systolicDataSet = createLineDataSet(systolicEntries, "Systolic", Color.RED);
        LineDataSet diastolicDataSet = createLineDataSet(diastolicEntries, "Diastolic", Color.BLUE);

        LineData lineData = new LineData(systolicDataSet, diastolicDataSet);
        chart.setData(lineData);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setLabelCount(Math.min(dates.size(), 10), true);

        chart.invalidate();
    }

    private LineDataSet createLineDataSet(List<Entry> entries, String label, int color) {
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(color);
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(color);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(10f);
        return dataSet;
    }
}