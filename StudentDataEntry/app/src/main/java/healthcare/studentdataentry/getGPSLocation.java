package healthcare.studentdataentry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class getGPSLocation extends AppCompatActivity implements LocationListener{
    String sid;
    TextView gpsStudentID;
    EditText latitude,longitude;
    ProgressDialog progressDialog;
    private LocationManager locationManager;
    SQLiteDatabase database;


    private Handler mHandler = new Handler();


    public String table_query="  child_id VARCHAR[11]," +
            "  lat TEXT, " +
            "  lon TEXT " ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gpslocation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        gpsStudentID = (TextView) findViewById(R.id.gps_std_id);
        latitude =(EditText) findViewById(R.id.latitude);
        longitude =(EditText) findViewById(R.id.longitude);
        studentidDialog();


        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE, null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS loc(" + table_query + ")");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_gpslocation, menu);
        return true;
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
            //Validating Student ID
            UpdateActivity.StudentIDValidation(dialogView);

            //Add action buttons

            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    sid = studentID.getText().toString().toUpperCase();
                    //System.out.println(skin_sid);
                    if (!UpdateActivity.isStudentID(sid)) {
                        showError();
                        studentidDialog();
                    } else {
                        dialog.dismiss();
                        gpsStudentID.setText(sid);

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
            progressDialog= ProgressDialog.show(getGPSLocation.this,"Fetching GPS Location","Please wait, Do not close the Application");
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
                    database.execSQL("INSERT INTO loc VALUES (" + insert_query + ")");

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

    }
