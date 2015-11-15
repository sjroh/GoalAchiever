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
        giftcards.add(new GiftCard(0, "Newegg", preferences.getInt("GC5", 0)));

        String temp = "";
        for (int i = 0; i < giftcards.size(); i++) {
            temp += giftcards.get(i).getCount() + " ";
        }
        Log.d("[SCORE_HANDLER]", "### giftcards : " + temp);
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
