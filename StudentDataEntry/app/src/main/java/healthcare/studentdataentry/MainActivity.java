package healthcare.studentdataentry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    //Declaring Buttons
    Button add, sync;
    //Declaring variables
    public static boolean check_internet = true;
    static TextView syncStatus;
    //Declaring Database
    static SQLiteDatabase db;
    private static MainActivity app;
    static SharedPreferences mPrefs;
    static String url="";
    public static boolean connected=false;
    public static boolean threadStarted=false;
    public static ProgressDialog dialog;
    private Handler mHandler = new Handler();
    public static MainActivity get(){
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app=this;

        mPrefs=getSharedPreferences("label", 0);

        //Checking if Internet is available
        if (check_internet && INTERNER_CHECK()) {
            showMessage("You have an Internet Connection", "Please Sync now",get());
        }
        //Making sure that it appears only once when the app is opened.
        check_internet = false;
        //Creating DB
        db = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE, null);


        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[20] ," +
                        "  image TEXT" ;
        //creating image table
        db.execSQL("CREATE TABLE IF NOT EXISTS images( " + image_table_query + " )");

        String child_table_query=
                "  child_id VARCHAR[10] ," +
                        "  name VARCHAR[140] ," +
                        "  gender VARCHAR[10]," +
                        "  father_name VARCHAR[140]";
        //creating image table
        db.execSQL("CREATE TABLE IF NOT EXISTS child_references( " + child_table_query + " )");

        String school_table_query=
                "  school_id VARCHAR[10] ," +
                        "  name VARCHAR[140] " ;
        //creating image table
        db.execSQL("CREATE TABLE IF NOT EXISTS school_references( " + school_table_query + " )");

        /*
        String insert_query = "'" + "11111111" + "'," +
                "'" + "School Name" + "'";
        System.out.println(insert_query);
        //inserting into database
        MainActivity.db.execSQL("INSERT INTO school_references VALUES (" + insert_query + ")");


        String insert_query_child = "'" + "11111111111" + "'," +
                "'" + "Name" + "',"+
                "'" + "Male" + "',"+
                "'" + "Father Name" + "'";
        System.out.println(insert_query);
        //inserting into database
        MainActivity.db.execSQL("INSERT INTO child_references VALUES (" + insert_query_child + ")");*/


        syncStatus=(TextView) findViewById(R.id.syncStatus);

        String mString = mPrefs.getString("status", "Not Synced");
        syncStatus.setText(mString);


        //Button initialization
        add = (Button) findViewById(R.id.add);
        //update = (Button) findViewById(R.id.update);
        sync = (Button) findViewById(R.id.sync);
    }

    //To Add Schools and Students(Changing Intent to SchoolAdd Activity)
    public void ADD(View view) {

        Intent intent = new Intent(this, StudentDetails.class);
        startActivity(intent);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
    public void school(View view) {

        Intent intent = new Intent(this, SchoolDetails.class);
        startActivity(intent);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
    public void RETRIEVE(View view) {
        boolean check = INTERNER_CHECK();
        if (check) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectorCheck connectorCheck=new connectorCheck();

                    connectorCheck.execute();
                    int timeout=10500;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (connected) {
                                syncing a = new syncing();
                                a.retrieve_child_data();
                                a.retrieve_school_data();
                            } else {
                                showMessage("Warning", "Please check if server is connected to the internet. \nRestart the App and try again", MainActivity.get());
                            }
                        }
                    }, timeout);
                }
            });

            connected=false;
        } else {
            showMessage("Check Internet Connection", "Try again", get());
        }
    }
    public void socio(View view) {

        Intent intent = new Intent(this, SocioDemographicDetails.class);
        startActivity(intent);
        //Transition Animation
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
    public void gps(View view) {
        selectDialog();
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
                        Intent school=new Intent(MainActivity.this,schoolGPS.class);
                        startActivity(school);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;
                    case 2:dialog.dismiss();
                        Intent child=new Intent(MainActivity.this,getGPSLocation.class);
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
    //To Sync the details to the Cloud(Pushing Data to the Cloud)
    public void SYNC(View view) {
        boolean check = INTERNER_CHECK();
        if (check) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        connectorCheck connectorCheck=new connectorCheck();
                        connectorCheck.execute();

                        Intent intent = new Intent(MainActivity.this, syncingData.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    /*                    int timeout=10500;
                        mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (connected) {
                                Intent intent=new Intent(MainActivity.this,syncingData.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                            } else {
                                showMessage("Warning", "Please check if server is connected to the internet. \nRestart the App and try again", MainActivity.get());
                            }
                        }
                    }, timeout);
        */
                }
            });
            connected=false;
        } else {
            showMessage("Check Internet Connection", "Try again", get());
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
    public static void showMessage(String title, String message,Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.show();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
