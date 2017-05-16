package com.example.alex.mapsdownloader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alex.mapsdownloader.R;
import com.example.alex.mapsdownloader.Services.MyDownloadService;
import com.example.alex.mapsdownloader.activities.MainActivity;
import com.example.alex.mapsdownloader.adapters.RegionsAdapter;
import com.example.alex.mapsdownloader.data.Parser;
import com.example.alex.mapsdownloader.models.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionsFragment extends android.support.v4.app.ListFragment {

    public static final String KEY_MAP_PATH = "map_path";
    public static final String KEY_NAME = "name";
    private static final String KEY = "path";
    private ArrayList<Integer> path;
    private List<Region> regions;

    public RegionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(KEY)) {
            path = args.getIntegerArrayList(KEY);
        }

        regions = Parser.getRegions(path);

        RegionsAdapter adapter = new RegionsAdapter(getActivity(), regions);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regions, container, false);
    }

    /**
     * If the pressed region has subregions, the subregions are displayed in a new listview.
     * Otherwise, loading of the map for the pressed region begins.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Region current = regions.get(position);
        if (current.getSubregions().size() > 0) {
            ((MainActivity) getActivity()).showNextRegionFragment(position, current.getName());
        } else if (current.hasMap()) {
            Intent intent = new Intent(getContext(), MyDownloadService.class);
            intent.putExtra(KEY_MAP_PATH, current.getDownloadPath());
            intent.putExtra(KEY_NAME, current.getFileName());
            getActivity().startService(intent);
        }
    }
}
