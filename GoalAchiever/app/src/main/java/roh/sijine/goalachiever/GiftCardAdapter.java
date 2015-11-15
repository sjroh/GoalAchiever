package roh.sijine.goalachiever;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

/**
 * Created by sijine on 11/15/15.
 */
public class GiftCardAdapter extends ArrayAdapter<GiftCard> {
    private final int layoutResourceId;
    private final ArrayList<GiftCard> data;
    customButtonListener customListner;
    private Context mContext;
    private ArrayList<GiftCard> list;
    private int position;
    private ScoreHandler sh;

    public GiftCardAdapter(Context mContext, int layoutResourceId, ArrayList<GiftCard> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

        sh = new ScoreHandler(mContext.getApplicationContext());
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        GiftCard giftcard = data.get(position);
        TextView giftcardName = (TextView) convertView.findViewById(R.id.text_card_name);
        TextView giftcardCount = (TextView) convertView.findViewById(R.id.text_card_count);
        FButton btnCart = (FButton) convertView.findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position);
                }
            }
        });
        giftcardName.setText(giftcard.getName());
        if (giftcard.getCount() == giftcard.getMax()) {
            giftcardCount.setVisibility(View.GONE);
            btnCart.setVisibility(View.VISIBLE);
        } else {
            btnCart.setVisibility(View.GONE);
            giftcardCount.setVisibility(View.VISIBLE);
            giftcardCount.setText(giftcard.getCount() + " / " + giftcard.getMax());
        }

        return convertView;
    }

    public interface customButtonListener {
        void onButtonClickListner(int position);
    }

}