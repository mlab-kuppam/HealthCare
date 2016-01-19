package com.mlab.pes.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    //Method to change intent based on button clicked
    public void CHANGE(View view) {
        switch (view.getId()) {
            case R.id.eye:
                Intent j = new Intent(this, EyeActivity1.class);
                startActivity(j);
                break;
            case R.id.ent:
                Intent k = new Intent(this, ENTActivity.class);
                startActivity(k);
                break;
            case R.id.skin:
                Intent l = new Intent(this, SkinActivity.class);
                startActivity(l);
                break;
            case R.id.health:
                Intent o = new Intent(this, HealthActivity1.class);
                startActivity(o);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
