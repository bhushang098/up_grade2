package com.example.upgrade;

public class Branches {

    String brName;
    int iconId;
    String url;

    public Branches(String brName, int iconId,String url) {
        this.setUrl(url);
        this.setBrName(brName);
        this.setIconId(iconId);
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
