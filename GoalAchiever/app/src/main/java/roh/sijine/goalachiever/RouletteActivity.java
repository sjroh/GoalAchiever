package roh.sijine.goalachiever;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class RouletteActivity extends AppCompatActivity {

    private static final float rotate_from = 0.0f;
    private static final float rotate_to = -10.0f * 360.0f;
    private Context mContext;
    private ScoreHandler sh;
    private TextView textCoin;
    private ImageView roulette;
    private FButton btnSpin;
    /*  README
    *       READ ScoreHandler
    *       You can add coins by using sh.addCoin(int)
    *       Whenever you add coins, refresh textCoin using setText function and sh.getCoin function
    *       You can add  gift card piece by using sh.addGiftCardPiece(int)
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_roulette);

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

        // setup text coin
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        // should change to spin wheel
//        roulette = (ImageView) findViewById(R.id.roulette);
//        roulette.setImageResource(R.drawable.roulette);

        btnSpin = (FButton) findViewById(R.id.btn_spin);
        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation r;
                r = new RotateAnimation(rotate_from, rotate_to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                r.setDuration((long) 2*1500);
                r.setRepeatCount(0);
                roulette.startAnimation(r);
            }
        });
    }

}
