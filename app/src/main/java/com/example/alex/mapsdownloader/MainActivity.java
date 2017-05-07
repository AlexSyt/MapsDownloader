package com.example.alex.mapsdownloader;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parser.loadData(this);

        IndicatorFragment indicatorFragment = new IndicatorFragment();
        ListFragment listFragment = new ListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.indicator_fragment, indicatorFragment);
        transaction.add(R.id.list_fragment, listFragment);

        transaction.commit();
    }
}
