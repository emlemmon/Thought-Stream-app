package com.example.thoughtstream.ui;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;


public class TimerPresenter implements TimerContract.Presenter {

    private final TimerContract.View timerView;
    private final TimerContract.Model timerModel;

    public TimerPresenter(TimerContract.View view, TimerContract.Model model) {
        this.timerView = view;
        this.timerModel = model;
    }

    @Override
    public void onStartButtonClick() {
        if (!timerModel.getmTimerRunning()) {
                startTimer();
                timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
                timerView.startAlarm(timerModel.getAlarmTime());
        }
    }
    
    private void startTimer() {
        timerModel.startTimer();

        long timeLeft = timerModel.getmTimeLeftInMillis();


        timerModel.setmCountDownTimer(new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerModel.setmTimeLeftInMillis(millisUntilFinished);
                long mTimeLeftInMillis = timerModel.getmTimeLeftInMillis();
                long mStartTimeInMillis = timerModel.getmStartTimeInMillis();
                int progress = timerModel.getProgress();

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

                timerModel.setProgress(progress);
                timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
            }

            @Override
            public void onFinish() {
                timerModel.setmTimerRunning(false);
                timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
            }
        }.start());
            
        timerModel.setmTimerRunning(true);
        timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
    }

    @Override
    public void onPauseButtonClick() {
        if (timerModel.getmTimerRunning()) {
            timerModel.pauseTimer();
            timerView.cancelAlarm();
            timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
        }
    }

    @Override
    public void onResetButtonClick() {
        timerModel.resetTimer();
        timerView.cancelAlarm();
        timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
        timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
    }

    @Override
    public void onSetButtonClick(long millisInput) {
        timerModel.setTime(millisInput);
        timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
        timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
    }

    @Override
    public void stop(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", timerModel.getmStartTimeInMillis());
        editor.putLong("millisLeft",timerModel.getmTimeLeftInMillis());
        editor.putBoolean("timerRunning", timerModel.getmTimerRunning());
        editor.putLong("endTime", timerModel.getmEndTime());

        editor.apply();
        CountDownTimer countDownTimer = timerModel.getmCountDownTimer();
        if (countDownTimer != null) {
            timerModel.cancelTimer();
        }
    }

    @Override
    public void start(SharedPreferences prefs) {

        timerModel.setmStartTimeInMillis(prefs.getLong("startTimeInMillis", 600000));
        timerModel.setmTimeLeftInMillis(prefs.getLong("millisLeft", timerModel.getmStartTimeInMillis()));
        timerModel.setmTimerRunning(prefs.getBoolean("timerRunning", false));

        timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
        timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());

        if (timerModel.getmTimerRunning()) {
            timerModel.setmEndTime(prefs.getLong("endTime", 0));
            timerModel.setmTimeLeftInMillis(timerModel.getmEndTime() - System.currentTimeMillis());
            timerModel.startTimer();

            if (timerModel.getmTimeLeftInMillis() < 0) {
                timerModel.setmTimeLeftInMillis(0);
                timerModel.setmTimerRunning(false);
                timerView.updateCountDownText(timerModel.getmTimeLeftInMillis(), timerModel.getProgress());
                timerView.updateWatchInterface(timerModel.getmTimeLeftInMillis(), timerModel.getmTimerRunning(), timerModel.getmStartTimeInMillis());
            } else {
                timerModel.startTimer();
            }
        }
    }
}
