package com.example.alex.mapsdownloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RegionsAdapter extends ArrayAdapter {

    private Context context;
    private List regions;

    public RegionsAdapter(Context context, List regions) {
        super(context, R.layout.region_list_item, regions);
        this.context = context;
        this.regions = regions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.region_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        Region current = (Region) regions.get(position);

        if (current.getSubregions().size() > 0) {
            holder.icon.setBackgroundResource(R.mipmap.ic_world_globe_dark);
            holder.download.setVisibility(View.GONE);
        } else {
            holder.icon.setBackgroundResource(R.mipmap.ic_map);
            if (current.isMap()) {
                holder.download.setVisibility(View.VISIBLE);
                holder.download.setFocusable(false);
                holder.download.setBackgroundResource(R.mipmap.ic_action_import);
            }
        }

        holder.title.setText(current.getName());

        return convertView;
    }

    private static class ViewHolder {

        final ImageView icon;
        final TextView title;
        final ImageButton download;

        ViewHolder(View v) {
            icon = (ImageView) v.findViewById(R.id.region_icon_iv);
            title = (TextView) v.findViewById(R.id.region_title_tv);
            download = (ImageButton) v.findViewById(R.id.region_download_ib);
        }
    }
}
