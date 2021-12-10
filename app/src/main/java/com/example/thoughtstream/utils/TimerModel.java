package com.example.thoughtstream.utils;

import android.os.CountDownTimer;

import com.example.thoughtstream.ui.TimerContract;


public class TimerModel implements TimerContract.Model{

    /* Function: getmCountDownTimer()
    * Purpose: retrieve the state of the CountDownTimer mCountDownTimer
    * Returns (CountDownTimer): The object mCountDownTimer*/
    public CountDownTimer getmCountDownTimer() {
        return mCountDownTimer;
    }

    /* Function: getmStartTimeInMillis()
     * Purpose: retrieve the state of the variable mStartTimeInMillis
     * Returns (long): the time (in milliseconds) that the timer was started.*/
    public long getmStartTimeInMillis() {
        return mStartTimeInMillis;
    }

    /* Function: getmTimeLeftInMillis()
     * Purpose: retrieve the state of the variable mTimeLeftInMillis
     * Returns (long): the time (in milliseconds) that is left in the timer.*/
    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    /* Function: getmTimerRunning()
     * Purpose: retrieve the state of the boolean mTimerRunning
     * Returns (boolean): whether or not the CountDownTimer is currently ticking down.*/
    @Override
    public boolean getmTimerRunning() {
        return mTimerRunning;
    }

    /* Function: getmEndTime()
     * Purpose: retrieve the state of the variable mEndTime
     * Returns (long): the time (in milliseconds) that the timer will end at.*/
    public long getmEndTime() {
        return mEndTime;
    }

    /* Function: getProgress()
     * Purpose: retrieve the state of the variable progress
     * Returns (int): how much progress has been made towards the end of the timer.*/
    public int getProgress() {
        return progress;
    }

    /* Function: getAlarmTime()
     * Purpose: retrieve the state of the variable alarmTime
     * Returns (long): the time (in milliseconds) that the AlarmManager will notify the user.*/
    public long getAlarmTime() {
        return alarmTime;
    }

    /* Function: setmTimerRunning()
     * Purpose: set the state of the variable mTimerRunning
     * Parameter (mTimerRunning): The desired state of mTimerRunning*/
    public void setmTimerRunning(boolean mTimerRunning) {
        this.mTimerRunning = mTimerRunning;
    }

    /* Function: setmStartTimeInMillis()
     * Purpose: set the state of the variable mStartTimeInMillis
     * Parameter (mStartTimeInMillis): The desired state of mStartTimeInMillis*/
    public void setmStartTimeInMillis(long mStartTimeInMillis) {
        this.mStartTimeInMillis = mStartTimeInMillis;
    }

    /* Function: setmTimeLeftInMillis()
     * Purpose: set the state of the variable mTimeLeftInMillis
     * Parameter (mTimeLeftInMillis): The desired state of mTimeLeftInMillis*/
    public void setmTimeLeftInMillis(long mTimeLeftInMillis) {
        this.mTimeLeftInMillis = mTimeLeftInMillis;
    }

    /* Function: setmEndTime()
     * Purpose: set the state of the variable mEndTime
     * Parameter (mEndTime): The desired state of mEndTime*/
    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    /* Function: setmCountDownTimer()
     * Purpose: set the state of the object mCountDownTimer
     * Parameter (mCountDownTimer): The desired state of mCountDownTimer*/
    public void setmCountDownTimer(CountDownTimer mCountDownTimer) {
        this.mCountDownTimer = mCountDownTimer;
    }

    /* Function: setProgress()
     * Purpose: set the state of the variable progress
     * Parameter (progress): The desired state of progress*/
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /* Declare all member variables*/
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private int progress;
    private long alarmTime;

    /* Function: startTimer()
    * Purpose: Change the state of the timer from not running to running. Determine the ending time
    *          and store it. Set the alarmTime to the ending time.*/
    @Override
    public void startTimer() {
        setmTimerRunning(true);
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        alarmTime = mEndTime;
    }

    /* Function: pauseTimer()
     * Purpose: Change the state of the timer from running to not running. Cancel the object \
     *          CountDowntimer*/
    @Override
    public void pauseTimer() {
        mCountDownTimer.cancel();
        setmTimerRunning(false);
    }

    /* Function: resetTimer()
     * Purpose: Reset the current time until completion to the starting time and the progress towards
     *          completion to 0.*/
    @Override
    public void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        progress = 0;
    }

    /* Function: cancelTimer()
     * Purpose: Cancel the object mCountDownTimer*/
    @Override
    public void cancelTimer() {
        mCountDownTimer.cancel();
    }

    /* Function: setTime()
     * Purpose: Set the starting time of the CountDownTimer and reset the timer to its initial state.
     * Parameter (milliseconds): */
    @Override
    public void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

}
