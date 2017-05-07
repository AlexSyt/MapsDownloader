package com.example.alex.mapsdownloader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RegionsAdapter extends RecyclerView.Adapter<RegionsAdapter.RegionViewHolder> {

    private final List<Region> regions;

    public RegionsAdapter(List<Region> regions) {
        Collections.sort(regions, new Comparator<Region>() {
            @Override
            public int compare(Region reg1, Region reg2) {
                return reg1.getName().compareTo(reg2.getName());
            }
        });
        this.regions = regions;
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_item, parent, false);
        return new RegionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, int position) {
        Region current = regions.get(position);

        if (current.getSubregions().size() > 0) {
            holder.icon.setBackgroundResource(R.mipmap.ic_world_globe_dark);
        } else {
            holder.icon.setBackgroundResource(R.mipmap.ic_map);
            holder.download.setVisibility(View.VISIBLE);
            holder.download.setBackgroundResource(R.mipmap.ic_action_import);
        }

        holder.title.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    static class RegionViewHolder extends RecyclerView.ViewHolder {

        final ImageView icon;
        final TextView title;
        final ImageButton download;

        RegionViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.region_icon_iv);
            title = (TextView) v.findViewById(R.id.region_title_tv);
            download = (ImageButton) v.findViewById(R.id.region_download_ib);
        }
    }
}
