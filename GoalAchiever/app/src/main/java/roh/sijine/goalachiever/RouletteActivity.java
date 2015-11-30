package roh.sijine.goalachiever;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import info.hoang8f.widget.FButton;

public class RouletteActivity extends AppCompatActivity {

    final static int price = 100;

    private Context mContext;
    private ScoreHandler sh;
    private TextView textCoin;
    private FButton btnSpin;
    //    private ImageView boxFront;
    private ImageView boxBack;
    private ImageView boxFront;

    private LinearLayout rewardCoin;
    private LinearLayout rewardGiftcard;
    private TextView rewardTextCoin;
    private ImageView rewardImageGiftcard;

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

        // rewards
        rewardCoin = (LinearLayout) findViewById(R.id.layout_reward_coin);
        rewardGiftcard = (LinearLayout) findViewById(R.id.layout_reward_giftcard);
        rewardTextCoin = (TextView) findViewById(R.id.text_reward_coin);
        rewardImageGiftcard = (ImageView) findViewById(R.id.image_reward_giftcard);
        rewardCoin.setVisibility(View.INVISIBLE);
        rewardGiftcard.setVisibility(View.INVISIBLE);

        // box
        boxBack = (ImageView) findViewById(R.id.image_box_back);
        boxFront = (ImageView) findViewById(R.id.image_box_front);

        boxBack.setVisibility(View.INVISIBLE);

        btnSpin = (FButton) findViewById(R.id.btn_spin);
        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRewardPosition();
                sh.removeCoin(price);
                textCoin.setText(String.valueOf(sh.getCoin()));
                startAnimation();
            }
        });
    }

    private void resetRewardPosition() {
        boxFront.setImageDrawable(getResources().getDrawable(R.drawable.box_closed));
        boxFront.setVisibility(View.VISIBLE);
        boxBack.setVisibility(View.INVISIBLE);
        rewardCoin.setVisibility(View.INVISIBLE);
        rewardGiftcard.setVisibility(View.INVISIBLE);
        rewardCoin.clearAnimation();
        rewardGiftcard.clearAnimation();
    }

    private void startAnimation() {
        TranslateAnimation translation;
        translation = new TranslateAnimation(0f, 0f, -100f, 0f);
        translation.setStartOffset(500);
        translation.setDuration(2000);
        translation.setFillAfter(true);
        translation.setFillEnabled(true);
        translation.setInterpolator(new BounceInterpolator());
        translation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btnSpin.setVisibility(View.INVISIBLE);
                rewardCoin.setVisibility(View.INVISIBLE);
                rewardGiftcard.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boxBack.setVisibility(View.VISIBLE);
                boxFront.setImageDrawable(getResources().getDrawable(R.drawable.box_front));
                rewardGenerator();
            }
        });
        boxFront.startAnimation(translation);
    }

    private void rewardGenerator() {
        final int max = 100;

        int[][] rewardArray = new int[2][7];
        rewardArray[0][0] = 3;  // 0 coin
        rewardArray[1][0] = 0;
        rewardArray[0][1] = 40; // 100 coins
        rewardArray[1][1] = 100;
        rewardArray[0][2] = 30; // 200 coins
        rewardArray[1][2] = 200;
        rewardArray[0][3] = 15; // 500 coins
        rewardArray[1][3] = 500;
        rewardArray[0][4] = 5;  // 1000 coins
        rewardArray[1][4] = 1000;
        rewardArray[0][5] = 2;  // 2000 coins
        rewardArray[1][5] = 2000;
        rewardArray[0][6] = 5;  // 1 piece of giftcard
        rewardArray[1][6] = -1;

        int[] reward = new int[max];
        int n = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < rewardArray[0][i]; j++) {
                reward[n] = rewardArray[1][i];
                n++;
            }
        }

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(max);
        int result = reward[randomInt];

        Log.d("[ROULETTE_ACTIVITY]", "### reward = " + result);

        if (result > 0) {
            // coin reward
            rewardCoin.setVisibility(View.VISIBLE);
            rewardTextCoin.setText(String.valueOf(result));
            TranslateAnimation translation;
            translation = new TranslateAnimation(0f, 0f, 0f, -500f);
            translation.setStartOffset(0);
            translation.setDuration(1000);
            translation.setFillAfter(true);
            translation.setFillEnabled(true);
//            translation.setInterpolator(new BounceInterpolator());
            translation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    btnSpin.setVisibility(View.VISIBLE);
                }
            });
            sh.addCoin(result);
            textCoin.setText(String.valueOf(sh.getCoin()));
            rewardCoin.startAnimation(translation);
        } else {
            // giftcard reward
            rewardGiftcard.setVisibility(View.VISIBLE);
            int randomCard = randomGenerator.nextInt(sh.getGiftCardList().size());
            while (!sh.isGiftCardFull(randomCard)) {
                randomCard = randomGenerator.nextInt(sh.getGiftCardList().size());
            }
            rewardImageGiftcard.setImageResource(sh.pieceList[randomCard][sh.getGiftCardList().get(randomCard).getCount()]);
            Log.d("[ROULETTE_ACTIVITY]", "### randomCard = " + randomCard + "; getCount = " + sh.getGiftCardList().get(randomCard).getCount());
            sh.addGiftCardPiece(randomCard);
            TranslateAnimation translation;
            translation = new TranslateAnimation(0f, 0f, 0f, -450f);
            translation.setStartOffset(0);
            translation.setDuration(1000);
            translation.setFillAfter(true);
            translation.setFillEnabled(true);
//            translation.setInterpolator(new BounceInterpolator());
            translation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    btnSpin.setVisibility(View.VISIBLE);
                }
            });
            rewardGiftcard.startAnimation(translation);
        }
    }
}
