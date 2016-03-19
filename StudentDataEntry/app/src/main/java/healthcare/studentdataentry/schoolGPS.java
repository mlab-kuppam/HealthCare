package healthcare.studentdataentry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;

public class schoolGPS extends AppCompatActivity implements LocationListener {
    String sid;
    TextView gpsStudentID;
    EditText latitude,longitude;
    ProgressDialog progressDialog;
    private LocationManager locationManager;
    SQLiteDatabase database;

    TextView schoolID,schoolName;


    static String schoolId;

    private Handler mHandler = new Handler();


    public String table_query="  school_id VARCHAR[8]," +
            "  lat TEXT, " +
            "  lon TEXT " ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_gps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gpsStudentID = (TextView) findViewById(R.id.gps_std_id);
        latitude =(EditText) findViewById(R.id.latitude);
        longitude =(EditText) findViewById(R.id.longitude);
        schoolidDialog();


        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE, null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS loc_school(" + table_query + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_gpslocation, menu);
        return true;
    }


    String[] valuesSchool;
    public void schoolidDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter School ID");
        // Get the layout inflater
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.school_id_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent1 view because its going in the dialog layout
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

                Getback();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void setSchoolID() {
        gpsStudentID.setText(schoolId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_next:
                Next();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void Getback() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void showError() {
        Toast.makeText(this, "Enter a Valid Student ID", Toast.LENGTH_LONG).show();
    }

    public void backDialog() {

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


    public void startSearching(View view){
        progressDialog= ProgressDialog.show(schoolGPS.this,"Fetching GPS Location","Please wait, Do not close the Application");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000,   // 3 sec
                10, this);
    }

    public void Next(){
        if(latitude.getText().toString().length()==0){
            showMessage("Error", "Please Enter Latitude or Automatically Retrieve the Coordinates");
            return;
        }
        else if(longitude.getText().toString().length()==0){
            showMessage("Error", "Please Enter Longitude or Automatically Retrieve the Coordinates");
            return;
        }
        else {
            try {
                //creating insertion query
                String insert_query = "'" + ((TextView) findViewById(R.id.gps_std_id)).getText().toString().trim() + "'," +
                        "'" + latitude.getText().toString().trim() + "'," +
                        "'" + longitude.getText().toString().trim() + "'";


                //inserting into database
                database.execSQL("INSERT INTO loc_school VALUES (" + insert_query + ")");

                //sending confirmation that data is inserted
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Success");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Getback();
                    }
                });
                builder.setCancelable(false);
                builder.setMessage("Entry Successfully Added!");
                builder.show();

                sid="";
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                database.close();

            }
        }

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

    }

    @Override
    public void onBackPressed() {
        backDialog();
    }


    /************* Called after each 3 sec **********/
    @Override
    public void onLocationChanged(Location location) {

        String str = "Latitude: "+location.getLatitude()+"Longitude:"+location.getLongitude();

        latitude.setText(location.getLatitude() + "");
        longitude.setText(location.getLongitude() + "");


        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);



    }

    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/

        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

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
    public static String[] displaySchoolDetails(String[] child_id) {
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
}
