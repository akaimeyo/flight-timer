package com.akaimeyo.flightstopwatch;

import android.util.Log;

import org.apache.commons.lang3.time.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Stopwatch {

    String flightTimer = "FlightTimer";
    String blockTimer = "BlockTimer";
    boolean isBlockTimeStarted = false;
    boolean isFlightTimeStarted = false;

    public Stopwatch(StopWatch blockTime, StopWatch flightTime) {
        this.blockTime = blockTime;
        this.flightTime = flightTime;
    }

    StopWatch flightTime;

    StopWatch blockTime;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public void createTimers() {
        flightTime = new StopWatch("flightTime");
        blockTime = new StopWatch("blockTime");
    }

    public void toggleBlockTime() {
        if (!blockTime.isStarted()) {
            blockTime.start();
            isBlockTimeStarted = true;
            Log.d(blockTimer, blockTimer + " started");
        } else if (blockTime.isStarted() && !blockTime.isSuspended()) {
            blockTime.suspend();
            isBlockTimeStarted = false;
            Log.d(blockTimer, blockTimer + " suspended");
        } else if (blockTime.isSuspended()) {
            blockTime.resume();
            isBlockTimeStarted = true;
            Log.d(blockTimer, blockTimer + " resumed");
        }
    }

    public void toggleFlightTime() {
        if (!flightTime.isStarted()) {
            flightTime.start();
            isFlightTimeStarted = true;
            Log.d(flightTimer, flightTimer + " started");
        } else if (flightTime.isStarted() && !flightTime.isSuspended()) {
            flightTime.suspend();
            isFlightTimeStarted = false;
            Log.d(flightTimer, flightTimer + " suspended");
        } else if (flightTime.isSuspended()) {
            flightTime.resume();
            isFlightTimeStarted = true;
            Log.d(flightTimer, flightTimer + " resumed");
        }
    }

    public String getFlightTime() {
        long minutes = flightTime.getDuration().toMinutes();
        long hours = minutes / 60;
        long minutesModulo = minutes % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutesModulo);
    }

    public String getBlockTime() {
        long minutes = blockTime.getDuration().toMinutes();
        long hours = minutes / 60;
        long minutesModulo = minutes % 60;
        long seconds = blockTime.getDuration().getSeconds() % 60; //for debugging only
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutesModulo, seconds);
    }

    public String getFLightDate() {
        return dateFormatter.format(new Date().getTime());
    }

    public void resetTimers() {
        blockTime.reset();
        flightTime.reset();
        isBlockTimeStarted = false;
        isFlightTimeStarted = false;
    }
}
