package com.example.alex.mapsdownloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MemoryFragment extends Fragment {

    public MemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        TextView memoryTv = (TextView) view.findViewById(R.id.memory_tv);
        ProgressBar memoryPb = (ProgressBar) view.findViewById(R.id.memory_pb);

        double internalSpace = ((MainActivity) getActivity()).getInternalSpace();
        double freeInternalSpace = ((MainActivity) getActivity()).getInternalFreeSpace();

        memoryPb.setMax((int) internalSpace);
        memoryPb.setProgress((int) (internalSpace - freeInternalSpace));

        NumberFormat formatter = new DecimalFormat("#0.00");
        memoryTv.setText(formatter.format(freeInternalSpace) + " GB");

        return view;
    }
}
