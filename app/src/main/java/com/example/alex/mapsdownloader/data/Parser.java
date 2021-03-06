package com.example.alex.mapsdownloader.data;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.alex.mapsdownloader.R;
import com.example.alex.mapsdownloader.models.Region;

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

    /**
     * This method is needed in order to obtain a list of subregions of the region selected in the
     * listview. This in order not to implement parcelable in Region.
     *
     * @param path list of indexes of transitions in the listview of regions.
     * @return subregions of the region, which is defined in the path, if any; otherwise null.
     */
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

    /**
     * Converts a xml with data to a list of regions with a nested structure.
     */
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
                    String type = parser.getAttributeValue(null, "type");
                    String map = parser.getAttributeValue(null, "map");

                    if (name != null) {
                        newReg.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                    }
                    if (downloadPrefix != null) newReg.setDownloadPrefix(downloadPrefix);
                    if (innDownloadPrefix != null) {
                        if (innDownloadPrefix.equals("$name")) {
                            newReg.setInnDownloadPrefix(name);
                        } else newReg.setInnDownloadPrefix(innDownloadPrefix);
                    }
                    if (downloadSuffix != null) newReg.setDownloadSuffix(downloadSuffix);
                    if (innDownloadSuffix != null) newReg.setInnDownloadSuffix(innDownloadSuffix);
                    if (map != null && map.equals("no")) newReg.setMap(false);

                    if (parser.getDepth() == 2) {
                        regions.add(newReg);
                        Collections.sort(regions);
                    } else {
                        if (type == null) current.addSubregion(newReg);
                        else if (!type.equals("srtm")) current.addSubregion(newReg);
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

