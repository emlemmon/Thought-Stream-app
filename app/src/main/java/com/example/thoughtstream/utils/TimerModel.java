package com.example.thoughtstream.utils;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.thoughtstream.ui.TimerContract;

import java.util.Calendar;


public class TimerModel implements TimerContract.Model{
    public CountDownTimer getmCountDownTimer() {
        return mCountDownTimer;
    }

    public long getmStartTimeInMillis() {
        return mStartTimeInMillis;
    }

    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    @Override
    public boolean getmTimerRunning() {
        return mTimerRunning;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setmTimerRunning(boolean mTimerRunning) {
        this.mTimerRunning = mTimerRunning;
    }

    public void setmStartTimeInMillis(long mStartTimeInMillis) {
        this.mStartTimeInMillis = mStartTimeInMillis;
    }

    public void setmTimeLeftInMillis(long mTimeLeftInMillis) {
        this.mTimeLeftInMillis = mTimeLeftInMillis;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public Calendar getAlarmTime() {
        return alarmTime;
    }


    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private int progress;
    private Calendar alarmTime;

    @Override
    public void startTimer() {
        setmTimerRunning(true);
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        int hours = (int) (mEndTime / 1000) / 3600;
        int minutes = (int) ((mEndTime / 1000) % 3600) / 60;
        alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hours);
        alarmTime.set(Calendar.MINUTE, minutes);
        alarmTime.set(Calendar.SECOND, 0);
    }

    @Override
    public void pauseTimer() {
        mCountDownTimer.cancel();
        setmTimerRunning(false);
    }

    @Override
    public void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        progress = 0;
    }

    @Override
    public void cancelTimer() {
        mCountDownTimer.cancel();
    }

    @Override
    public void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    public void setmCountDownTimer(CountDownTimer mCountDownTimer) {
        this.mCountDownTimer = mCountDownTimer;
    }

    public int setProgress(int progress) {
        this.progress = progress;
        return progress;
    }
}
