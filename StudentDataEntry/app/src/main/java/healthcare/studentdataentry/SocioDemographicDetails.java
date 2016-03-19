package healthcare.studentdataentry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SocioDemographicDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner typeOfFamily,socioEconomicClass,typeOfHouse,flooring,waterSupply,garbageDisposal,overNight;

    int familyType,socioEconomic,TypeOfHouse,Flooring,WaterSupply,GarbageDisposal,OverNight;

    EditText address,landline,mobile,numberOfFamilyMembers,titleHOF,aadharHOF,educationHOF,occupationHOF,
            educationFather,occupationFather,educationMother,occupationMother,totalIncome,frequency;

    int overcrowding=2,crossVentilation=2,adequateLighting=2,kitchenWithSink=2,
            hygenicSurroundings=2,sanitaryLatrine=2,availaingNearBy=2;


    String sid;

    SQLiteDatabase database;

    String table_query=
            "  child_id VARCHAR[11] ," +
                    "  address VARCHAR[140] ," +
                    "  landline VARCHAR[11] ," +
                    "  mobile VARCHAR[11] ," +
                    "  type INTEGER[1] ," +
                    "  number_members INTEGER[2] ," +
                    "  hf_title VARCHAR[20] ," +
                    "  hf_aadhar VARCHAR[12] ," +
                    "  hf_education VARCHAR[140] ," +
                    "  hf_occupation VARCHAR[140] ," +
                    "  f_education VARCHAR[140] ," +
                    "  f_occupation VARCHAR[140] ," +
                    "  m_education VARCHAR[140] ," +
                    "  m_occupation VARCHAR[140] ," +
                    "  total_income INTEGER[8] ," +
                    "  socio_economic INTEGER[1] ," +
                    "  hs_type INTEGER[1] ," +
                    "  hs_flooring INTEGER[1] ," +
                    "  r_overcrowding INTEGER[1] ," +
                    "  cross_ventilation INTEGER[1] ," +
                    "  lighting INTEGER[1] ," +
                    "  kitchen INTEGER[1] ," +
                    "  water INTEGER[1] ," +
                    "  hygenic_surroundings INTEGER[1] ," +
                    "  sanitary_latrine INTEGER[1] ," +
                    "  garbage_disposal INTEGER[1], " +
                    "  availing_health INTEGER[1] ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio_demographic_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        studentidDialog();

        address=(EditText) findViewById(R.id.Address);
        landline=(EditText) findViewById(R.id.landline);
        mobile=(EditText) findViewById(R.id.mobile);
        numberOfFamilyMembers=(EditText) findViewById(R.id.numberOfFamilyMembers);
        titleHOF=(EditText) findViewById(R.id.headOfFamilyTitle);
        aadharHOF=(EditText) findViewById(R.id.headOfFamilyAadhar);
        educationHOF=(EditText) findViewById(R.id.headOfFamilyEducation);
        occupationHOF=(EditText) findViewById(R.id.headOfFamilyOccupation);
        educationFather=(EditText) findViewById(R.id.fatherEducation);
        occupationFather=(EditText) findViewById(R.id.fatherOccupation);
        educationMother=(EditText) findViewById(R.id.motherEducation);
        occupationMother=(EditText) findViewById(R.id.motherOccupation);
        totalIncome=(EditText) findViewById(R.id.totalIncome);



        typeOfFamily = (Spinner) findViewById(R.id.typeOfFamily);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeOfFamily_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeOfFamily.setAdapter(adapter);
        typeOfFamily.setOnItemSelectedListener(this);

        socioEconomicClass = (Spinner) findViewById(R.id.socioEconomicClass);
        adapter = ArrayAdapter.createFromResource(this, R.array.socioEconomicClass_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        socioEconomicClass.setAdapter(adapter);
        socioEconomicClass.setOnItemSelectedListener(this);

        typeOfHouse = (Spinner) findViewById(R.id.typeOfHouse);
        adapter = ArrayAdapter.createFromResource(this, R.array.typeOfHouse_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeOfHouse.setAdapter(adapter);
        typeOfHouse.setOnItemSelectedListener(this);

        flooring = (Spinner) findViewById(R.id.flooring);
        adapter = ArrayAdapter.createFromResource(this, R.array.flooring_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        flooring.setAdapter(adapter);
        flooring.setOnItemSelectedListener(this);

        waterSupply = (Spinner) findViewById(R.id.waterSupply);
        adapter = ArrayAdapter.createFromResource(this, R.array.waterSupply_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        waterSupply.setAdapter(adapter);
        waterSupply.setOnItemSelectedListener(this);

        garbageDisposal = (Spinner) findViewById(R.id.garbageDisposal);
        adapter = ArrayAdapter.createFromResource(this, R.array.garbageDisposal, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        garbageDisposal.setAdapter(adapter);
        garbageDisposal.setOnItemSelectedListener(this);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS socio_demographic(" + table_query + ")");
    }

    //Method to get selected item in the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == typeOfFamily) {
            familyType = position - 1;
        }
        else if (parent == socioEconomicClass){
            socioEconomic=position -1;
        }
        else if (parent == typeOfHouse){
            TypeOfHouse=position -1;
        }
        else if (parent == flooring){
            Flooring=position -1;
        }
        else if (parent == waterSupply){
            WaterSupply=position -1;
        }
        else if (parent == garbageDisposal){
            GarbageDisposal=position -1;
        }
        else if (parent == overNight){
            OverNight=position -1;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.socio_demographic_details, menu);
        return true;
    }

    public void onRadioClick(View view){
        switch (view.getId()){
            //overcrowding
            case R.id.overcrowdingYes :
                overcrowding=1;
                break;
            case R.id.overcrowdingNo :
                overcrowding=0;
                break;
            //cross Ventilation
            case R.id.crossVentilationYes :
                crossVentilation=1;
                break;
            case R.id.crossVentilationNo :
                crossVentilation=0;
                break;
            //adequate lighting
            case R.id.adequateLightingYes :
                adequateLighting=1;
                break;
            case R.id.adequateLightingNo :
                adequateLighting=0;
                break;
            //Kitchen With Sink
            case R.id.kitchenSkinYes :
                kitchenWithSink=1;
                break;
            case R.id.kitchenSkinNo :
                kitchenWithSink=0;
                break;
            //hygenic Surroundings
            case R.id.hygenicSurroundingsYes :
                hygenicSurroundings=1;
                break;
            case R.id.hygenicSurroundingsNo
                    :hygenicSurroundings=0;
                break;
            //sanitary Latrine
            case R.id.sanitaryLatrinesYes :
                sanitaryLatrine=1;
                break;
            case R.id.sanitaryLatrinesNo :
                sanitaryLatrine=0;
                break;

            case R.id.healthCareYes:
                availaingNearBy=1;
                break;
            case R.id.healthCareNo:
                availaingNearBy=0;
                break;        }
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
                    ((TextView) findViewById(R.id.studentID)).setText(sid);
                    //Toast.makeText(getApplicationContext(), "Student ID: " + sid, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showError() {
        Toast.makeText(this, "Enter a Valid Student ID", Toast.LENGTH_LONG).show();
    }



    //Adding details collected to SQLite
    public void ADD() {


        if (address.getText().toString().trim().length() == 0 ) {
            showMessage("Error", "Please Enter Home Address");
            return;
        }else if (!((mobile.getText().toString().trim().length() == 10 && landline.getText().toString().trim().length() == 0)
                || (landline.getText().toString().trim().length() == 10 && mobile.getText().toString().trim().length() == 0)
                || (landline.getText().toString().trim().length() == 10 && mobile.getText().toString().trim().length() == 10))){
            showMessage("Error", "Please enter a valid Mobile/Landline number");
            return;
        } else if (familyType==-1) {
            showMessage("Error", "Please Select the Type of Family");
            return;
        }else if (numberOfFamilyMembers.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the number of Family Memebers");
            return;
        }else if (titleHOF.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Title of the Head of the Family");
            return;
        }else if (!(aadharHOF.getText().toString().trim().length() == 0 || aadharHOF.getText().toString().trim().length() == 12)) {
            showMessage("Error", "Please Enter a Valid Aadhar Number");
            return;
        }else if (educationHOF.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Education qualification(s) of the Head of the Family");
            return;
        }else if (occupationHOF.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Occupation(s) of the Head of the Family");
            return;
        }else if (educationFather.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Education qualification(s) of the Father");
            return;
        }else if (occupationFather.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Occupation(s) of the Father");
            return;
        }else if (educationMother.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Education qualification(s) of the Mother");
            return;
        }else if (occupationMother.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Occupation(s) of the Mother");
            return;
        }else if (totalIncome.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please Enter the Total Income of the Family in INR");
            return;
        }else if (socioEconomic == -1) {
            showMessage("Error", "Please Select the Socio-Economic Class");
            return;
        }else if (TypeOfHouse == -1) {
            showMessage("Error", "Please Select the Type of House in Housing Standards");
            return;
        }else if (Flooring == -1) {
            showMessage("Error", "Please Select the Type of Flooring in Housing Standards");
            return;
        }else if (overcrowding == 2) {
            showMessage("Error", "Please Select an Option for Overcrowding in Housing Standards");
            return;
        }else if (crossVentilation == 2) {
            showMessage("Error", "Please Select an Option for Cross Ventilation in Housing Standards");
            return;
        }else if ( adequateLighting  == 2) {
            showMessage("Error", "Please Select an Option for Adequate Lighting in Housing Standards");
            return;
        }else if (kitchenWithSink ==2) {
            showMessage("Error", "Please Select an Option for Kitchen with Sink in Housing Standards");
            return;
        }else if (WaterSupply == -1) {
            showMessage("Error", "Please Select the Source of Water Supply in Housing Standards");
            return;
        }else if (hygenicSurroundings == 2) {
            showMessage("Error", "Please Select an Option for Hygenic Surroundings in Housing Standards");
            return;
        }else if (sanitaryLatrine == 2) {
            showMessage("Error", "Please Select an Option for Sanitary Latrine in Housing Standards");
            return;
        }else if (GarbageDisposal == -1) {
            showMessage("Error", "Please Select an Option for Garbage Disposal in Housing Standards");
            return;
        }else if (availaingNearBy == 2) {
            showMessage("Error", "Please Select an Option for Availing Near-By Health Care Services in Housing Standards");
            return;
        }
        else {
            //If the required fields are filled then the DB is updated
            //creating insertion query
            /*
            int no_rooms=0;
            int no_households=0;
            if(isInteger(numberOfRooms.getText().toString().trim())){
                no_rooms=Integer.parseInt(numberOfRooms.getText().toString().trim());
            }
            if(isInteger(numberOfHouseholds.getText().toString().trim())){
                no_households=Integer.parseInt(numberOfHouseholds.getText().toString().trim());
            }
*/
            try {
                String insert_query = "'" + sid + "'," +
                        "'" + address.getText().toString().trim() + "'," +
                        "'" + landline.getText().toString().trim() + "'," +
                        "'" + mobile.getText().toString().trim() + "'," +
                        "'" + familyType + "'," +
                        "'" + Integer.parseInt(numberOfFamilyMembers.getText().toString().trim()) + "'," +
                        "'" + titleHOF.getText().toString().trim() + "'," +
                        "'" + aadharHOF.getText().toString().trim() + "'," +
                        "'" + educationHOF.getText().toString().trim() + "'," +
                        "'" + occupationHOF.getText().toString().trim() + "'," +
                        "'" + educationFather.getText().toString().trim() + "'," +
                        "'" + occupationFather.getText().toString().trim() + "'," +
                        "'" + educationMother.getText().toString().trim() + "'," +
                        "'" + occupationMother.getText().toString().trim() + "'," +
                        "'" + totalIncome.getText().toString().trim() + "'," +
                        "'" + socioEconomic + "'," +
                        "'" + TypeOfHouse + "'," +
                        "'" + Flooring + "'," +
                        "'" + overcrowding + "'," +
                        "'" + crossVentilation + "'," +
                        "'" + adequateLighting + "'," +
                        "'" + kitchenWithSink + "'," +
                        "'" + WaterSupply + "'," +
                        "'" + hygenicSurroundings + "'," +
                        "'" + sanitaryLatrine + "'," +
                        "'" + GarbageDisposal + "',"+
                        "'" + availaingNearBy + "'";

                //inserting into database
                database.execSQL("INSERT INTO socio_demographic VALUES (" + insert_query + ")");


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

            }
            catch(Exception e){
                Log.d("error", e.toString() + " OCCURED !!!!");
                e.printStackTrace();
            }
            finally {
                //closing database
                database.close();

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_next:
                ADD();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
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

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public void Intent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                try
                {
                    database.delete("child", "child_id = ?", new String[] { StudentDetails.sid });
                }
                catch(Exception e)
                {
                    showMessage("Error","Child Id "+StudentDetails.sid+" not found!");
                }
                finally
                {
                    //database.close();
                }
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

    //Method to change intent to root MainActivity
    public void backIntent() {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

}
