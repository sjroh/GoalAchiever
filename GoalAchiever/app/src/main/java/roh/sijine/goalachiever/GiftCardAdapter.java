package roh.sijine.goalachiever;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private int maxLogos = 6;
    private int maxPieces = 6;
    private int[] logoList = new int[maxLogos];
    private int[][] pieceList = new int[maxLogos][maxPieces];

    public GiftCardAdapter(Context mContext, int layoutResourceId, ArrayList<GiftCard> data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

        sh = new ScoreHandler(mContext.getApplicationContext());

        logoList[0] = R.drawable.amazon;
        pieceList[0][0] = R.drawable.amazon1;
        pieceList[0][1] = R.drawable.amazon2;
        pieceList[0][2] = R.drawable.amazon3;
        pieceList[0][3] = R.drawable.amazon4;
        pieceList[0][4] = R.drawable.amazon5;
        pieceList[0][5] = R.drawable.amazon6;
        logoList[1] = R.drawable.starbucks;
        pieceList[1][0] = R.drawable.starbucks1;
        pieceList[1][1] = R.drawable.starbucks2;
        pieceList[1][2] = R.drawable.starbucks3;
        pieceList[1][3] = R.drawable.starbucks4;
        pieceList[1][4] = R.drawable.starbucks5;
        pieceList[1][5] = R.drawable.starbucks6;
        logoList[2] = R.drawable.target;
        pieceList[2][0] = R.drawable.target1;
        pieceList[2][1] = R.drawable.target2;
        pieceList[2][2] = R.drawable.target3;
        pieceList[2][3] = R.drawable.target4;
        pieceList[2][4] = R.drawable.target5;
        pieceList[2][5] = R.drawable.target6;
        logoList[3] = R.drawable.bestbuy;
        pieceList[3][0] = R.drawable.bestbuy1;
        pieceList[3][1] = R.drawable.bestbuy2;
        pieceList[3][2] = R.drawable.bestbuy3;
        pieceList[3][3] = R.drawable.bestbuy4;
        pieceList[3][4] = R.drawable.bestbuy5;
        pieceList[3][5] = R.drawable.bestbuy6;
        logoList[4] = R.drawable.walmart;
        pieceList[4][0] = R.drawable.walmart1;
        pieceList[4][1] = R.drawable.walmart2;
        pieceList[4][2] = R.drawable.walmart3;
        pieceList[4][3] = R.drawable.walmart4;
        pieceList[4][4] = R.drawable.walmart5;
        pieceList[4][5] = R.drawable.walmart6;
        logoList[5] = R.drawable.apple;
        pieceList[5][0] = R.drawable.apple1;
        pieceList[5][1] = R.drawable.apple2;
        pieceList[5][2] = R.drawable.apple3;
        pieceList[5][3] = R.drawable.apple4;
        pieceList[5][4] = R.drawable.apple5;
        pieceList[5][5] = R.drawable.apple6;
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
//            Log.d("[GIFTCARD_ADAPTER]", "### convertView is NULL!!!");
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
        RelativeLayout giftcardImages = (RelativeLayout) convertView.findViewById(R.id.image_card_count);

        giftcardCount.setVisibility(View.GONE);

        if (giftcard.getCount() == giftcard.getMax()) {
            btnCart.setVisibility(View.VISIBLE);

//            giftcardCount.setVisibility(View.GONE);
            giftcardImages.setVisibility(View.GONE);
        } else {
            btnCart.setVisibility(View.GONE);

//            giftcardCount.setVisibility(View.VISIBLE);
//            giftcardCount.setText(giftcard.getCount() + " / " + giftcard.getMax());
            giftcardImages.setVisibility(View.VISIBLE);
            ImageView[] giftcardPieces = new ImageView[maxPieces];
            giftcardPieces[0] = (ImageView) convertView.findViewById(R.id.giftcard_piece_1);
            giftcardPieces[1] = (ImageView) convertView.findViewById(R.id.giftcard_piece_2);
            giftcardPieces[2] = (ImageView) convertView.findViewById(R.id.giftcard_piece_3);
            giftcardPieces[3] = (ImageView) convertView.findViewById(R.id.giftcard_piece_4);
            giftcardPieces[4] = (ImageView) convertView.findViewById(R.id.giftcard_piece_5);
            giftcardPieces[5] = (ImageView) convertView.findViewById(R.id.giftcard_piece_6);
            for (int i = 0; i < maxPieces; i++)
                if (i < giftcard.getCount()) {
//                    int test = mContext.getResources().getDrawable(R.drawable.amazon1);
//                    giftcardPieces[i].setImageResource(convertView.getResources().getDrawable(R.drawable.amazon1));
                    giftcardPieces[i].setImageResource(pieceList[position][i]);
                    giftcardPieces[i].setVisibility(View.VISIBLE);
                } else {
                    giftcardPieces[i].setVisibility(View.GONE);
                }
        }

        return convertView;
    }

    public interface customButtonListener {
        void onButtonClickListner(int position);
    }

}