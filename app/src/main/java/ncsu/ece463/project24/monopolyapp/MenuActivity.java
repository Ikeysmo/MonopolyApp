package ncsu.ece463.project24.monopolyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.io.InputStreamReader;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void gotoProfile(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void gotoBanker(View view){
        Intent intent = new Intent(this, BankerActivity.class);
        intent.putExtra("Players", ((SeekBar) findViewById(R.id.seekBar)).getProgress() + 1);
        startActivity(intent);
    }
}
