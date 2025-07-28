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
    Button resetButton;
    TextView blockTimeTextView;
    TextView flightTimeTextView;
    TextView flightDateInput;
    Stopwatch stopwatch = new Stopwatch(blockTime, flightTime);

    private final Handler handler = new Handler(Looper.getMainLooper());
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

        initElements();
        initListeners();
        setFlightDate();

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

    public void initElements() {
        stopwatch.createTimers();
        blockButton = findViewById(R.id.blockButton);
        flightButton = findViewById(R.id.flightButton);
        resetButton = findViewById(R.id.resetButton);
        blockTimeTextView = findViewById(R.id.blockTime);
        flightTimeTextView = findViewById(R.id.flightTime);
        flightDateInput = findViewById(R.id.flightDateInput);

    }

    public void initListeners() {

        blockButton.setOnClickListener(v -> {
            stopwatch.toggleBlockTime();
            blockButton.setText(stopwatch.isBlockTimeStarted ? R.string.stop_block : R.string.start_block);
        });
        flightButton.setOnClickListener(v -> {
            stopwatch.toggleFlightTime();
            flightButton.setText(stopwatch.isFlightTimeStarted ? R.string.stop_flight : R.string.start_flight);
        });
        resetButton.setOnClickListener(v -> stopwatch.resetTimers());
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

    public void setFlightDate() {
        flightDateInput.setText(stopwatch.getFLightDate());
    }
}