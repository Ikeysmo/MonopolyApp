package ncsu.ece463.project24.monopolyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {
    public static ArrayList<Player> playerList = new ArrayList<Player>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }

    public void connectService(View view){ //needs to get the information from ip address, and begin service. Lastly try to begin the next activity
        EditText ipInfo = (EditText) findViewById(R.id.ipAddress);
        String ipField = ipInfo.getText().toString();
        //if(ipField.isEmpty() || isNotIPAddress(ipField))
          //  return; //not ready!
        Log.d("DEBUG", "Whaddya Say");
        //begin service
        Client client = new Client(this, "192.168.1.114", 4550);
        client.execute();
/*        if(client.isConnected) {
            //do something about service
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        else{
            Log.d("DEBUG", "Succeful connected");
        }*/
    }

    public void startNextService(){
            //do something about service
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
    }
    private boolean isNotIPAddress(String ipField) {
        if(ipField.contains("."))
            return false;
        else
            return true;

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
        protected Void doInBackground(Void... params) {
            Log.d("DEBUG", "Trying hard to get with berugh!");
            Socket socket = null;
            try {
                socket = new Socket();
                //socket.setSoTimeout(1000);
                socket.connect(new InetSocketAddress("192.168.1.114", dstPort), 1000);

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
            startActivity(intent);

        }
    }

}
