package com.example.mywifisettingsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.VpnService;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mywifisettingsapp.Service.MyVpnService;

public class ServiceVpnMainActivity extends AppCompatActivity {

    private static final int VPN_REQUEST_CODE=1;
    Button startButton,ShowAllVpnButton;
    TextView countryName,counterText;
    static TextView txtConnected;
    ProgressBar progressBar;
    private int SererPort;
    String Serverip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_vpn_main);

        startButton=findViewById(R.id.btn_start);
        ShowAllVpnButton=findViewById(R.id.btn_show_vpn);
        countryName=findViewById(R.id.tv_vpn_country_name);
        progressBar=findViewById(R.id.progress_circular);
        counterText=findViewById(R.id.tv_vpn_counter);
        txtConnected=findViewById(R.id.tv_vpn_connected);

        ShowAllVpnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceVpnMainActivity.this, ServerListActivity.class);
                startActivity(intent);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                establishedVpnConnection();
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(VpnConnectedReceiver,
                new IntentFilter(MyVpnService.ACTION_VPN_CONNECTED));
    }

    private void establishedVpnConnection() {
        Intent VpnIntent= VpnService.prepare(ServiceVpnMainActivity.this);
        if (VpnIntent != null){
            startActivityForResult(VpnIntent,VPN_REQUEST_CODE);
        }else {
            StartVpnServiceWithIp();
        }
    }

    private void StartVpnServiceWithIp() {
        Intent vpnIntent=new Intent(ServiceVpnMainActivity.this, MyVpnService.class);
        vpnIntent.putExtra("vpnIp","163.182.174.159");
        vpnIntent.putExtra("vpnPort",8080);
        startActivity(vpnIntent);

    }

    private BroadcastReceiver VpnConnectedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyVpnService vpnService=MyVpnService.instance;
            if (vpnService != null && vpnService.getVpnInterface() != null){
                txtConnected.setText("Connected");
            }else {
                txtConnected.setText("Disconnected");
            }
        }
    };
    {

    }
}