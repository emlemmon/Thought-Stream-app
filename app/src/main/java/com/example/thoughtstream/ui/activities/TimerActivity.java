package com.example.thoughtstream.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class TimerActivity extends AppCompatActivity {
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        
        mButtonSet = findViewById(R.id.button_set);
        mButtonStart = findViewById(R.id.button_start);
        mButtonPause = findViewById(R.id.button_pause);
        mButtonReset = findViewById(R.id.button_reset);
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

        hoursPicker.setOnValueChangedListener((picker, oldVal, newVal) -> hoursInMillis.set(((long) newVal * 60) * 60000));

        minutesPicker.setOnValueChangedListener((picker, oldVal, newVal) -> minutesInMillies.set((long) newVal * 60000));

        secondsPicker.setOnValueChangedListener((picker, oldVal, newVal) -> secondsInMillies.set((long) newVal * 1000));

        mButtonSet.setOnClickListener(v -> {
            long millisInput =  hoursInMillis.get() + minutesInMillies.get() + secondsInMillies.get();

            setTime(millisInput);
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
        closeKeyboard();
    }

    private void startAlarm(Calendar alarmTime) {
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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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


//        package com.example.thoughtstream;
//
//        import android.animation.ObjectAnimator;
//        <<<<<<<< HEAD:app/src/main/java/com/example/thoughtstream/TimerActivity.java
//        import android.os.Bundle;
//        import android.os.Handler;
//        ========
//        import android.content.Intent;
//        import android.content.res.Resources;
//        import android.graphics.drawable.Drawable;
//        import android.os.Handler;
//        import android.os.Bundle;
//        import android.view.View;
//        >>>>>>>> a9468863f67759a1629c2e1b00a04ff7a170ea59:app/src/main/java/com/example/progressbarpractice/MainActivity.java
//        import android.view.animation.DecelerateInterpolator;
//        import android.widget.Button;
//        import android.widget.ProgressBar;
//        import android.widget.TextView;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//public class TimerActivity extends AppCompatActivity {
//
//    private final Handler handler = new Handler();
//
//    TextView tv;
//
//<<<<<<<< HEAD:app/src/main/java/com/example/thoughtstream/TimerActivity.java
//========
//    private Button TESTINGBUTTON;
//
//>>>>>>>> a9468863f67759a1629c2e1b00a04ff7a170ea59:app/src/main/java/com/example/progressbarpractice/MainActivity.java
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timer);
//        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
//        mProgress.setProgress(0);   // Main Progress
//        mProgress.setSecondaryProgress(100); // Secondary Progress
//        mProgress.setMax(100); // Maximum Progress
//        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
//        animation.setDuration(50000);
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.start();
//        tv = (TextView) findViewById(R.id.tv);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (mProgress.getProgress() <= 100) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            tv.setText(mProgress.getProgress() + "%");
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        // Just to display the progress slowly
//                        Thread.sleep(16); //thread will take approx 3 seconds to finish
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
//        TESTINGBUTTON = findViewById(R.id.button_send);
//        TESTINGBUTTON.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goTotimer(v);
//            }
//        });
//
//    }
//
//    public void goTotimer (View view) {
//        Intent intent = new Intent(this, TimerActivity.class);
//        startActivity(intent);
//    }
//}

