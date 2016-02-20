package com.mlab.pes.healthcare;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

public class UpdateActivity extends ActionBarActivity {

    SQLiteDatabase database;

    static String schoolId;

    TextView schoolID,schoolName;
    String[] valuesSchool;


    static SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mPrefs=getSharedPreferences("label", 0);


        schoolID=(TextView) findViewById(R.id.schoolIdDisplay);
        schoolName=(TextView) findViewById(R.id.schoolNameDisplay);

        if(getIntent().hasExtra("caller")) {
            schoolidDialog();
        }
        else{
            schoolID.setText(mPrefs.getString("school_id", ""));
            schoolName.setText(mPrefs.getString("school_name",""));
        }

        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[20] ," +
                        "  image TEXT" ;
        //creating image table
        database.execSQL("CREATE TABLE IF NOT EXISTS images( " + image_table_query + " )");
    }
    static boolean checkSchool=false;
    //Method to create studentId dialog box
    public void schoolidDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter School ID");
        // Get the layout inflater
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.school_id_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);
        final EditText schoolID = (EditText) dialogView.findViewById(R.id.schoolId);
        final TextView errorMessage=(TextView) dialogView.findViewById(R.id.ErrorMessage);
        final TextView schoolName=(TextView) dialogView.findViewById(R.id.schoolDetailsName);
        final LinearLayout schoolDetails=(LinearLayout) dialogView.findViewById(R.id.schoolDetails);

        //Validating School ID
        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String school=schoolID.getText().toString();
                if(school.length()==8){
                    //change the parameters for checking
                    boolean mandalCheck=Integer.parseInt(school.substring(0, 1))<=5 && Integer.parseInt(school.substring(0,1))>=0;
                    boolean panchayatCheck=Integer.parseInt(school.substring(1,3))<=99 && Integer.parseInt(school.substring(1,3))>=0;
                    boolean thirdCheck=Integer.parseInt(school.substring(3,5))<=99 && Integer.parseInt(school.substring(3,5))>=0;
                    boolean schoolCheck=Integer.parseInt(school.substring(5))<=999 && Integer.parseInt(school.substring(5))>=0;

                    if(mandalCheck && panchayatCheck && thirdCheck && schoolCheck){
                        checkSchool= true;
                    }
                    else {
                        checkSchool= false;
                    }
                    schoolDetails.setVisibility(View.VISIBLE);

                    if(isSchoolID(school)) {
                        valuesSchool=new String[2];
                        valuesSchool[0]=school;
                        valuesSchool=displaySchoolDetails(valuesSchool);
                        schoolName.setText(valuesSchool[1]);
                    }
                    else{
                        schoolDetails.setVisibility(View.GONE);
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("Please Enter a School ID which is Available");
                    }
                }
                else if(school.length()<8)
                {
                    schoolDetails.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        schoolID.addTextChangedListener(inputTextWatcher);
        //Add action buttons

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                schoolId = schoolID.getText().toString().toUpperCase();
                //System.out.println(skin_sid);
                if (!isSchoolID(schoolId)) {
                    showError();
                    schoolidDialog();
                } else {
                    setSchoolID();
                    dialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "Student ID: " + sid, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                backIntent();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void setSchoolID() {
        schoolID.setText(schoolId);
        schoolName.setText(valuesSchool[1]);

        SharedPreferences.Editor mEditor = MainActivity.mPrefs.edit();
        mEditor.putString("school_id", schoolId).commit();
        mEditor.putString("school_name", valuesSchool[1]).commit();
    }
    //Method to change intent to root MainActivity
    public void backIntent() {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }
    public void showError() {

        Toast.makeText(this, "Enter a valid School ID", Toast.LENGTH_LONG).show();
    }
    //Method to create the dialog box
    //@params title and message
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

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
    public static boolean isStudentID(String child_id){
        try {
            String q = "SELECT * FROM child_references WHERE child_id='" + child_id.toUpperCase() + "'";
            Cursor c = MainActivity.db.rawQuery(q, null);

            if (c != null && c.moveToFirst() && c.getString(c.getColumnIndex("child_id")).equalsIgnoreCase(child_id)) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isSchoolID(String school_id){
        try {
            String q = "SELECT * FROM school_references WHERE school_id='" + school_id.toUpperCase() + "'";
            Cursor c = MainActivity.db.rawQuery(q, null);

            if (c != null && c.moveToFirst() && c.getString(c.getColumnIndex("school_id")).equalsIgnoreCase(school_id)) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static String[] displayStudentDetails(String[] child_id){
        try {
            String q = "SELECT * FROM child_references WHERE child_id='" + child_id[0] + "'";
            Cursor c = MainActivity.db.rawQuery(q, null);
            c.moveToFirst();
            if(c != null && c.getCount() > 0) {
                LinkedHashMap rows = new LinkedHashMap();
                for (int l = 0; l < c.getColumnCount(); l++) {
                    String name = c.getColumnName(l);
                    switch (c.getType(l)) {
                        case Cursor.FIELD_TYPE_STRING:
                            rows.put(name, c.getString(l));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            rows.put(name, c.getInt(l));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            rows.put(name, c.getFloat(l));
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            rows.put(name, c.getBlob(l));
                            break;
                    }
                }
                //System.out.println(rows);

                //showMessage("Student Found!", "Student Name: " + rows.get("name") + "\nGender: " + rows.get("gender"));
                child_id[1]=rows.get("name").toString();
                child_id[2]=rows.get("gender").toString();

                return child_id;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public static String[] displaySchoolDetails(String[] child_id){
        try {
            String q = "SELECT * FROM school_references WHERE school_id='" + child_id[0] + "'";
            Cursor c = MainActivity.db.rawQuery(q, null);
            c.moveToFirst();
            if(c != null && c.getCount() > 0) {
                LinkedHashMap rows = new LinkedHashMap();
                for (int l = 0; l < c.getColumnCount(); l++) {
                    String name = c.getColumnName(l);
                    switch (c.getType(l)) {
                        case Cursor.FIELD_TYPE_STRING:
                            rows.put(name, c.getString(l));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            rows.put(name, c.getInt(l));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            rows.put(name, c.getFloat(l));
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            rows.put(name, c.getBlob(l));
                            break;
                    }
                }
                //System.out.println(rows);

                //showMessage("Student Found!", "Student Name: " + rows.get("name") + "\nGender: " + rows.get("gender"));
                child_id[1]=rows.get("name").toString();

                return child_id;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public static void StudentIDValidation(View dialogView){

        final EditText studentID = (EditText) dialogView.findViewById(R.id.studid);
        final LinearLayout studentDetails=(LinearLayout) dialogView.findViewById(R.id.studentDetails);
        final TextView studentDetailsName=(TextView) dialogView.findViewById(R.id.studentDetailsName);
        final TextView studentDetailsGender=(TextView) dialogView.findViewById(R.id.studentDetailsGender);
        final TextView errorMessage=(TextView) dialogView.findViewById(R.id.ErrorMessage);


        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                String sid=studentID.getText().toString().toUpperCase();

                if(sid.length()==11)
                {
                    studentDetails.setVisibility(View.VISIBLE);
                    if(isStudentID(sid)) {
                        String[] values=new String[3];
                        values[0]=sid;
                        values=displayStudentDetails(values);
                        studentDetailsName.setText(values[1]);
                        studentDetailsGender.setText(values[2]);

                    }
                    else{
                        studentDetails.setVisibility(View.GONE);
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("Please Enter a Student ID which is Available");
                    }
                }
                else if(sid.length()<11){
                    studentDetails.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };

        studentID.addTextChangedListener(inputTextWatcher);
    }
    public static boolean isConnectedToServer(String url) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}

