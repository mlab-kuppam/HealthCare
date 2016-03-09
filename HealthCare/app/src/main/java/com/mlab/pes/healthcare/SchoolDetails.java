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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SchoolDetails extends ActionBarActivity implements AdapterView.OnItemSelectedListener{


    EditText address,name,headMasterName,landline,mobile,email;
    int Category,Type,busyPlaces=2,hygenicSurroundings=2,fencing=2,playground=2,multiStoreyed=2,overcrowding=2,lab=2,kitchen=2,
            furniture=2,crossVentilation=2,adequateLighting=2,protectedWaterSupply,electricity=2,latrines=2,separate=2;

    LinearLayout separateFor;

    Spinner category,type,waterSupply;

    TextView schoolID;
    String schoolId;

    SQLiteDatabase database;

    String table_query=
            "  school_id VARCHAR[8] ," +
                    "  address VARCHAR[140] ," +
                    "  name VARCHAR[140] ," +
                    "  category INTEGER[1] ," +
                    "  type INTEGER[1] ," +
                    "  hm_name VARCHAR[140] ," +
                    "  landline FLOAT ," +
                    "  mobile FLOAT ," +
                    "  email VARCHAR[254] ," +
                    "  c_busy_places INTEGER[1] ," +
                    "  c_hygenic INTEGER[1] ," +
                    "  c_fencing INTEGER[1] ," +
                    "  c_playground INTEGER[1] ," +
                    "  c_stories INTEGER[1] ," +
                    "  c_overcrowding INTEGER[1] ," +
                    "  c_lab INTEGER[1] ," +
                    "  c_kitchen INTEGER[1] ," +
                    "  c_furniture INTEGER[1] ," +
                    "  c_cross_vent INTEGER[1] ," +
                    "  c_lighting INTEGER[1] ," +
                    "  c_water INTEGER[1] ," +
                    "  c_electricity INTEGER[1] ," +
                    "  c_latrines INTEGER[1] ," +
                    "  c_l_separate INTEGER[1] " ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        schoolidDialog();

        schoolID=(TextView) findViewById(R.id.schoolID);

        address=(EditText) findViewById(R.id.schoolAddress);
        name=(EditText) findViewById(R.id.schoolName);
        headMasterName=(EditText) findViewById(R.id.headMasterName);
        landline=(EditText) findViewById(R.id.landline);
        mobile=(EditText) findViewById(R.id.mobile);
        email=(EditText) findViewById(R.id.email);

        separateFor=(LinearLayout) findViewById(R.id.separate);

        category = (Spinner) findViewById(R.id.catergory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);

        type = (Spinner) findViewById(R.id.typeSchool);
        adapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

        waterSupply = (Spinner) findViewById(R.id.waterSupply);
        adapter = ArrayAdapter.createFromResource(this, R.array.waterSupply_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        waterSupply.setAdapter(adapter);
        waterSupply.setOnItemSelectedListener(this);

        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS school(" + table_query + ")");


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


    @Override
    public void onBackPressed() {
        backDialog();
    }

    //Method to create alert dialog when back pressed in middle of the activity
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
                backIntent();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_school_details, menu);
        return true;
    }


    public void onRadioClick(View view){
        switch (view.getId()){
            case R.id.busyPlacesYes :
                busyPlaces=1;
                break;
            case R.id.busyPlacesNo :
                busyPlaces=0;
                break;

            case R.id.hygenicSurroundingsYes :
                hygenicSurroundings=1;
                break;
            case R.id.hygenicSurroundingsNo :
                hygenicSurroundings=0;
                break;

            case R.id.fencingYes :
                fencing=1;
                break;
            case R.id.fencingNo :
                fencing=0;
                break;

            case R.id.playgroundYes :
                playground=1;
                break;
            case R.id.playgroundNo :
                playground=0;
                break;


            case R.id.multistoreyedYes :
                multiStoreyed=1;
                break;
            case R.id.multistoreyedNo :
                multiStoreyed=0;
                break;

            case R.id.overcrowdingYes :
                overcrowding=1;
                break;
            case R.id.overcrowdingNo :
                overcrowding=0;
                break;

            case R.id.labYes :
                lab=1;
                break;
            case R.id.labNo :
                lab=0;
                break;

            case R.id.kitchenYes :
                kitchen=1;
                break;
            case R.id.kitchenNo :
                kitchen=0;
                break;

            case R.id.furnitureYes :
                furniture=1;
                break;
            case R.id.furnitureNo :
                furniture=0;
                break;

            case R.id.crossVentilationYes :
                crossVentilation=1;
                break;
            case R.id.crossVentilationNo :
                crossVentilation=0;
                break;

            case R.id.adequateLightingYes :
                adequateLighting=1;
                break;
            case R.id.adequateLightingNo :
                adequateLighting=0;
                break;

            case R.id.electricityYes :
                electricity=1;
                break;
            case R.id.electricityNo :
                electricity=0;
                break;

            case R.id.sanitaryLatrinesYes :
                latrines=1;
                separateFor.setVisibility(View.VISIBLE);
                break;
            case R.id.sanitaryLatrinesNo :
                latrines=0;
                separateFor.setVisibility(View.GONE);
                break;

            case R.id.separateForBoysAndGirlsYes :
                separate=1;
                break;
            case R.id.separateForBoysAndGirlsNo :
                separate=0;
                break;
        }
    }

    public void Next() {
        if (address.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter Address");
            return;
        } else if (name.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter School name");
            return;
        } else if (Category == -1) {
            showMessage("Error", "Please Select an option for Category of School");
            return;
        } else if (Type == -1) {
            showMessage("Error", "Please Select an option for Type of School");
            return;
        } else if (headMasterName.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter Headmaster/Class Teacher name");
            return;
        } else if (!((mobile.getText().toString().trim().length() == 10 && landline.getText().toString().trim().length() == 0)
                || (landline.getText().toString().trim().length() == 10 && mobile.getText().toString().trim().length() == 0)
                || (landline.getText().toString().trim().length() == 10 && mobile.getText().toString().trim().length() == 10))) {
            showMessage("Error", "Please enter a valid Mobile/Landline number");
            return;
        } else if (email.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter Email ID");
            return;
        } else if (busyPlaces == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Not near-by Busy Places");
            return;
        } else if (hygenicSurroundings == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Hygenic Surroundings");
            return;
        } else if (fencing == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Fencing");
            return;
        } else if (playground == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Playground");
            return;
        } else if (multiStoreyed == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Not Multi-Storeyed");
            return;
        } else if (overcrowding == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - No Overcrowding");
            return;
        } else if (lab == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Lab/Art Room");
            return;
        } else if (kitchen == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Kitchen");
            return;
        } else if (furniture == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Furniture");
            return;
        } else if (crossVentilation == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Cross Ventilation");
            return;
        } else if (adequateLighting == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Adequate Lighting");
            return;
        } else if (protectedWaterSupply == -1) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Protected Water Supply");
            return;
        } else if (electricity == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Electricity");
            return;
        } else if (latrines == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Latrines");
            return;
        } else if (latrines == 1 && separate == 2) {
            showMessage("Error", "Please Select an option for Criteria for Healthful School - Latrines - Separate for Boys and Girls");
            return;
        } else {


            try {
                //If the required fields are filled then the DB is updated
                //creating insertion query
                String insert_query = "'" + schoolId + "'," +
                        "'" + address.getText().toString().trim() + "'," +
                        "'" + name.getText().toString().trim() + "'," +
                        "'" + Category + "'," +
                        "'" + Type + "'," +
                        "'" + headMasterName.getText().toString().trim() + "'," +
                        "'" + landline.getText().toString().trim() + "'," +
                        "'" + mobile.getText().toString().trim() + "'," +
                        "'" + email.getText().toString().trim() + "'," +
                        "'" + busyPlaces + "'," +
                        "'" + hygenicSurroundings + "'," +
                        "'" + fencing + "'," +
                        "'" + playground + "'," +
                        "'" + multiStoreyed + "'," +
                        "'" + overcrowding + "'," +
                        "'" + lab + "'," +
                        "'" + kitchen + "'," +
                        "'" + furniture + "'," +
                        "'" + crossVentilation + "'," +
                        "'" + adequateLighting + "'," +
                        "'" + protectedWaterSupply + "'," +
                        "'" + electricity + "'," +
                        "'" + latrines + "'," +
                        "'" + separate + "'";

                //inserting into database
                database.execSQL("INSERT INTO school VALUES (" + insert_query + ")");

                insert_query = "'" + schoolId + "'," +
                        "'" + name.getText().toString().trim() + "'";
                database.execSQL("INSERT INTO school_references VALUES (" + insert_query + ")");


                //sending confirmation that data is inserted
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Success");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent();
                    }
                });
                builder.setCancelable(false);
                builder.setMessage("Entry Successfully Added!");
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.close();
            }
        }
    }
    public void Intent() {
        Intent intent = new Intent(this, studentDataEntry.class);
        startActivity(intent);
    }
    boolean checkSchool=false;
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

        //Validating School ID
        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String school=schoolID.getText().toString();
                if(school.length()==8){
                    //change the parameters for checking
                    boolean mandalCheck= Integer.parseInt(school.substring(0, 1))<=5 && Integer.parseInt(school.substring(0, 1))>=0;
                    boolean panchayatCheck= Integer.parseInt(school.substring(1, 3))<=99 && Integer.parseInt(school.substring(1, 3))>=0;
                    boolean thirdCheck= Integer.parseInt(school.substring(3, 5))<=99 && Integer.parseInt(school.substring(3, 5))>=0;
                    boolean schoolCheck= Integer.parseInt(school.substring(5))<=999 && Integer.parseInt(school.substring(5))>=0;

                    if(mandalCheck && panchayatCheck && thirdCheck && schoolCheck){
                        checkSchool= true;
                    }
                    else {
                        checkSchool= false;
                    }

                    errorMessage.setVisibility(View.VISIBLE);
                    if(checkSchool){
                        if(isSchoolID(school)) {
                            errorMessage.setText("School Already Exists! Choose a different School ID");
                        }
                        else{
                            errorMessage.setText("School ID Available");
                        }
                    }
                    else
                    {
                        errorMessage.setText("Please Enter a Valid School ID");
                    }
                }
                else if(school.length()<8)
                {
                    errorMessage.setVisibility(View.GONE);
                    checkSchool=false;
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
                if (!isSchoolID(schoolId) && checkSchool) {
                    setSchoolID();
                    dialog.dismiss();
                    checkSchool=false;
                } else {
                    showError();
                    schoolidDialog();
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
    //Method to change intent to root MainActivity
    public void backIntent() {
        Intent back = new Intent(this, studentDataEntry.class);
        startActivity(back);
    }
    public void showError() {

        Toast.makeText(this, "Enter a valid School ID", Toast.LENGTH_LONG).show();
    }
    public void setSchoolID() {
        schoolID.setText(schoolId);

        SharedPreferences.Editor mEditor = MainActivity.mPrefs.edit();
        mEditor.putString("school_id", schoolId).commit();
    }
    public boolean isSchoolID(String school_id){
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
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

    }

    //Method to get selected item in the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==category)
        {
            Category = position-1;
        }else if(parent==type){
            Type=position-1;
        }else if(parent==waterSupply){
            protectedWaterSupply=position-1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
