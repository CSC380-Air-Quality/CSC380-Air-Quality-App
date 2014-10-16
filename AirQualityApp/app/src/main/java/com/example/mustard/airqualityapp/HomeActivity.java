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

public class HomeActivity extends TabActivity {

    private TabHost th;
    private Button btnUpdate;
    private TextView tvNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //this.btnUpdate = (Button) this.findViewById(R.id.btnUpdate);
        this.tvNO = (TextView) this.findViewById(R.id.textView);

        //th = (TabHost) findViewById(R.id.tabHost);
        th = getTabHost();
        TabHost.TabSpec spec = th.newTabSpec("Tab1");//tabhost object
        spec.setContent(R.id.button);
        spec.setIndicator("Map");
        th.addTab(spec);

        TabHost.TabSpec spec2 = th.newTabSpec("Tab2");//tabhost object
        spec2.setContent(R.id.switch1);
        spec2.setIndicator("Home");
        th.addTab(spec2);

        TabHost.TabSpec spec3 = th.newTabSpec("Tab3");//tabhost object
        spec3.setContent(R.id.checkBox);
        spec3.setIndicator("Graph");
        th.addTab(spec3);

        th.setCurrentTab(1);










        /*this.btnUpdate.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(tvNO != null){
                    tvNO.setText("I changed the Text!!!! <3");
                }else{
                    System.err.println("Null Stuff");
                }

            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
