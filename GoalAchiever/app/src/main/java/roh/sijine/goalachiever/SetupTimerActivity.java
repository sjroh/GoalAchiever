package roh.sijine.goalachiever;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;


public class SetupTimerActivity extends AppCompatActivity {

    final private int maxHour = 5;
    final private int maxMin = 1;
    final private int timesMin = 30;
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

        setContentView(R.layout.activity_setup_timer);

        mContext = this.getApplicationContext();

        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fbutton_color_midnight_blue)));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.layout_actionbar, null);
        ((RelativeLayout) v).setGravity(Gravity.RIGHT);
        actionBar.setCustomView(v);
        actionBar.setDisplayShowCustomEnabled(true);


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
        String[] stringArray = new String[2];
        stringArray[0] = "00";
        stringArray[1] = "30";
        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked = newVal;
                changeTimeNotice(hourPicked, minPicked);
            }
        });
        minPicker.setDisplayedValues(stringArray);

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
                    intent.putExtra("minPicked", minPicked * timesMin);
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
                Intent intent = new Intent(mContext, RouletteActivity.class);
                startActivity(intent);
            }
        });

        btnCollection = (FButton) findViewById(R.id.btn_collection);
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move on to collection
                Intent intent = new Intent(mContext, CollectionActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));
        hourPicker.setValue(0);
        minPicker.setValue(0);
        hourPicked = 0;
        minPicked = 0;
        timeNotice.setText(mContext.getResources().getString(R.string.no_input_timer));
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
            if (hourPicked > 0 && minPicked > 0) {
                message += "\n";
            }
            if (minPicked == 1) {
                message += (minPicked * timesMin) + " " + mContext.getResources().getString(R.string.minute) + " ";
            }
//            if (minPicked == 1) {
//                message += minPicked + " " + mContext.getResources().getString(R.string.minute) + " ";
//            } else if (minPicked > 1) {
//                message += minPicked + " " + mContext.getResources().getString(R.string.minutes) + " ";
//            }
            message += mContext.getResources().getString(R.string.later);

            canGo = true;
        }
        timeNotice.setText(message);
    }

}
