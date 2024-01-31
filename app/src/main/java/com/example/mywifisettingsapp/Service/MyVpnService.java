package com.example.mywifisettingsapp.Service;

import android.content.Intent;
import android.net.VpnService;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mywifisettingsapp.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyVpnService extends VpnService {
    private static final String TAG="MyVpnService";
    private final AtomicBoolean isRunning =new AtomicBoolean(false);
    private ParcelFileDescriptor VpnInterface;
    private String ServerIp;
    private int ServerPortNumber;
    private Handler handler=new Handler(Looper.getMainLooper());

    public static final String ACTION_VPN_CONNECTED="com.example.mywifisettingsapp.Service";
    public static MyVpnService instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public ParcelFileDescriptor getVpnInterface() {
        return VpnInterface;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            //get the server Ip address and port number from the intent
            ServerIp=intent.getStringExtra("vpnIp");
            ServerPortNumber=intent.getIntExtra("vpnPort",0);

            //now start the vpn connection in separate thread
            Thread VpnThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    MyVpnService.this.runVpnConnection();

                }
            });
            VpnThread.start();

        }

        return START_STICKY;
    }

    private void runVpnConnection() {
        try {
            //Establish the vpn connection
            if (establishedVpnConnection()){
                readFromVpnInterface();
            }
        }catch (Exception e){
            Log.d(TAG, "Error VPN Connection "+e.getMessage());
        }finally {
            StopVpnConnection();
        }
    }

    private void StopVpnConnection() {
    }

    private boolean establishedVpnConnection() throws IOException {
        if (VpnInterface !=null){
            Builder builder=new Builder();
            builder.addAddress(ServerIp,32);
            builder.addRoute("0.0.0.0",0);

            VpnInterface=builder.setSession(getString(R.string.app_name))
                    .setConfigureIntent(null)
                    .establish();

            return VpnInterface != null;

        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onVpnConnectionSuccess();
                    Toast.makeText(MyVpnService.this,"VPN Connection Already Established",Toast.LENGTH_SHORT).show();
                }
            });
        }
        return true;
    }

    //Read from the vpn Interface and write to the network
    private void readFromVpnInterface() throws IOException{
        isRunning.set(true);
        ByteBuffer buffer=ByteBuffer.allocate(32767);

        while (isRunning.get()){
            try {
                FileInputStream inputStream=new FileInputStream(VpnInterface.getFileDescriptor());
                int length=inputStream.read(buffer.array());
                if (length>0){
                    String receivedData= new String(buffer.array(),0,length);
                    //now send the reveived data to the main activity using local broad cast receiver
                    Intent intent=new Intent("received_data_from_vpn");
                    intent.putExtra("data",receivedData);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                    //now create a method to write the processed data to the network
                    writeToNetwork(buffer,length);
                }
            }catch (Exception e){
                Log.d(TAG, "error reading data from vpn interface "+e.getMessage());
            }
        }
    }

    private void writeToNetwork(ByteBuffer buffer, int length) {
        String processData=new String(buffer.array(),0,length);

        try{
            Socket socket=new Socket(ServerIp,ServerPortNumber);
            OutputStream outputStream=socket.getOutputStream();

            byte[] dataBytes=processData.getBytes(StandardCharsets.UTF_8);
            outputStream.write(dataBytes);

            outputStream.close();
            socket.close();
        }catch (Exception e){
            Log.d(TAG, "error sending data to the server"+e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopVpnConnection();
    }

    private void stopVpnConnection() {
        isRunning.set(false);
        if (VpnInterface != null){
            try {
                VpnInterface.close();
            }catch (Exception e){
                Log.d(TAG, "error close Vpn Connection"+ e.getMessage());
            }
        }
    }

    private void onVpnConnectionSuccess(){
        //here we can send a local broadcast to the main activity to notify user by success message
        Intent intent=new Intent(ACTION_VPN_CONNECTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
