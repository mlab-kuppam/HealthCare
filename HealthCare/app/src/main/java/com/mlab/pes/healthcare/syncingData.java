package com.mlab.pes.healthcare;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class syncingData extends ActionBarActivity {

    static TextView displayMessage;
    ProgressBar progBar;

    private Handler mHandler = new Handler();

    boolean processing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncing_data);

        progBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progBar != null) {
            progBar.setVisibility(View.VISIBLE);
            progBar.setIndeterminate(true);
            progBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        }


        displayMessage=(TextView) findViewById(R.id.displayMessage);


        processing=true;
        process();
    }

    public void process(){
        displayMessage.setText("Connecting to the Server");
        int timeout=10500;
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (MainActivity.connected) {
                    syncing a = new syncing();
                    a.SYNC();
                    progBar.setVisibility(View.INVISIBLE);
                } else {
                    onBackPressed();
                    MainActivity.showMessage("Warning", "Please check if server is connected to the internet. \nRestart the App and try again", MainActivity.get());
                }
                processing=false;
            }
        }, timeout);
    }
    @Override
    public void onBackPressed() {
        if(!processing)
        {
            super.onBackPressed();
        }
    }
    public static void addText(String a){
        String prev=(String)displayMessage.getText();
        displayMessage.setText(prev+"\n"+a);
    }
}
