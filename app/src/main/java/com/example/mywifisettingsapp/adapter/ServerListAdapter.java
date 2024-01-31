package com.example.mywifisettingsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywifisettingsapp.R;
import com.example.mywifisettingsapp.model.ServerModelClass;

import java.util.ArrayList;

public class ServerListAdapter extends RecyclerView.Adapter<ServerListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<ServerModelClass> serverList;

    public ServerListAdapter(Context context, ArrayList<ServerModelClass> serverList) {
        this.context = context;
        this.serverList = serverList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_server_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServerModelClass serverModelClass=serverList.get(position);
        holder.countryName.setText(serverModelClass.getServerCountry());
        holder.imageView.setImageResource(getSingleDrawable(serverModelClass.getPing()));
        Glide.with(context).load(serverModelClass.getFlagUrl()).into(holder.imageViewFlag);
    }

    private int getSingleDrawable(int ping) {
        if (ping <= 10){
            return R.drawable.full_wifi;
        }else if (ping<=20){
            return R.drawable.wifi_3;
        }else if (ping<=30){
            return R.drawable.wifi_2;
        }else {
            return R.drawable.wifi_1;
        }
    }


    @Override
    public int getItemCount() {
        return serverList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,imageViewFlag;
        TextView countryName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_vpn_ping);
            imageViewFlag=itemView.findViewById(R.id.img_vpn_flag);
            countryName=itemView.findViewById(R.id.tv_country_name);
        }
    }

}
