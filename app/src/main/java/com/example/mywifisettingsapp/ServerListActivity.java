package com.example.mywifisettingsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mywifisettingsapp.adapter.ServerListAdapter;
import com.example.mywifisettingsapp.model.ServerModelClass;

import java.util.ArrayList;

public class ServerListActivity extends AppCompatActivity {
    RecyclerView ServerRecyclerView;
    ArrayList<ServerModelClass> ServerList;
    ServerListAdapter ServerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        ServerRecyclerView = findViewById(R.id.recycler_server_list);
        ServerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ServerModelClass> serverList = new ArrayList<>();
        serverList.add(new ServerModelClass("Japan", "https://www.vpngate.net/images/flags/JP.png", "udp", 1195, "1683680111254", "15094348", "219.100.37.123", 20, 254.51, 72));
        serverList.add(new ServerModelClass("Japan", "https://www.vpngate.net/images/flags/JP.png", "udp", 1195, "1683680111254", "15074628", "219.100.37.7", 21, 420.15, 96));
        serverList.add(new ServerModelClass("United States", "https://www.vpngate.net/images/flags/US.png", "udp", 8080, "1683676805152", "18570753", "163.182.174.159", 4, 0.16, 130));
        serverList.add(new ServerModelClass("United States", "https://www.vpngate.net/images/flags/US.png", "udp", 441, "1683680111254", "18833185", "173.198.248.39", 4, 3.67, 171));
        serverList.add(new ServerModelClass("Viet Nam", "https://www.vpngate.net/images/flags/VN.png", "udp", 8443, "1683680111254", "19362304", "113.166.128.178", 29, 254.39, 120));
        serverList.add(new ServerModelClass("Korea", "https://www.vpngate.net/images/flags/KR.png", "udp", 1751, "1683660283516", "16272780", "210.96.184.151", 41, 19.93, 101));

        ServerList = serverList;

        Log.d("TAG", "onCreate: " + ServerList);
        if (ServerList != null) {
            ServerAdapter = new ServerListAdapter(this, ServerList);
            ServerRecyclerView.setAdapter(ServerAdapter);
        }
    }

}