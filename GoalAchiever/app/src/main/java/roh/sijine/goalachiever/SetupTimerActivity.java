package roh.sijine.goalachiever;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SetupTimerActivity extends AppCompatActivity {

//    private SharedPreferences preferences;

    final private int maxHour = 5;
    final private int maxMin = 59;
    private Context mContext;
    private NumberPicker hourPicker;
    private NumberPicker minPicker;
    private int hourPicked;
    private int minPicked;

    private TextView timeNotice;
    private TextView textCoin;

    private boolean canGo = false;

    private FButton btnStart;
    private FButton btnRoulette;
    private FButton btnCollection;

    private Calendar future;
    private ScoreHandler sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_setup_timer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // setup number pickers
        timeNotice = (TextView) findViewById(R.id.text_time_notice);
        hourPicker = (NumberPicker) findViewById(R.id.hour_picker);
        minPicker = (NumberPicker) findViewById(R.id.min_picker);

        hourPicker.setMaxValue(maxHour);
        minPicker.setMaxValue(maxMin);

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked = newVal;
                changeTimeNotice(hourPicked, minPicked);
            }
        });
        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked = newVal;
                changeTimeNotice(hourPicked, minPicked);
            }
        });

        // setup text coin
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        // setup buttons
        btnStart = (FButton) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canGo) {
                    Intent intent = new Intent(mContext, WatcherActivity.class);
                    intent.putExtra("hourPicked", hourPicked);
                    intent.putExtra("minPicked", minPicked);
                    intent.putExtra("milliPicked", 60000 * (hourPicked * 60 + minPicked));
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.no_input_timer), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRoulette = (FButton) findViewById(R.id.btn_roulette);
        btnRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move on to roulette
            }
        });

        btnCollection = (FButton) findViewById(R.id.btn_collection);
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move on to collection
            }
        });

    }

    private void changeTimeNotice(int hour, int min) {
        String message = "";
        if (hour == 0 && min == 0) {
            message = mContext.getResources().getString(R.string.no_input_timer);
            canGo = false;
        } else {
            future = Calendar.getInstance();//(Calendar) now.clone();
            SimpleDateFormat format = new SimpleDateFormat("h:mm a");
            future.add(Calendar.HOUR_OF_DAY, hour);
            future.add(Calendar.MINUTE, min);

            // print out "Until TIME"
//            message += mContext.getResources().getString(R.string.until) + " ";
//            message += format.format(future.getTime());

            // print out "TIME Later"
            if (hourPicked == 1) {
                message += hourPicked + " " + mContext.getResources().getString(R.string.hour) + " ";
            } else if (hourPicked > 1) {
                message += hourPicked + " " + mContext.getResources().getString(R.string.hours) + " ";
            }
            if (minPicked == 1) {
                message += minPicked + " " + mContext.getResources().getString(R.string.minute) + " ";
            } else if (minPicked > 1) {
                message += minPicked + " " + mContext.getResources().getString(R.string.minutes) + " ";
            }
            message += mContext.getResources().getString(R.string.later);

            canGo = true;
        }
        timeNotice.setText(message);
    }

}
