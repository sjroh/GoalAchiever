package roh.sijine.goalachiever;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RouletteActivity extends AppCompatActivity {

    private Context mContext;
    private ScoreHandler sh;
    private TextView textCoin;

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

        // setup text coin
        sh = new ScoreHandler(mContext);
        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        // TODO
        // WRITE YOUR CODE

    }

}
