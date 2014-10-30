package com.branbron.bagos.airquality;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bagos on 10/30/2014.
 */
public class MAP extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        final Button openMAP = (Button) findViewById(R.id.openMap);
        openMAP.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View Arg0){
                setContentView(R.layout.map);
            }
        });
    }

}
