package com.example.thoughtstream.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.TimerContract;
import com.example.thoughtstream.ui.TimerPresenter;
import com.example.thoughtstream.utils.AlertReceiver;
import com.example.thoughtstream.utils.TimerModel;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class TimerActivity extends AppCompatActivity implements TimerContract.View{
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;
    public ProgressBar progressBar;

    private TimerPresenter timerPresenter;
    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerPresenter = new TimerPresenter(this, new TimerModel());

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
        AtomicLong minutesInMillis = new AtomicLong();
        AtomicLong secondsInMillis = new AtomicLong();

        hoursPicker.setOnValueChangedListener((picker, oldVal, newVal) -> hoursInMillis.set(((long) newVal * 60) * 60000));

        minutesPicker.setOnValueChangedListener((picker, oldVal, newVal) -> minutesInMillis.set((long) newVal * 60000));

        secondsPicker.setOnValueChangedListener((picker, oldVal, newVal) -> secondsInMillis.set((long) newVal * 1000));

        mButtonSet.setOnClickListener(v -> {
            if (hoursInMillis.get() == 0 && minutesInMillis.get() == 0 && secondsInMillis.get() == 0) {
                Toast.makeText(TimerActivity.this, "Cannot begin a blank timer", Toast.LENGTH_SHORT).show();
            } else {
                long millisInput =  hoursInMillis.get() + minutesInMillis.get() + secondsInMillis.get();
                timerPresenter.onSetButtonClick(millisInput);
            }
        });
        
        mButtonStart.setOnClickListener(v -> timerPresenter.onStartButtonClick());
        mButtonPause.setOnClickListener(v -> timerPresenter.onPauseButtonClick());
        mButtonReset.setOnClickListener(v -> timerPresenter.onResetButtonClick());
    }

    public void updateCountDownText(long mTimeLeftInMillis, int progress) {
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
        progressBar.setProgress(progress);
    }

    public void updateWatchInterface(long mTimeLeftInMillis, boolean mTimerRunning, long mStartTimeInMillis) {
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

    public void startAlarm(long alarmTime) {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, intent, 0);

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    public void cancelAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        mAlarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timerPresenter.stop(prefs);
    }


    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timerPresenter.start(prefs);
    }
}