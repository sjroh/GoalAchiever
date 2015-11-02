package roh.sijine.goalachiever;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SetupTimerActivity extends AppCompatActivity {

    final private int maxHour = 5;
    final private int maxMin = 59;
    private Context mContext;
    private NumberPicker hourPicker;
    private NumberPicker minPicker;
    private int hourPicked;
    private int minPicked;
    private TextView timeNotice;
    private FlatButton btnStart;
    private FlatButton btnRoulette;
    private FlatButton btnCollection;

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

        // setup buttons
        btnStart = (FlatButton) findViewById(R.id.btn_start);
        btnRoulette = (FlatButton) findViewById(R.id.btn_roulette);
        btnCollection = (FlatButton) findViewById(R.id.btn_collection);

    }

    private void changeTimeNotice(int hour, int min) {
        String message = "";
        if (hour == 0 && min == 0) {
            message = mContext.getResources().getString(R.string.no_input_timer);
        } else {
            Calendar future = Calendar.getInstance();//(Calendar) now.clone();
            SimpleDateFormat format = new SimpleDateFormat("h:mm a");
            future.add(Calendar.HOUR_OF_DAY, hour);
            future.add(Calendar.MINUTE, min);
            message += mContext.getResources().getString(R.string.until) + " ";
            message += format.format(future.getTime());
        }
        timeNotice.setText(message);
    }

}
