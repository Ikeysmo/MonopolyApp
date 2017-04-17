package ncsu.ece463.project24.monopolyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ListActivity extends AppCompatActivity {
    public ArrayList<Player> pplist = new ArrayList<Player>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listview = (ListView) findViewById(R.id.playerList);
        playerAdapter adapter = new playerAdapter(this, SetupActivity.playerList);
        listview.setAdapter(adapter);
        runConsistently();

    }

    private void runConsistently() {
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new Client("192.168.1.114", 4550);
                        client.execute();
                    }
                });

            }
        };
        timer.schedule(task, 0, 1000);
    }

    private class Client extends AsyncTask<Void, Void, Void> {
        String dst_address;
        int dstPort;
        Activity activity;
        public boolean isConnected = false;

        Client(Activity activity, String addr, int port) {
            this.activity = activity;
            dst_address = addr;
            dstPort = port;
        }

        Client(String addr, int port) {
            dst_address = addr;
            dstPort = port;
        }

        protected synchronized Void doInBackground(Void... params) {
            Log.d("DEBUG", "Trying hard to get with berugh!");
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.1.114", dstPort), 1000);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                dos.writeUTF("<requestAll>");
                String response = "";
                Log.d("DEBUG", "Trying to read from line!!!");
                while(true) {
                    String tmp = dis.readUTF();
                    Log.d("DIED", "GOT THIS SONE! " + tmp);
                    response += tmp;
                    if(dis.available() == 0)
                        break;
                }
                socket.close();
                Log.d("DEBUG", "this is result: " + response);
                String[] ps = response.split("\n");
                Log.d("DEBUG", "length: " + ps.length);
                pplist.clear();
                for (int i = 0; i < ps.length; i++) {
                    Log.d("DEBUG", "WHYY");
                    pplist.add(i, new Player(ps[i]));
                }

            } catch (IOException ie) {
                Log.d("DEBUG", "UNABLE TO CONNECT TO CERTAIN ADDRESS");
            }
            return null;
        }

        protected void onPostExecute(Void results) {
            Log.d("DEBUG", "AM I DONE?");
            ListView lv = (ListView) findViewById(R.id.playerList);
            playerAdapter ppadapter = new playerAdapter(getApplicationContext(), SetupActivity.playerList);

            lv.setAdapter(ppadapter);


//            playerAdapter as = (playerAdapter) lv.getAdapter();
////            as.clear();
//            as.addAll(pplist);
//            as.notifyDataSetChanged();



        }
    }
}