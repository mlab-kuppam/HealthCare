package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class HealthActivity1 extends Activity {

    //Declaring sid -> studentID(must)
    static String health_sid;

    EditText Height, Weight, Waist, Hip, Pulse_Rate, Resp_Rate, Bp, Nail, Bath, Groom, Oral, Sanitary, Dental, Fluorosis, Gingi, ulcer, Oral_Other, Lung, Resp_other, Heart;

    RelativeLayout San;

    String[] checkboxitem = new String[]{"Applicable", "Not Applicable"};

    String age;

    int nails = 10, bath = 10, groom = 10, oral = 10, men = 10, san = 10, dent = 10, flu = 10, ging = 10, ulc = 10, lung = 10, heart = 10, check = 10;

    TextView Std_id;

    CheckBox Applicable, NotApplicable;

    Spinner Anaemia_drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health1);

        //Invoking StudentID Dialog box
        studentidDialog();

        Height = (EditText) findViewById(R.id.height);
        Weight = (EditText) findViewById(R.id.weight);
        Waist = (EditText) findViewById(R.id.waist);
        Hip = (EditText) findViewById(R.id.hip);
        Pulse_Rate = (EditText) findViewById(R.id.pulse);
        Resp_Rate = (EditText) findViewById(R.id.rrate);
        Bp = (EditText) findViewById(R.id.bp);
        Nail = (EditText) findViewById(R.id.nail_text);
        Bath = (EditText) findViewById(R.id.bath_text);
        Groom = (EditText) findViewById(R.id.groom_text);
        Oral = (EditText) findViewById(R.id.oral_text);
        Sanitary = (EditText) findViewById(R.id.san_text);
        Dental = (EditText) findViewById(R.id.dental_text);
        Fluorosis = (EditText) findViewById(R.id.fluoro_text);
        Gingi = (EditText) findViewById(R.id.gingi_text);
        ulcer = (EditText) findViewById(R.id.oralul_text);
        Oral_Other = (EditText) findViewById(R.id.sys_text);
        Lung = (EditText) findViewById(R.id.lung_text);
        Resp_other = (EditText) findViewById(R.id.resp_text);
        Heart = (EditText) findViewById(R.id.heart_text);

        Std_id = (TextView)findViewById(R.id.hlt_std_id);

        Applicable = (CheckBox) findViewById(R.id.app);
        NotApplicable = (CheckBox) findViewById(R.id.notapp);


        San = (RelativeLayout) findViewById(R.id.sanitary);


        Anaemia_drop = (Spinner) findViewById(R.id.anaemia_drop);

        String[] symp = new String[]{"Mild", "Moderate", "Severe"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, symp);
        Anaemia_drop.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            Next();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioselect(View v) {
        switch (v.getId()) {
            case R.id.nail_trim:
                nails = 1;
                Nail.setVisibility(v.GONE);
                break;
            case R.id.nail_nottrim:
                nails = 0;
                Nail.setVisibility(v.VISIBLE);
                break;

            case R.id.bath_reg:
                bath = 1;
                Bath.setVisibility(v.GONE);
                break;
            case R.id.bath_irr:
                bath = 0;
                Bath.setVisibility(v.VISIBLE);
                break;

            case R.id.groom_good:
                groom = 1;
                Groom.setVisibility(v.GONE);
                break;
            case R.id.groom_poor:
                groom = 0;
                Groom.setVisibility(v.VISIBLE);
                break;

            case R.id.oral_good:
                oral = 1;
                Oral.setVisibility(v.GONE);
                break;
            case R.id.oral_poor:
                oral = 0;
                Oral.setVisibility(v.VISIBLE);
                break;

            case R.id.san_yes:
                san = 1;
                Sanitary.setVisibility(v.VISIBLE);
                break;
            case R.id.san_no:
                san = 0;
                Sanitary.setVisibility(v.GONE);
                break;

            case R.id.dental_yes:
                dent = 1;
                Dental.setVisibility(v.VISIBLE);
                break;
            case R.id.dental_no:
                dent = 0;
                Dental.setVisibility(v.GONE);
                break;

            case R.id.fluoro_yes:
                flu = 1;
                Fluorosis.setVisibility(v.VISIBLE);
                break;
            case R.id.fluoro_no:
                flu = 0;
                Fluorosis.setVisibility(v.GONE);
                break;

            case R.id.gingi_yes:
                ging = 1;
                Gingi.setVisibility(v.VISIBLE);
                break;
            case R.id.gingi_no:
                ging = 0;
                Gingi.setVisibility(v.GONE);
                break;

            case R.id.oralul_yes:
                ulc = 1;
                ulcer.setVisibility(v.VISIBLE);
                break;
            case R.id.oralul_no:
                ulc = 0;
                ulcer.setVisibility(v.GONE);
                break;

            case R.id.lung_yes:
                lung = 1;
                Lung.setVisibility(v.GONE);
                break;
            case R.id.lung_no:
                lung = 0;
                Lung.setVisibility(v.VISIBLE);
                break;

            case R.id.heart_yes:
                heart = 1;
                Heart.setVisibility(v.VISIBLE);
                break;
            case R.id.heart_no:
                heart = 0;
                Heart.setVisibility(v.GONE);
                break;

        }
    }

    public void onCheckBoxclicked(View v) {
        switch (v.getId()) {
            case R.id.app:
                check = 1;
                age = checkboxitem[0];
                San.setVisibility(v.VISIBLE);
                NotApplicable.setChecked(false);
                //Applicable.setChecked(true);
                break;
            case R.id.notapp:
                check = 0;
                age = checkboxitem[1];
                San.setVisibility(v.GONE);
                Applicable.setChecked(false);
                //NotApplicable.setChecked(true);
                break;
        }
    }

    public void Next() {
        if (nails == 10 || bath == 10 || groom == 10 || oral == 10 || check == 10 || (check ==10 && san == 10) || dent == 10 || flu == 10 ||
                ging == 10 || ulc == 10 || lung == 10 || heart == 10 ||
                Height.getText().toString().length()==0 || Weight.getText().toString().length()==0 || Waist.getText().toString().length()==0 ||
                Hip.getText().toString().length()==0 ||Pulse_Rate.getText().toString().length()==0 ||Resp_Rate.getText().toString().length()==0 ||
                Bp.getText().toString().length()==0 ) {

            showMessage("Warning", "Please enter all values");
            return;
        }

        Intent b = new Intent(this, HealthActivity2.class);
        startActivity(b);
    }

    //Method to create studentId dialog box
    public void studentidDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Student ID");
        // Get the layout inflater
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.mydialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);
        final EditText studentID = (EditText) dialogView.findViewById(R.id.studid);
        // Add action buttons

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                health_sid = studentID.getText().toString();
                //System.out.println(sid);
                if (health_sid.length() <= 1) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Student ID: " + health_sid, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Getback();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showError() {
        Toast.makeText(this, "Enter Student ID", Toast.LENGTH_LONG).show();
    }

    public void setStudentID() {
        //studentID.setText(AddActivity.sid);
    }

    public void Backpress() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Current Data Will be Lost");
        // Get the layout inflater
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        // Add action buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //If the entry is cancelled, this code will delete the images of the session
                deletePicture();
                Getback();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void Getback() {
        Intent i = new Intent(this, UpdateActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Backpress();
    }

    public void deletePicture(){
        //If the entry is cancelled, this code will delete the images of the session
        File file = new File(Environment.getExternalStorageDirectory(), "Images");
        if(file.exists())
        {
            int x;
            //loop will delete all photos taken during the cancelled session
            for(x = 0; x < HealthActivity2.pic_count_health; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/"+health_sid+"_health_"+x+".jpg");
                //System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }

}
