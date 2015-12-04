package roh.sijine.goalachiever;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sijine on 11/2/15.
 */
public class ScoreHandler {
    final private int MAXPIECE = 6;
    final private int MAXGIFTCARDS = 6;
    public final int[] logoList = new int[MAXGIFTCARDS];
    public final int[][] pieceList = new int[MAXGIFTCARDS][MAXPIECE];
    private SharedPreferences preferences;
    private SharedPreferences listPreferences;
    private int coin;
    //    private ArrayList<Integer> giftCards;
    private ArrayList<GiftCard> giftcards;

    ScoreHandler(Context mContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        listPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);

        // setup basic values
        coin = preferences.getInt("COIN", 0);

        giftcards = new ArrayList<GiftCard>();
        giftcards.add(new GiftCard(0, "Amazon", preferences.getInt("GC0", 0)));
        giftcards.add(new GiftCard(0, "Starbucks", preferences.getInt("GC1", 0)));
        giftcards.add(new GiftCard(0, "Target", preferences.getInt("GC2", 0)));
        giftcards.add(new GiftCard(0, "BestBuy", preferences.getInt("GC3", 0)));
        giftcards.add(new GiftCard(0, "Walmart", preferences.getInt("GC4", 0)));
        giftcards.add(new GiftCard(0, "Apple", preferences.getInt("GC5", 0)));

        String temp = "";
        for (int i = 0; i < giftcards.size(); i++) {
            temp += giftcards.get(i).getCount() + " ";
        }
        Log.d("[SCORE_HANDLER]", "### giftcards : " + temp);

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

    public int getCoin() {
        return coin;
    }

    public void addCoin(int c) { // c is the number of coins that you want to add.
        SharedPreferences.Editor editor = preferences.edit();
        coin += c;
        editor.putInt("COIN", coin);
        editor.commit();
    }

    public void setCoin(int c) {
        SharedPreferences.Editor editor = preferences.edit();
        coin = c;
        editor.putInt("COIN", coin);
        editor.commit();
    }

    public void removeCoin(int c) {
        SharedPreferences.Editor editor = preferences.edit();
        coin -= c;
        if (coin < 0) {
            coin = 0;
        }
        editor.putInt("COIN", coin);
        editor.commit();
    }

    public boolean isGiftCardFull() {
        int temp = 0;
        for (int j = 0; j < giftcards.size(); j++) {
            temp += giftcards.get(j).getCount();
        }
        String tt = "";
        for (int j = 0; j < giftcards.size(); j++) {
            tt += giftcards.get(j).getCount() + " ";
        }
        Log.d("[SCORE_HANDLER]", "### isGiftCarFull() = giftcards : " + tt);
        return (temp == giftcards.size() * MAXPIECE);
    }

    public boolean isGiftCardFull(int id) {
        return giftcards.get(id).getCount() < MAXPIECE;
    }

    public void addGiftCardPiece(int i) { // i is a index number of giftcard
        String temp = "";
        for (int j = 0; j < giftcards.size(); j++) {
            temp += giftcards.get(j).getCount() + " ";
        }
        Log.d("[SCORE_HANDLER]", "### giftcards : " + temp);

        if (i < MAXGIFTCARDS && i >= 0) {
            if (giftcards.get(i).getCount() < MAXPIECE) {
                giftcards.get(i).setCount(giftcards.get(i).getCount() + 1);

                SharedPreferences.Editor editor = preferences.edit();
                String gcname = "GC" + String.valueOf(i);
                editor.putInt(gcname, giftcards.get(i).getCount());
                editor.commit();

                Log.d("[SCORE_HANDLER]", "### " + "GC" + String.valueOf(i) + " : " + preferences.getInt(gcname, 0));

            } else {
                Log.d("[SCORE_HANDLER]", "### SELECTED GIFT CARD is already MAX");
            }
        } else {
            Log.d("[SCORE_HANDLER]", "### OUT OF RANGE, index of Gift Cards");
        }
    }

    public ArrayList<GiftCard> getGiftCardList() {
        return giftcards;
    }



    public void exchangeGiftCard(int i) { // i is a index number of giftcard
        if (i < MAXGIFTCARDS && i >= 0) {
            if (giftcards.get(i).getCount() == MAXPIECE) {
                giftcards.get(i).setCount(0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("GC" + String.valueOf(i), giftcards.get(i).getCount());
                editor.commit();
            } else {
                Log.d("[SCORE_HANDLER]", "### SELECTED GIFT CARD doesn't have enough pieces.");
            }
        } else {
            Log.d("[SCORE_HANDLER]", "### OUT OF RANGE, index of Gift Cards");
        }
    }
}
