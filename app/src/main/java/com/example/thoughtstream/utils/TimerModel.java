package com.example.thoughtstream.utils;

import android.os.CountDownTimer;

import com.example.thoughtstream.ui.TimerContract;


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

    public long getAlarmTime() {
        return alarmTime;
    }


    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private int progress;
    private long alarmTime;

    @Override
    public void startTimer() {
        setmTimerRunning(true);
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        alarmTime = mEndTime;
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

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
