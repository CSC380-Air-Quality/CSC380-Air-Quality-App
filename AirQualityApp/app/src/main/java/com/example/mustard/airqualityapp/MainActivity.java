package com.example.mustard.airqualityapp;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity{
    TabHost th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        th = (TabHost) findViewById(R.id.tabHost);
        TabHost.TabSpec spec = th.newTabSpec("Tab1");//tabhost object
        spec.setContent(R.id.button);
        spec.setIndicator("Tab 1 Button");//Set Title
        th.addTab(spec);

        TabHost.TabSpec spec2 = th.newTabSpec("Tab2");//tabhost object
        spec.setContent(R.id.textView);
        spec.setIndicator("Tab 2 Text View");//Set Title
        th.addTab(spec2);

        TabHost.TabSpec spec3 = th.newTabSpec("Tab3");//tabhost object
        spec.setContent(R.id.switch1);
        spec.setIndicator("Tab 3 Switch");//Set Title
        th.addTab(spec3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}