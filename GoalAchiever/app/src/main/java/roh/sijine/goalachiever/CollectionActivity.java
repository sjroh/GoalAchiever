package roh.sijine.goalachiever;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import info.hoang8f.widget.FButton;


public class CollectionActivity extends AppCompatActivity implements
        GiftCardAdapter.customButtonListener {

    private Context mContext;
    private Context mContextAdapter;
    private ScoreHandler sh;
    private TextView textCoin;
    private ListView listGiftCard;
    private FButton btnBuyPiece;
    private GiftCardAdapter listAdapter;
    private int exchangePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();
        mContextAdapter = this;

        setContentView(R.layout.activity_collection);

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

        // setup
        sh = new ScoreHandler(mContext);

        textCoin = (TextView) findViewById(R.id.text_coin);
        textCoin.setText(String.valueOf(sh.getCoin()));

        listGiftCard = (ListView) findViewById(R.id.list_giftcard);
        listAdapter = new GiftCardAdapter(mContextAdapter, R.layout.layout_list_row, sh.getGiftCardList());
        listAdapter.setCustomButtonListner(CollectionActivity.this);
        listGiftCard.setAdapter(listAdapter);

        btnBuyPiece = (FButton) findViewById(R.id.btn_buy_piece);
        btnBuyPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sh.addCoin(1001);
                if (sh.isGiftCardFull()) {
                    Toast.makeText(v.getContext(), "FULL COLLECTION!! YAY!!", Toast.LENGTH_SHORT).show();
                } else if (sh.getCoin() > 1000) {
                    sh.removeCoin(1000);
                    textCoin.setText(String.valueOf(sh.getCoin()));
                    Random r = new Random();
                    int rand = r.nextInt(sh.getGiftCardList().size());
                    while (sh.getGiftCardList().get(rand).getCount() == sh.getGiftCardList().get(rand).getMax()) {
                        rand = r.nextInt(sh.getGiftCardList().size());
                    }
                    sh.addGiftCardPiece(rand);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "You won " + sh.getGiftCardList().get(rand).getName() + " Gift Card Piece.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "NOT ENOUGH COINS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //

    }

    @Override
    public void onButtonClickListner(final int position) {
//        showInputDialog(position);
        // custom dialog
        final Dialog dialog = new Dialog(mContextAdapter);
        dialog.setContentView(R.layout.dialog_email);
        dialog.setTitle("Type your Email address");


        FButton btnOk = (FButton) dialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editEmail = (EditText) dialog.findViewById(R.id.input_email);
                EmailValidator ev = new EmailValidator();
                if (ev.validate(editEmail.getText().toString())) {
                    sh.exchangeGiftCard(position);
                    listAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mContext, "TYPE EMAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FButton btnCancel = (FButton) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    protected void showInputDialog(int position) {
        exchangePosition = position;
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptView = layoutInflater.inflate(R.layout.dialog_email, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptView);


        final EditText editText = (EditText) promptView.findViewById(R.id.input_email);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // email validation check
                        sh.exchangeGiftCard(exchangePosition);
                        listAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
