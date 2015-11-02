package roh.sijine.goalachiever;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.cengalabs.flatui.FlatUI;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IntroActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_intro);

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
