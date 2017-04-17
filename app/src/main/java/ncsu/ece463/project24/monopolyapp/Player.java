package ncsu.ece463.project24.monopolyapp;

import android.util.Log;

/**
 * Created by Ikeys on 12/28/2016.
 */

public class Player {
    public String name;
    public int money;
    public static int POT_MONEY = 0;

    Player(String name, int money){
        this.name = name;
        this.money = money;
    }

    Player(String tag) throws IllegalArgumentException{
        this.name = getName(tag);
        this.money = getMoney(tag);
        Log.d("DEBUG", "name: " + name + " \nmoney: " + money);

    }

    public static String getName(String tag){
        tag = tag.trim();
        tag = tag.substring(1, tag.length()-1);
        Log.d("DEBUG", "this is tag: " + tag);
        String[] mini_tags = tag.split(",");
        return mini_tags[1];

    }

    public static int getMoney(String tag){
        tag = tag.trim();
        tag = tag.substring(1, tag.length()-1);
        Log.d("DEBUG", "this is tag: " + tag);
        String[] mini_tags = tag.split(",");
        return Integer.parseInt(mini_tags[2]);

    }

}
