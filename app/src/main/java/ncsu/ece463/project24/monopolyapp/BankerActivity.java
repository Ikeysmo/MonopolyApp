package ncsu.ece463.project24.monopolyapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.Vector;

public class BankerActivity extends AppCompatActivity {
    private Vector<Player> players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banker);
        int numPlayers = 0;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            numPlayers = extras.getInt("Players", 1);
        }
        players = new Vector<Player>();
        for(int i = 0; i < numPlayers; i++ ){
            players.add(new Player("Isaiah", 1500));
        }

        ExpandableListView exlistview = (ExpandableListView) findViewById(R.id.banker_list);
        exlistview.setClickable(true);
        exlistview.setDividerHeight(8);
        //exlistview.setDivider(getResources().getDrawable(R.color.Black));
        banker_adapter adapter = new banker_adapter(this, players, getLayoutInflater());
        exlistview.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
