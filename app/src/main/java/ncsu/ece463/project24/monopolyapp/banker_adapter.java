package ncsu.ece463.project24.monopolyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by Ikeys on 3/23/2017.
 */

public class banker_adapter extends BaseExpandableListAdapter {
    private final Context context;
    private final Vector<Player> players;
    private LayoutInflater layinf;

    public banker_adapter(Context context, Vector<Player> players, LayoutInflater layinf){
        this.context = context;
        this.players = players;
        this.layinf = layinf;
    }


    @Override
    public int getGroupCount() {
        return players.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return players.elementAt(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        final int index = i;
        if(view == null){
            view = layinf.inflate(R.layout.listlayout, null);
        }
        Spinner spin = (Spinner) view.findViewById(R.id.spinner2);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("DEBUG", "Name: " + players.elementAt(index).name + " becomes " + adapterView.getSelectedItem().toString());
                players.elementAt(index).name = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertview, ViewGroup viewGroup) {
        if(convertview == null){
            Log.d("DEBUG", "YOU KNOW ME<");
            convertview = layinf.inflate(R.layout.expandedlist, null);
        }

        Button add200Button = (Button) convertview.findViewById(R.id.add200Button);
        Button addMoneyButton = (Button) convertview.findViewById(R.id.addMoneyButton);
        Button removeMoneyButton = (Button) convertview.findViewById(R.id.removeMoneyButoon);
        Button transferButton = (Button)convertview.findViewById(R.id.transferButton);
        final int index = i;
        final View cview = convertview;
        final ViewGroup vwGroup = viewGroup;
        add200Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players.elementAt(index).money += 200;
                Log.d("DEBUG", players.elementAt(index).name + " now has " + String.valueOf(players.elementAt(index).money));
                TextView tv = (TextView) vwGroup.getRootView().findViewById(R.id.moneyAmount);
                String money = String.valueOf(players.elementAt(index).money);
                if(money != null && tv != null)
                    tv.setText("$"+ money);
            }
        });
        return convertview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean isEmpty(){
        return players.size() == 0?  true: false;
    }
}
