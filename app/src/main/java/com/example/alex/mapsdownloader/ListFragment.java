package com.example.alex.mapsdownloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private List<Integer> path;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("path")) {
            path = bundle.getIntegerArrayList("path");
        } else path = new ArrayList<>();

        RecyclerView regsList = (RecyclerView) view.findViewById(R.id.regions_list);
        regsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        regsList.setLayoutManager(layoutManager);
        DividerItemDecoration did = new DividerItemDecoration(regsList.getContext(), layoutManager.getOrientation());
        regsList.addItemDecoration(did);

        RegionsAdapter adapter = new RegionsAdapter(Parser.getRegions(path));
        regsList.setAdapter(adapter);

        return view;
    }
}
