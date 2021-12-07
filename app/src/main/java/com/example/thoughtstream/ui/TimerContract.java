package com.example.thoughtstream.ui;

import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import java.util.Calendar;

public interface TimerContract {

    interface View {
        void updateCountDownText(long mTimeLeftInMillis, int progress);
        void updateWatchInterface(long mTimeLeftInMillis, boolean mTimerRunning, long mStartTimeInMillis);
        void startAlarm(long alarmTime);
        void cancelAlarm();
    }
    interface Presenter {
        void onStartButtonClick();
        void onPauseButtonClick();
        void onResetButtonClick();
        void onSetButtonClick(long millisInput);
        void stop(SharedPreferences prefs);
        void start(SharedPreferences prefs);
    }
    interface Model {
        void startTimer();
        void pauseTimer();
        void resetTimer();
        void cancelTimer();
        void setTime(long milliseconds);
        long getmStartTimeInMillis();
        long getmTimeLeftInMillis();
        boolean getmTimerRunning();
        long getmEndTime();
        CountDownTimer getmCountDownTimer();

        void setmStartTimeInMillis(long startTimeInMillis);

        void setmTimeLeftInMillis(long millisLeft);

        void setmTimerRunning(boolean timerRunning);

        void setmEndTime(long endTime);

        int getProgress();

        long getAlarmTime();

        void setmCountDownTimer(CountDownTimer mCountDownTimer);

        int setProgress(int i);
    }
}
