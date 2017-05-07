package com.example.alex.mapsdownloader;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();
    private static ArrayList<Region> regions;

    public static void loadData(Context context) {
        if (regions == null) parse(context);
    }

    @Nullable
    public static ArrayList<Region> getRegions(List<Integer> path) {
        if (path.size() == 0 || regions == null) return regions;
        else {
            ArrayList<Region> res = new ArrayList<>();
            for (int i = 0; i < path.size(); i++) {
                if (i == 0) res = regions.get(path.get(i)).getSubregions();
                else res = res.get(path.get(i)).getSubregions();
            }
            return res;
        }
    }

    private static void parse(Context context) {
        regions = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.data);

        try {
            int eventType = parser.getEventType();
            Region current = new Region();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    Region newReg = new Region();

                    String name = parser.getAttributeValue(null, "name");
                    String downloadPrefix = parser.getAttributeValue(null, "download_prefix");
                    String downloadSuffix = parser.getAttributeValue(null, "download_suffix");
                    String innDownloadPrefix = parser.getAttributeValue(null, "inner_download_prefix");
                    String innDownloadSuffix = parser.getAttributeValue(null, "inner_download_suffix");
                    String map = parser.getAttributeValue(null, "map");

                    if (name != null) {
                        newReg.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                    }
                    if (downloadPrefix != null) newReg.setDownloadPrefix(downloadPrefix);
                    if (innDownloadPrefix != null) {
                        if (innDownloadPrefix.equals("$name")) {
                            newReg.setDownloadPrefix(name);
                        } else newReg.setDownloadPrefix(innDownloadPrefix);
                    }
                    if (downloadSuffix != null) newReg.setDownloadSuffix(downloadSuffix);
                    if (innDownloadSuffix != null) newReg.setDownloadSuffix(innDownloadSuffix);
                    if (map != null && map.equals("no")) newReg.setMap(false);

                    if (parser.getDepth() == 2) {
                        regions.add(newReg);
                        Collections.sort(regions);
                    } else {
                        current.addSubregion(newReg);
                        newReg.setParent(current);
                    }
                    current = newReg;
                }

                if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getDepth() > 1) current = current.getParent();
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Cannot parse xml", e);
        } catch (IOException e) {
            Log.e(TAG, "Cannot parse xml", e);
        }
    }
}

