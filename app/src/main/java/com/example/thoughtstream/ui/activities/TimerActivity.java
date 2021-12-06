package com.example.thoughtstream.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.utils.AlertReceiver;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class TimerActivity extends AppCompatActivity {
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;
    private ProgressBar progressBar;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private int progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        
        mButtonSet = findViewById(R.id.button_set);
        mButtonStart = findViewById(R.id.button_start);
        mButtonPause = findViewById(R.id.button_pause);
        mButtonReset = findViewById(R.id.button_reset);
        progressBar = findViewById(R.id.progress_bar);
        NumberPicker hoursPicker = findViewById(R.id.hoursPicker);
        NumberPicker minutesPicker = findViewById(R.id.minutesPicker);
        NumberPicker secondsPicker = findViewById(R.id.secondsPicker);

        hoursPicker.setMinValue(0);
        minutesPicker.setMinValue(0);
        secondsPicker.setMinValue(0);
        hoursPicker.setMaxValue(24);
        minutesPicker.setMaxValue(59);
        secondsPicker.setMaxValue(59);
        AtomicLong hoursInMillis = new AtomicLong();
        AtomicLong minutesInMillies = new AtomicLong();
        AtomicLong secondsInMillies = new AtomicLong();
        progress = 0;

        hoursPicker.setOnValueChangedListener((picker, oldVal, newVal) -> hoursInMillis.set(((long) newVal * 60) * 60000));

        minutesPicker.setOnValueChangedListener((picker, oldVal, newVal) -> minutesInMillies.set((long) newVal * 60000));

        secondsPicker.setOnValueChangedListener((picker, oldVal, newVal) -> secondsInMillies.set((long) newVal * 1000));

        mButtonSet.setOnClickListener(v -> {
            if (hoursInMillis.get() == 0 && minutesInMillies.get() == 0 && secondsInMillies.get() == 0) {
                Toast.makeText(TimerActivity.this, "Cannot begin a blank timer", Toast.LENGTH_SHORT).show();
            } else {
                long millisInput =  hoursInMillis.get() + minutesInMillies.get() + secondsInMillies.get();
                setTime(millisInput);
            }
        });
        
        mButtonStart.setOnClickListener(v -> {
            if(!mTimerRunning) {
                startTimer();
            }
        });
        mButtonPause.setOnClickListener(v -> {
            if(mTimerRunning) {
                pauseTimer();
            }
        });
        mButtonReset.setOnClickListener(v -> {
            updateWatchInterface();
            resetTimer();
        });
    }
    
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startAlarm(@NonNull Calendar alarmTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (alarmTime.before(Calendar.getInstance())) {
            alarmTime.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        int hours = (int) (mEndTime / 1000) / 3600;
        int minutes = (int) ((mEndTime / 1000) % 3600) / 60;
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hours);
        alarmTime.set(Calendar.MINUTE, minutes);
        alarmTime.set(Calendar.SECOND, 0);
        startAlarm(alarmTime);

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

                if (mTimeLeftInMillis < mStartTimeInMillis * .9 && mTimeLeftInMillis > mStartTimeInMillis * .8) {
                    progress = 10;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .8 && mTimeLeftInMillis > mStartTimeInMillis * .7) {
                    progress = 20;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .7 && mTimeLeftInMillis > mStartTimeInMillis * .6) {
                    progress = 30;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .6 && mTimeLeftInMillis > mStartTimeInMillis * .5) {
                    progress = 40;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .5 && mTimeLeftInMillis > mStartTimeInMillis * .4) {
                    progress = 50;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .4 && mTimeLeftInMillis > mStartTimeInMillis * .3) {
                    progress = 60;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .3 && mTimeLeftInMillis > mStartTimeInMillis * .2) {
                    progress = 70;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .2 && mTimeLeftInMillis > mStartTimeInMillis * .1) {
                    progress = 80;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .1 && mTimeLeftInMillis > 0) {
                    progress = 90;
                }

                if ( mTimeLeftInMillis < 1000) {
                    progress = 100;
                }

                progressBar.setProgress(progress);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        cancelAlarm();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        cancelAlarm();
        progress = 0;
        progressBar.setProgress(progress);
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStart.setVisibility(View.INVISIBLE);
            mButtonPause.setVisibility(View.VISIBLE);
        } else {
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStart.setVisibility(View.VISIBLE);
            mButtonPause.setVisibility(View.INVISIBLE);
            if (mTimeLeftInMillis < 1000) {
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStart.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft",mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }

    }
}