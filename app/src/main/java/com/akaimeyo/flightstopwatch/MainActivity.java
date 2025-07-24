package com.akaimeyo.flightstopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.StopWatch;

public class MainActivity extends AppCompatActivity {

    StopWatch blockTime;
    StopWatch flightTime;

    Button blockButton;
    Button flightButton;
    TextView blockTimeTextView;
    TextView flightTimeTextView;
    Stopwatch stopwatch = new Stopwatch(blockTime, flightTime);

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateTimerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stopwatch.createTimers();
        blockButton = findViewById(R.id.blockButton);
        flightButton = findViewById(R.id.flightButton);
        blockTimeTextView = findViewById(R.id.blockTime);
        flightTimeTextView = findViewById(R.id.flightTime);

        blockButton.setOnClickListener(v -> {
            if (!stopwatch.blockTime.isStarted()) {
                stopwatch.startBlockTime();
                blockButton.setText(R.string.stop_block);
            } else {
                stopwatch.stopBlockTime();
                blockButton.setText(R.string.start_block);
            }
        });
        flightButton.setOnClickListener(v -> {
            if (!stopwatch.flightTime.isStarted()) {
                stopwatch.startFlightTime();
                flightButton.setText(R.string.stop_flight);
            } else {
                stopwatch.stopFlightTime();
                flightButton.setText(R.string.start_flight);
            }
        });

        updateTimerRunnable = new Runnable() {
            @Override
            public void run() {
                blockTimeTextView.setText(stopwatch.getBlockTime());
                flightTimeTextView.setText(stopwatch.getFlightTime());
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(updateTimerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(updateTimerRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimerRunnable);

    }
}