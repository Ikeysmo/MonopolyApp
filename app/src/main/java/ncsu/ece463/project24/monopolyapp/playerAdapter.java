package ncsu.ece463.project24.monopolyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ikeys on 12/28/2016.
 */

public class playerAdapter extends ArrayAdapter<Player> {

    public playerAdapter(Context context, ArrayList<Player> Players){
        super(context, 0, Players);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Player player = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listlayout, parent, false);
        }
        //TextView pname = (TextView) convertView.findViewById(R.id.playerName);
        //pname.setText(player.name);
        return convertView;
    }
}
