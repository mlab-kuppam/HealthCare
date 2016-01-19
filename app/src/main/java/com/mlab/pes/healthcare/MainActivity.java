package com.mlab.pes.healthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends Activity {

    //Declaring Buttons
    Button add, update, sync;

    //Declaring variables
    public static boolean check_internet = true;

    //Declaring Database
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking if Internet is available
        if (check_internet && INTERNER_CHECK()) {
            showMessage("You have Internet Connection", "Please Sync now");
        }
        //Making sure that it appears only once when the app is opened.
        check_internet = false;
        //Creating DB
        db = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE, null);
        //Button initialization
        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        sync = (Button) findViewById(R.id.sync);
    }

    //To Add Schools and Students(Changing Intent to SchoolAdd Activity)
    public void ADD(View view) {
        Intent intent = new Intent(this, StudentAddActivity.class);
        startActivity(intent);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //To Update Health Details of particular student(Changing Intent to Update Activity
    public void UPDATE(View view) {
        Intent i = new Intent(this, UpdateActivity.class);
        startActivity(i);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_down_out);
    }

    //To Sync the details to the Cloud(Pushing Data to the Cloud)
    public void SYNC(View view) {
        boolean check = INTERNER_CHECK();
        if (check) {
            String t = "";
            ArrayList<String> tablenames = new ArrayList<String>();
            LinkedHashMap json_fin = new LinkedHashMap();
            try {

                Cursor r = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

                if (r.moveToFirst()) {
                    while (!r.isAfterLast()) {
                        tablenames.add(r.getString(r.getColumnIndex("name")));
                        r.moveToNext();
                    }
                }
                r.close();

                for (int i = 1; i < tablenames.size(); i++) {
                    String q = "SELECT * FROM " + tablenames.get(i);
                    Cursor c = db.rawQuery(q, null);
                    c.moveToFirst();
                    if (c != null) {
                        ArrayList array = new ArrayList();
                        do {
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
                            array.add(rows);
                        } while (c.moveToNext());
                        c.close();
                        json_fin.put(tablenames.get(i), array);
                    }
                }

                t = json_fin.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if (!t.equals("{}")) {
                GetXMLTask get = new GetXMLTask();
                get.execute(new LinkedHashMap[]{json_fin});
                for (int i = 0; i < tablenames.size(); i++) {
                    db.execSQL("DROP TABLE IF EXISTS " + tablenames.get(i));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Entries found", Toast.LENGTH_LONG).show();
            }
        } else {
            showMessage("Check Internet Connection", "Try again");
        }
    }

    //Method to check internet connection
    public boolean INTERNER_CHECK() {
        boolean isInternetPresent;
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        return isInternetPresent;
    }

    //Method to create the dialog box
    //@params title and message
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
