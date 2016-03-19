package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
                startActivity(intent);
                //Transition Animation
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.add:
                intent = new Intent(this, StudentDetails.class);
                startActivity(intent);
                //Transition Animation
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.update:
                intent = new Intent(this, SocioDemographicDetails.class);
                startActivity(intent);
                //Transition Animation
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.gps:
                selectDialog();
        }
    }
    int selectedVal=0;

    public void onRadioClick(View view){
        switch (view.getId()){
            case R.id.schoolRadio:
                selectedVal=1;
                break;
            case R.id.childRadio:
                selectedVal=2;
                break;
        }
    }

    //Method to create studentId dialog box
    public void selectDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        // Get the layout inflater
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.select_gps_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);
        //Add action buttons

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                switch (selectedVal){
                    case 1:dialog.dismiss();
                        Intent school=new Intent(studentDataEntry.this,schoolGPS.class);
                        startActivity(school);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;
                    case 2:dialog.dismiss();
                        Intent child=new Intent(studentDataEntry.this,getGPSLocation.class);
                        startActivity(child);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Please Select an Option ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
