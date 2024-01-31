package com.example.mywifisettingsapp.model;

public class ServerModelClass {
    private String ServerCountry;
    private String FlagUrl;
    private String type;
    private int port;
    private String sid;
    private String hid;
    private String ip;
    private int ping;
    private double bandwidth;
    private int sessions;

    public ServerModelClass() {
    }

    public ServerModelClass(String serverCountry,String flagUrl, String type, int port, String sid, String hid, String ip, int ping, double bandwidth, int sessions) {
        ServerCountry = serverCountry;
        FlagUrl = flagUrl;
        this.type=type;
        this.port = port;
        this.sid = sid;
        this.hid = hid;
        this.ip = ip;
        this.ping = ping;
        this.bandwidth = bandwidth;
        this.sessions = sessions;
    }

    public String getServerCountry() {
        return ServerCountry;
    }

    public void setServerCountry(String serverCountry) {
        ServerCountry = serverCountry;
    }

    public String getFlagUrl() {
        return FlagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        FlagUrl = flagUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
