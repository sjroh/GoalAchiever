package roh.sijine.goalachiever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import info.hoang8f.widget.FButton;

public class WatcherActivity extends AppCompatActivity {

    final private int timesMin = 30;
    private WatcherActivity.UnlockedReceiver unlockedReceiver;
    private TextView fullscreenTextview;
    private int unlockCounter;
    private Context mContext;
    private FButton btnStop;
    private int hourPicked;
    private int minPicked;
    private int milliPicked;
    private int totalMin;
    private int maxUnlock;
    private int timeLeft;
    private CountDownTimer timerCountDown;
    private CountDownTimer timerUnlockTime;
    private int maxUnlockTime;
    private int maxUnlockTimeLeft;

    private HomeWatcher mHomeWatcher;

    private ScoreHandler sh;
    private TextView textCoin;

    private TextView textCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watcher);

        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fbutton_color_midnight_blue)));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_blue)));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.layout_actionbar, null);
        ((RelativeLayout) v).setGravity(Gravity.RIGHT);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);


        Intent intent = getIntent();
        hourPicked = intent.getIntExtra("hourPicked", 0);
        minPicked = intent.getIntExtra("minPicked", 0);
        totalMin = hourPicked * 60 + minPicked;

        Log.d("[WATCHER]", "### hour picked = " + hourPicked);
        Log.d("[WATCHER]", "### min picked = " + minPicked);
        Log.d("[WATCHER]", "### total min = " + totalMin);

        maxUnlock = totalMin / 30 + 1;

        milliPicked = intent.getIntExtra("milliPicked", 0);
        timeLeft = milliPicked;

        mContext = this.getApplicationContext();

        // setup text coin
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        textCountdown = (TextView) findViewById(R.id.text_countdown);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                callResultActivity(false, "FROM HOME BUTTON STOP");
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

        timerCountDown = new CountDownTimer(milliPicked, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft -= 1;
            }

            @Override
            public void onFinish() {
                callResultActivity(true, "FROM TIMER");
            }
        }.start();

        btnStop = (FButton) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerCountDown.cancel();
                callResultActivity(false, "FROM STOP");
//                callResultActivity(true, "FROM STOP");
            }
        });

        unlockedReceiver = new WatcherActivity.UnlockedReceiver();
        fullscreenTextview = (TextView) findViewById(R.id.fullscreen_content);
        unlockCounter = 0;

        registerReceiver(unlockedReceiver, new IntentFilter("android.intent.action.USER_PRESENT"));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // will prevent back button
            Toast.makeText(mContext, "You should stop your timer first.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start timer 30 sec and keep changing value
        maxUnlockTime = 30000;

        // for demo purpose
        maxUnlockTime = 10000;

        maxUnlockTimeLeft = maxUnlockTime;
        timerUnlockTime = new CountDownTimer(maxUnlockTime, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (maxUnlockTimeLeft % 1000 == 0) {
                    int timeLeft = maxUnlockTimeLeft / 1000;
                    if (timeLeft > 0) {
                        textCountdown.setText("(in " + timeLeft + " sec.)");
                    } else {
                        textCountdown.setText("(Uh..oh...)");
                    }

                }

                maxUnlockTimeLeft -= 1;
            }

            @Override
            public void onFinish() {
                callResultActivity(false, "FROM EXCEED MAX UNLOCK SEC STOP");
            }
        };
        timerUnlockTime.start();
        Log.d("[WATCHER_ACTIVITY]", "### onResume timer started");
//        updateCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop the timer
        timerUnlockTime.cancel();
        Log.d("[WATCHER_ACTIVITY]", "### onPause timer canceled");
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(unlockedReceiver);
        Log.d("[WATCHER_ACTIVITY]", "### onDestroy");
        timerUnlockTime.cancel();
        timerCountDown.cancel();
        super.onDestroy();
    }

    private void callResultActivity(boolean status, String message) {
        Intent intent = new Intent(mContext, ResultActivity.class);
        intent.putExtra("hourPicked", hourPicked);
        intent.putExtra("minPicked", minPicked * timesMin);
//        intent.putExtra("milliPicked", 60000 * (hourPicked * 60 + minPicked));

        // for demo
        intent.putExtra("milliPicked", 1000 * (hourPicked * 60 + minPicked * 30));

        intent.putExtra("STATUS", status);
        intent.putExtra("RESULT", message);
        if (status) {
            intent.putExtra("maxUnlock", maxUnlock);
            intent.putExtra("unlockCounter", unlockCounter);
        }
        startActivity(intent);
        finish();
    }

    public void updateCounter() {
        unlockCounter++;
        Log.d("[WATCH_ACTIVITY]", "### count = " + unlockCounter);

        int unlockLeft = maxUnlock - unlockCounter;
        if (unlockLeft > 1) {
            fullscreenTextview.setText(String.valueOf(unlockLeft) + " times of unlock left");
        } else if (unlockLeft == 1) {
            fullscreenTextview.setText(String.valueOf(unlockLeft) + " time of unlock left");
        } else if (unlockLeft == 0) {
            fullscreenTextview.setText("This would be your last chance");
        } else {
            fullscreenTextview.setVisibility(View.INVISIBLE);
            textCountdown.setVisibility(View.INVISIBLE);
        }


        // if unlock counter is greater than min unlock
        if (unlockCounter > maxUnlock) {
            callResultActivity(false, "FROM FORCE STOP");
        }
        // else keep going

    }

    private class UnlockedReceiver extends BroadcastReceiver {
        // The reason that I put this BroadcastReceiver in this activity is
        // I couldn't find the way to call updateCounter function or capture onReceive
        // from outside of the class.
        public void onReceive(Context ctx, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                updateCounter();
            }
        }
    }
}

