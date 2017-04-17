package ncsu.ece463.project24.monopolyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Ikeys on 12/23/2016.
 */

public class Client extends AsyncTask<Void, Void, Void> {
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
    protected Void doInBackground(Void... params) {
        Log.d("DEBUG", "Trying hard to get with berugh!");
        Socket socket = null;
        try {
            socket = new Socket();
            //socket.setSoTimeout(1000);
            socket.connect(new InetSocketAddress("192.168.1.181", dstPort), 1000);

            //OutputStreamWriter ow = new OutputStreamWriter(socket.getOutputStream());
            //InputStreamReader ir = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            //BufferedReader br = new BufferedReader(ir);
            //BufferedWriter bw = new BufferedWriter(ow);
            isConnected = true;
            dos.writeUTF("<requestAll>");
            String response = "";
            Log.d("DEBUG", "Trying to read from line!!!");
            while (true) {
                String tmp = dis.readUTF();
                Log.d("DIED", "GOT THIS SONE! " + tmp);
                response+=tmp;
                if(dis.available() == 0)
                    break;
            }
            dis.close();
            Log.d("DEBUG", "this is result: " + response);
            String[] ps = response.split("\n");
            Log.d("DEBUG", "length: " + ps.length);
            for(int i = 0; i < ps.length; i++){
                Log.d("DEBUG", "WHYY");
                SetupActivity.playerList.add(i, new Player(ps[i]));
            }
            Log.d("DEBUG", "Successfully connected");


        } catch (IOException ie) {
            Log.d("DEBUG", "UNABLE TO CONNECT TO CERTAIN ADDRESS");
        }
        return null;
    }

    protected void onPostExecute(Void results) {
        TextView tt = (TextView) activity.findViewById(R.id.connection_status);
        tt.setText("Connected!");
        Intent intent = new Intent(activity, MenuActivity.class );
        activity.startActivity(intent);
    }
}
