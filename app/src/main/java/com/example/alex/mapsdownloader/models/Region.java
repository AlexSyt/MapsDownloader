package com.example.alex.mapsdownloader.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Region implements Comparable<Region> {

    private String name;
    private String innDownloadPrefix;
    private String innDownloadSuffix;
    private String downloadPrefix;
    private String downloadSuffix;
    private boolean map = true;
    private Region parent;
    private final ArrayList<Region> subregions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return name + ".obf.zip";
    }

    public void setInnDownloadPrefix(String innDownloadPrefix) {
        this.innDownloadPrefix = innDownloadPrefix;
    }

    public void setInnDownloadSuffix(String innDownloadSuffix) {
        this.innDownloadSuffix = innDownloadSuffix;
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    private String getPrefix() {
        if (innDownloadPrefix != null && downloadPrefix != null) return innDownloadPrefix;
        if (innDownloadPrefix == null && downloadPrefix != null) return downloadPrefix;
        if (innDownloadPrefix != null) return innDownloadPrefix;
        if (parent != null) return parent.getPrefix();
        return null;
    }

    private String getSuffix() {
        if (innDownloadSuffix != null && downloadSuffix != null) return innDownloadSuffix;
        if (innDownloadSuffix == null && downloadSuffix != null) return downloadSuffix;
        if (innDownloadSuffix != null) return innDownloadSuffix;
        if (parent != null) return parent.getSuffix();
        return null;
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
            String prefix;
            String suffix;
            if (downloadPrefix != null) prefix = downloadPrefix;
            else prefix = parent.getPrefix();
            if (downloadSuffix != null) suffix = downloadSuffix;
            else suffix = parent.getSuffix();

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
