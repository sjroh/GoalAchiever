package roh.sijine.goalachiever;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private Context mContext;

    private SharedPreferences preferences;
    private SharedPreferences listPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_intro);

        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        listPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        // setup basic values
        int coin = preferences.getInt("COIN", 0);
        List<Integer> giftCards = new ArrayList<Integer>();
        giftCards.add(preferences.getInt("GC1", 0));
        giftCards.add(preferences.getInt("GC2", 0));
        giftCards.add(preferences.getInt("GC3", 0));
        giftCards.add(preferences.getInt("GC4", 0));
        giftCards.add(preferences.getInt("GC5", 0));
//        int maxPiece = preferences.getInt("MAXPIECE", 6);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ProgressBar appLoading = (ProgressBar) findViewById(R.id.app_loading);

        // start dummy loading
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // start Setup Timer Activity after dummy loading
                Intent intent = new Intent(mContext, SetupTimerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);



    }

}
