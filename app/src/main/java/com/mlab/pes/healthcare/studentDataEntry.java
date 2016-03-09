package com.mlab.pes.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class studentDataEntry extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data_entry);
    }

    //Method to change intent to root MainActivity
    public void backIntent() {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

    public void CHANGE(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.school:
                intent = new Intent(this, SchoolDetails.class);
                break;
            case R.id.add:
                intent = new Intent(this, StudentDetails.class);
                break;
            case R.id.update:
                intent = new Intent(this, SocioDemographicDetails.class);
                break;
        }
        startActivity(intent);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
