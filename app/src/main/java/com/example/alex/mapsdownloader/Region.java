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

    private String getDownloadPrefix() {
        if (downloadPrefix != null) return downloadPrefix;
        else {
            if (parent == null) return null;
            return parent.getDownloadPrefix();
        }
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    private String getDownloadSuffix() {
        if (downloadSuffix != null) return downloadSuffix;
        else {
            if (parent == null) return null;
            return parent.getDownloadSuffix();
        }
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    public boolean hasMap() {
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

    public String getDownloadPath() {
        if (subregions.size() == 0 && map) {
            StringBuilder result = new StringBuilder();
            String prefix = getDownloadPrefix();
            String suffix = getDownloadSuffix();

            if (prefix != null) {
                result.append(prefix);
                result.append("_");
            }
            result.append(name);
            if (suffix != null) {
                result.append("_");
                result.append(suffix);
            }
            result.append("_2.obf.zip");

            return result.toString();
        } else return null;
    }

    @Override
    public int compareTo(@NonNull Region reg) {
        return name.compareTo(reg.getName());
    }
}
