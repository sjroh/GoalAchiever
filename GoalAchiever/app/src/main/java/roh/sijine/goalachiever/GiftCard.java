package roh.sijine.goalachiever;

/**
 * Created by sijine on 11/15/15.
 */
public class GiftCard {
    static final int max = 6;
    private int id;
    private int count;
    private String name;

    GiftCard(int i, String s) {
        id = i;
        name = s;
        count = 0;
    }

    GiftCard(int i, String s, int c) {
        id = i;
        name = s;
        count = c;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax() {
        return max;
    }
}
