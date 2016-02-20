package com.mlab.pes.healthcare;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

public class UpdateActivity extends ActionBarActivity {

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[20] ," +
                        "  image TEXT" ;
        //creating image table
        database.execSQL("CREATE TABLE IF NOT EXISTS images( " + image_table_query + " )");
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

