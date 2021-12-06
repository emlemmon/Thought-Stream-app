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

                if (mTimeLeftInMillis < mStartTimeInMillis * .99 && mTimeLeftInMillis > mStartTimeInMillis * .98) {
                    progress = 1;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .98 && mTimeLeftInMillis > mStartTimeInMillis * .97) {
                    progress = 2;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .97 && mTimeLeftInMillis > mStartTimeInMillis * .96) {
                    progress = 3;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .96 && mTimeLeftInMillis > mStartTimeInMillis * .95) {
                    progress = 4;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .95 && mTimeLeftInMillis > mStartTimeInMillis * .94) {
                    progress = 5;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .94 && mTimeLeftInMillis > mStartTimeInMillis * .93) {
                    progress = 6;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .93 && mTimeLeftInMillis > mStartTimeInMillis * .92) {
                    progress = 7;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .92 && mTimeLeftInMillis > mStartTimeInMillis * .91) {
                    progress = 8;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .91 && mTimeLeftInMillis > mStartTimeInMillis * .90) {
                    progress = 9;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .90 && mTimeLeftInMillis > mStartTimeInMillis * .89) {
                    progress = 10;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .89 && mTimeLeftInMillis > mStartTimeInMillis * .88) {
                    progress = 11;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .88 && mTimeLeftInMillis > mStartTimeInMillis * .87) {
                    progress = 12;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .87 && mTimeLeftInMillis > mStartTimeInMillis * .86) {
                    progress = 13;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .86 && mTimeLeftInMillis > mStartTimeInMillis * .85) {
                    progress = 14;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .85 && mTimeLeftInMillis > mStartTimeInMillis * .84) {
                    progress = 15;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .84 && mTimeLeftInMillis > mStartTimeInMillis * .83) {
                    progress = 16;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .83 && mTimeLeftInMillis > mStartTimeInMillis * .82) {
                    progress = 17;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .82 && mTimeLeftInMillis > mStartTimeInMillis * .81) {
                    progress = 18;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .81 && mTimeLeftInMillis > mStartTimeInMillis * .80) {
                    progress = 19;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .80 && mTimeLeftInMillis > mStartTimeInMillis * .79) {
                    progress = 20;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .79 && mTimeLeftInMillis > mStartTimeInMillis * .78) {
                    progress = 21;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .78 && mTimeLeftInMillis > mStartTimeInMillis * .77) {
                    progress = 22;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .77 && mTimeLeftInMillis > mStartTimeInMillis * .76) {
                    progress = 23;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .76 && mTimeLeftInMillis > mStartTimeInMillis * .75) {
                    progress = 24;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .75 && mTimeLeftInMillis > mStartTimeInMillis * .74) {
                    progress = 25;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .74 && mTimeLeftInMillis > mStartTimeInMillis * .73) {
                    progress = 26;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .73 && mTimeLeftInMillis > mStartTimeInMillis * .72) {
                    progress = 27;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .72 && mTimeLeftInMillis > mStartTimeInMillis * .71) {
                    progress = 28;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .71 && mTimeLeftInMillis > mStartTimeInMillis * .70) {
                    progress = 29;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .70 && mTimeLeftInMillis > mStartTimeInMillis * .69) {
                    progress = 30;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .69 && mTimeLeftInMillis > mStartTimeInMillis * .68) {
                    progress = 31;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .68 && mTimeLeftInMillis > mStartTimeInMillis * .67) {
                    progress = 32;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .67 && mTimeLeftInMillis > mStartTimeInMillis * .66) {
                    progress = 33;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .66 && mTimeLeftInMillis > mStartTimeInMillis * .65) {
                    progress = 34;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .65 && mTimeLeftInMillis > mStartTimeInMillis * .64) {
                    progress = 35;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .64 && mTimeLeftInMillis > mStartTimeInMillis * .63) {
                    progress = 36;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .63 && mTimeLeftInMillis > mStartTimeInMillis * .62) {
                    progress = 37;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .62 && mTimeLeftInMillis > mStartTimeInMillis * .61) {
                    progress = 38;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .61 && mTimeLeftInMillis > mStartTimeInMillis * .60) {
                    progress = 39;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .60 && mTimeLeftInMillis > mStartTimeInMillis * .59) {
                    progress = 40;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .59 && mTimeLeftInMillis > mStartTimeInMillis * .58) {
                    progress = 41;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .58 && mTimeLeftInMillis > mStartTimeInMillis * .57) {
                    progress = 42;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .57 && mTimeLeftInMillis > mStartTimeInMillis * .56) {
                    progress = 43;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .56 && mTimeLeftInMillis > mStartTimeInMillis * .55) {
                    progress = 44;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .55 && mTimeLeftInMillis > mStartTimeInMillis * .54) {
                    progress = 45;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .54 && mTimeLeftInMillis > mStartTimeInMillis * .53) {
                    progress = 46;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .53 && mTimeLeftInMillis > mStartTimeInMillis * .52) {
                    progress = 47;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .52 && mTimeLeftInMillis > mStartTimeInMillis * .51) {
                    progress = 48;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .51 && mTimeLeftInMillis > mStartTimeInMillis * .50) {
                    progress = 49;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .50 && mTimeLeftInMillis > mStartTimeInMillis * .49) {
                    progress = 50;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .49 && mTimeLeftInMillis > mStartTimeInMillis * .48) {
                    progress = 51;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .48 && mTimeLeftInMillis > mStartTimeInMillis * .47) {
                    progress = 52;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .47 && mTimeLeftInMillis > mStartTimeInMillis * .46) {
                    progress = 53;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .46 && mTimeLeftInMillis > mStartTimeInMillis * .45) {
                    progress = 54;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .45 && mTimeLeftInMillis > mStartTimeInMillis * .44) {
                    progress = 55;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .44 && mTimeLeftInMillis > mStartTimeInMillis * .43) {
                    progress = 56;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .43 && mTimeLeftInMillis > mStartTimeInMillis * .42) {
                    progress = 57;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .42 && mTimeLeftInMillis > mStartTimeInMillis * .41) {
                    progress = 58;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .41 && mTimeLeftInMillis > mStartTimeInMillis * .40) {
                    progress = 59;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .40 && mTimeLeftInMillis > mStartTimeInMillis * .39) {
                    progress = 60;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .39 && mTimeLeftInMillis > mStartTimeInMillis * .38) {
                    progress = 61;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .38 && mTimeLeftInMillis > mStartTimeInMillis * .37) {
                    progress = 62;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .37 && mTimeLeftInMillis > mStartTimeInMillis * .36) {
                    progress = 63;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .36 && mTimeLeftInMillis > mStartTimeInMillis * .35) {
                    progress = 64;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .35 && mTimeLeftInMillis > mStartTimeInMillis * .34) {
                    progress = 65;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .34 && mTimeLeftInMillis > mStartTimeInMillis * .33) {
                    progress = 66;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .33 && mTimeLeftInMillis > mStartTimeInMillis * .32) {
                    progress = 67;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .32 && mTimeLeftInMillis > mStartTimeInMillis * .31) {
                    progress = 68;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .31 && mTimeLeftInMillis > mStartTimeInMillis * .30) {
                    progress = 69;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .30 && mTimeLeftInMillis > mStartTimeInMillis * .29) {
                    progress = 70;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .29 && mTimeLeftInMillis > mStartTimeInMillis * .28) {
                    progress = 71;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .28 && mTimeLeftInMillis > mStartTimeInMillis * .27) {
                    progress = 72;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .27 && mTimeLeftInMillis > mStartTimeInMillis * .26) {
                    progress = 73;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .26 && mTimeLeftInMillis > mStartTimeInMillis * .25) {
                    progress = 74;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .25 && mTimeLeftInMillis > mStartTimeInMillis * .24) {
                    progress = 75;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .24 && mTimeLeftInMillis > mStartTimeInMillis * .23) {
                    progress = 76;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .23 && mTimeLeftInMillis > mStartTimeInMillis * .22) {
                    progress = 77;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .22 && mTimeLeftInMillis > mStartTimeInMillis * .21) {
                    progress = 78;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .21 && mTimeLeftInMillis > mStartTimeInMillis * .20) {
                    progress = 79;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .20 && mTimeLeftInMillis > mStartTimeInMillis * .19) {
                    progress = 80;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .19 && mTimeLeftInMillis > mStartTimeInMillis * .18) {
                    progress = 81;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .18 && mTimeLeftInMillis > mStartTimeInMillis * .17) {
                    progress = 82;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .17 && mTimeLeftInMillis > mStartTimeInMillis * .16) {
                    progress = 83;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .16 && mTimeLeftInMillis > mStartTimeInMillis * .15) {
                    progress = 84;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .15 && mTimeLeftInMillis > mStartTimeInMillis * .14) {
                    progress = 85;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .14 && mTimeLeftInMillis > mStartTimeInMillis * .13) {
                    progress = 86;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .13 && mTimeLeftInMillis > mStartTimeInMillis * .12) {
                    progress = 87;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .12 && mTimeLeftInMillis > mStartTimeInMillis * .11) {
                    progress = 88;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .11 && mTimeLeftInMillis > mStartTimeInMillis * .10) {
                    progress = 89;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .10 && mTimeLeftInMillis > mStartTimeInMillis * .09) {
                    progress = 90;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .09 && mTimeLeftInMillis > mStartTimeInMillis * .08) {
                    progress = 91;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .08 && mTimeLeftInMillis > mStartTimeInMillis * .07) {
                    progress = 92;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .07 && mTimeLeftInMillis > mStartTimeInMillis * .06) {
                    progress = 93;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .06 && mTimeLeftInMillis > mStartTimeInMillis * .05) {
                    progress = 94;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .05 && mTimeLeftInMillis > mStartTimeInMillis * .04) {
                    progress = 95;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .04 && mTimeLeftInMillis > mStartTimeInMillis * .03) {
                    progress = 96;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .03 && mTimeLeftInMillis > mStartTimeInMillis * .02) {
                    progress = 97;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .02 && mTimeLeftInMillis > mStartTimeInMillis * .01) {
                    progress = 98;
                } else if (mTimeLeftInMillis < mStartTimeInMillis * .01 && mTimeLeftInMillis > mStartTimeInMillis * .0) {
                    progress = 99;
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