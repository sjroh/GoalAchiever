package roh.sijine.goalachiever;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ResultActivity extends AppCompatActivity {

    private Context mContext;

    private TextView mContentView;

    private String message;
    private boolean status;

    private FButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        status = intent.getBooleanExtra("STATUS", false);
        message = intent.getStringExtra("RESULT");

        mContentView = (TextView) findViewById(R.id.fullscreen_content);

        btnBack = (FButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (status) {
            // Succeed
            int maxUnlock = intent.getIntExtra("maxUnlock", 0);
            int unlockCounter = intent.getIntExtra("unlockCounter", 0);

            // calculate the coin
            new ScoreHandler(mContext).addCoin(maxUnlock - unlockCounter);

            // test purpose
            mContentView.setText("Good/coin earned " + (maxUnlock - unlockCounter));
        } else {
            // Failed
            mContentView.setText("BAD/need to change this");
        }

    }

}
