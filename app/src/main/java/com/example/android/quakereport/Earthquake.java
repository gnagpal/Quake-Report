package com.example.android.quakereport;

public class Earthquake {
    private double mag;

    private String location;

    private Long timeInMillis;

    private String url;

    public Earthquake(Double mag, String location, Long time, String url) {
        this.mag = mag;
        this.location = location;
        this.timeInMillis = time;
        this.url = url;
    }

    public Double getMag() {
        return mag;
    }

    public String getLocation() {
        return location;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public String getUrl() {
        return url;
    }
}
