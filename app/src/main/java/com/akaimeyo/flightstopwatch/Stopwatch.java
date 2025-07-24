package com.akaimeyo.flightstopwatch;

import android.util.Log;

import org.apache.commons.lang3.time.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Stopwatch {

    String flightTimer = "FlightTimer";
    String blockTimer = "BlockTimer";

    public Stopwatch(StopWatch blockTime, StopWatch flightTime) {
        this.blockTime = blockTime;
        this.flightTime = flightTime;
    }

    StopWatch flightTime;

    StopWatch blockTime;
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public void createTimers() {
        flightTime = new StopWatch("flightTime");
        blockTime = new StopWatch("blockTime");
    }

    public void startFlightTime() {
        if(!flightTime.isStarted()){
            flightTime.start();
            Log.d(flightTimer, flightTimer + " started");
        }
    }

    public void startBlockTime() {
        if(!blockTime.isStarted()){
            blockTime.start();
            Log.d(blockTimer, blockTimer + " started");}
    }

    public void stopFlightTime() {
        if (flightTime.isStarted()) {
            flightTime.stop();
            Log.d(flightTimer, flightTime.toString());
        }
    }

    public void stopBlockTime() {
        if(blockTime.isStarted()) {
            blockTime.stop();
            Log.d(blockTimer, blockTime.toString());
        }
        if(flightTime.isStarted()) {
            flightTime.stop();
            Log.d(flightTimer, flightTime.toString() + " stopped with Block time");
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
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutesModulo);
    }

    public String getFlightStartDate(){
        return Date.from(flightTime.getStartInstant()).toString();
    }

    public String getFlightStopDate(){
        return Date.from(flightTime.getStopInstant()).toString();
    }
    public String getBlockStartDate(){
        return Date.from(blockTime.getStartInstant()).toString();
    }

    public String getBlockStopDate(){
        return Date.from(blockTime.getStopInstant()).toString();
    }
}
