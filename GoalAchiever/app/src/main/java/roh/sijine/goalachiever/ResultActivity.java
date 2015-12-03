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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import info.hoang8f.widget.FButton;

public class ResultActivity extends AppCompatActivity {

    private Context mContext;

    private TextView textMessage;
    private TextView textRewardCoin;
    private LinearLayout layoutRewardCoin;

    private String message;
    private boolean status;

    private FButton btnBack;
    private FButton btnReplay;

    private ArrayList<String> strSuccess;
    private ArrayList<String> strFail;

    private int hourPicked;
    private int minPicked;
    private int milliPicked;

    private ScoreHandler sh;
    private TextView textCoin;

    private void setVariables() {
        strSuccess = new ArrayList<String>();
        strSuccess.add(getResources().getString(R.string.result_succeed_1));
        strSuccess.add(getResources().getString(R.string.result_succeed_2));
        strSuccess.add(getResources().getString(R.string.result_succeed_3));

        strFail = new ArrayList<String>();
        strFail.add(getResources().getString(R.string.result_fail_1));
        strFail.add(getResources().getString(R.string.result_fail_2));
        strFail.add(getResources().getString(R.string.result_fail_3));
        strFail.add(getResources().getString(R.string.result_fail_4));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

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

        // setup text coin
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        hourPicked = intent.getIntExtra("hourPicked", 0);
        minPicked = intent.getIntExtra("minPicked", 0);
        milliPicked = intent.getIntExtra("milliPicked", 0);

        setVariables();

        layoutRewardCoin = (LinearLayout) findViewById(R.id.layout_result_coin);

        status = intent.getBooleanExtra("STATUS", false);
        message = intent.getStringExtra("RESULT");

        textMessage = (TextView) findViewById(R.id.text_message);
        textRewardCoin = (TextView) findViewById(R.id.text_reward_coin);

        btnBack = (FButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReplay = (FButton) findViewById(R.id.btn_replay);
        btnReplay.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(mContext, WatcherActivity.class);
                                             intent.putExtra("hourPicked", hourPicked);
                                             intent.putExtra("minPicked", minPicked);
                                             intent.putExtra("milliPicked", milliPicked);
                                             startActivity(intent);
                                             finish();
                                         }
                                     }
        );

        if (status) {
            // Succeed
            int maxUnlock = intent.getIntExtra("maxUnlock", 0);
            int unlockCounter = intent.getIntExtra("unlockCounter", 0);

            // calculate the coin
            int reward = (maxUnlock - unlockCounter);
            new ScoreHandler(mContext).addCoin(reward);

            int strId = new Random().nextInt(strSuccess.size());

            textMessage.setText(strSuccess.get(strId));
            textRewardCoin.setText(String.valueOf(reward));
//            mContentView.setText("You earned " + (maxUnlock - unlockCounter));
        } else {
            // Failed
            int strId = new Random().nextInt(strFail.size());
            layoutRewardCoin.setVisibility(View.INVISIBLE);
            textMessage.setText(strFail.get(strId));
        }

    }

}
