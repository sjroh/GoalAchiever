package roh.sijine.goalachiever;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijine on 11/2/15.
 */
public class ScoreHandler {
    private SharedPreferences preferences;
    private SharedPreferences listPreferences;

    private int coin;
    private List<Integer> giftCards;

    final private int MAXPIECE = 6;
    final private int MAXGIFTCARDS = 5;

    ScoreHandler(Context mContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        listPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);

        // setup basic values
        coin = preferences.getInt("COIN", 0);
        giftCards = new ArrayList<Integer>();
        giftCards.add(preferences.getInt("GC1", 0));
        giftCards.add(preferences.getInt("GC2", 0));
        giftCards.add(preferences.getInt("GC3", 0));
        giftCards.add(preferences.getInt("GC4", 0));
        giftCards.add(preferences.getInt("GC5", 0));
    }

    public List<Integer> getGiftCards() {
        return giftCards;
    }

    public int getCoin() {
        return coin;
    }

    public void addCoin(int c) {
        SharedPreferences.Editor editor = preferences.edit();
        coin += c;
        editor.putInt("COIN", coin);
        editor.commit();
    }

    public void addGiftCardPiece(int i) {
        if (i < MAXGIFTCARDS && i >= 0) {
            if (giftCards.get(i) < MAXPIECE) {
                giftCards.set(i, Integer.valueOf(giftCards.get(i) + 1));
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("GC" + String.valueOf(i + 1), giftCards.get(i));
                editor.commit();
            } else {
                Log.d("[SCORE_HANDLER]", "### SELECTED GIFT CARD is already MAX");
            }
        } else {
            Log.d("[SCORE_HANDLER]", "### OUT OF RANGE, index of Gift Cards");
        }
    }
}
