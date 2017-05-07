package com.example.alex.mapsdownloader;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Region implements Comparable<Region> {

    private String name;
    private String downloadPrefix;
    private String downloadSuffix;
    private boolean map = true;
    private Region parent;
    private ArrayList<Region> subregions = new ArrayList<>();

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

    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public ArrayList<Region> getSubregions() {
        return subregions;
    }

    public void addSubregion(Region subregion) {
        subregions.add(subregion);
        Collections.sort(subregions);
    }

    @Override
    public int compareTo(@NonNull Region reg) {
        return name.compareTo(reg.getName());
    }
}
