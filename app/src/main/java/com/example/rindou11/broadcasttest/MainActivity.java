package com.example.rindou11.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.TokenWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //系统在网络发生变化时,会发送一条值为"android.net.conn.CONNECTIVITY_CHAGE"的广播
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    //NetworkChangeReceiver类是继承BroadcastReceiver这个android的广播类,并重写BroadChangeReceiver中的onReceiver方法.
    public class NetworkChangeReceiver extends BroadcastReceiver{

        public void onReceive(Context context,Intent intent){

            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

            if(networkInfo!=null && networkInfo.isConnected())
                Toast.makeText(context, "Network is available.", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(context,"Network is unavailable.",Toast.LENGTH_SHORT).show();
            }
        }
        //    Toast.makeText(context,"Network Changed.",Toast.LENGTH_SHORT).show();
        //}
    }

    protected void onDestory(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
