package roh.sijine.goalachiever;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import info.hoang8f.widget.FButton;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WatcherActivity extends AppCompatActivity {

    private WatcherActivity.UnlockedReceiver unlockedReceiver;
    private TextView fullscreenTextview;
    private int unlockCounter;
    private Context mContext;
    private FButton btnStop;

    private int hourPicked;
    private int minPicked;
    private int milliPicked;
    private Calendar future;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);

//        future = (Calendar) savedInstanceState.get("future");
        Intent intent = getIntent();
        hourPicked = intent.getIntExtra("hourPicked", 0);
        minPicked = intent.getIntExtra("minPicked", 0);
        milliPicked = intent.getIntExtra("milliPicked", 0);


        mContext = this.getApplicationContext();

        btnStop = (FButton) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultActivity.class);
                intent.putExtra("RESULT", "FROM STOP");
                startActivity(intent);
                finish();
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
    protected void onDestroy() {
        unregisterReceiver(unlockedReceiver);
        super.onDestroy();
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

    public void updateCounter() {
        unlockCounter ++;
        Log.d("[WATCH_ACTIVITY]", "### count = " + unlockCounter);

        // for test purpose
        fullscreenTextview.setText(String.valueOf(unlockCounter));

        // if unlock counter is greater than min unlock

        // else keep going

    }
}

