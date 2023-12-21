package com.ashysystem.mbhq.model;

/**
 * Created by android-arindam on 4/5/17.
 */

public class SongInfoModel {
    private String title;
    private String album;
    private String artist;
    private String duration;
    private String path;
    private boolean isPlaying=false;
    public SongInfoModel(String title, String album, String artist, String duration, String path, boolean isPlaying) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.path=path;
        this.isPlaying=isPlaying;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
