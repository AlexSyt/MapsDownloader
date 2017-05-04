package com.example.alex.mapsdownloader;

import java.util.ArrayList;

public class Region {

    private String name;
    private String downloadPrefix;
    private String downloadSuffix;
    private boolean map = true;
    private ArrayList<Region> subregions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadPrefix() {
        return downloadPrefix;
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    public String getDownloadSuffix() {
        return downloadSuffix;
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
    }

    public ArrayList<Region> getSubregions() {
        return subregions;
    }

    public void setSubregions(ArrayList<Region> subregions) {
        this.subregions = subregions;
    }
}
