package com.example.thoughtstream;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStart = findViewById(R.id.button_start);
        mButtonPause = findViewById(R.id.button_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTimerRunning) {
                    startTimer();
                }
            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning) {
                    pauseTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonPause.setVisibility(View.INVISIBLE);
                resetTimer();
            }
        });
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();
        mTimerRunning = true;
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonPause.setVisibility(View.VISIBLE);
        } else {
            mButtonStart.setVisibility(View.VISIBLE);
            if (mTimeLeftInMillis < 1000) {
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStart.setVisibility(View.VISIBLE);
                mButtonPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
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
        editor.putLong("millisLeft",mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        mCountDownTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
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

