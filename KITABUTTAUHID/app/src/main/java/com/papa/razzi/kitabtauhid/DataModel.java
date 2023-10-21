package com.papa.razzi.kitabtauhid;

public class DataModel {
    private String mMusicName;
    private int mMusicResourceId;

    public String getMusicName() {
        return mMusicName;
    }

    public int getMusicResourceId() {
        return mMusicResourceId;
    }

    public DataModel(String mMusicName, int mMusicResourceId) {
        this.mMusicName = mMusicName;
        this.mMusicResourceId = mMusicResourceId;
    }
}
