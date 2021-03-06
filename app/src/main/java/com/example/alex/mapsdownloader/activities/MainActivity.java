package com.example.alex.mapsdownloader.activities;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.alex.mapsdownloader.R;
import com.example.alex.mapsdownloader.data.Parser;
import com.example.alex.mapsdownloader.fragments.MemoryFragment;
import com.example.alex.mapsdownloader.fragments.RegionsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "path";
    private static final String HOME_SCREEN_TITLE = "Maps Downloader";
    private ArrayList<Integer> path;
    private ArrayList<String> names;
    private StatFs statFs;
    private MemoryFragment memoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parser.loadData(this);
        path = new ArrayList<>();
        names = new ArrayList<>();
        statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        memoryFragment = new MemoryFragment();
        RegionsFragment regionsFragment = new RegionsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(KEY, path);
        regionsFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.memory_fragment, memoryFragment)
                .add(R.id.list_fragment, regionsFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        showPreviousRegionFragment();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showPreviousRegionFragment();
            getSupportFragmentManager().popBackStack();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    /**
     * This is the method for displaying the previous Region on listview. Controls the title of the
     * action bar and displays a fragment with a free memory if needed.
     */
    private void showPreviousRegionFragment() {
        int pathSize = path.size();
        if (pathSize > 0) {
            if (pathSize > 1) {
                names.remove(names.size() - 1);
                getSupportActionBar().setTitle(names.get(names.size() - 1));
            } else {
                getSupportActionBar().setTitle(HOME_SCREEN_TITLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction()
                        .show(memoryFragment)
                        .commit();
            }
            path.remove(pathSize - 1);
        }
    }

    /**
     * This is the method for displaying the next Region on listview. Controls the title of the
     * action bar and hides a fragment with a free memory.
     */
    public void showNextRegionFragment(int position, String name) {
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .hide(memoryFragment)
                .commit();
        path.add(position);
        names.add(name);

        RegionsFragment next = new RegionsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(KEY, path);
        next.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_fragment, next)
                .addToBackStack(null)
                .commit();
    }

    /**
     * @return the total memory space in GB.
     */
    public double getInternalSpace() {
        return (double) (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / 1024 / 1024 / 1024;
    }

    /**
     * @return the free memory space in GB.
     */
    public double getInternalFreeSpace() {
        return (double) (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / 1024 / 1024 / 1024;
    }
}
