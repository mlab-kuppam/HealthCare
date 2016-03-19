package healthcare.studentdataentry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StudentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    static String sid;
    TextView studentID,schoolID;
    static DatePicker dob;
    static EditText name,father_name,mother_name,guardian_name,attendance,performance,aadhar;
    Spinner standard,gender,academicPerformance;
    static String gen,std,acperf;

    static SQLiteDatabase database;


    private static StudentDetails app;



    String table_query=
            "  child_id VARCHAR[11] ," +
                    "  school_id VARCHAR[8] ," +
                    "  name VARCHAR[140] ," +
                    "  dob TEXT ," +
                    "  gender INTEGER[1] ," +
                    "  standard INTEGER[2] ," +
                    "  father_name VARCHAR[140] ," +
                    "  mother_name VARCHAR[140] ," +
                    "  guardian_name VARCHAR[140] ," +
                    "  attendance FLOAT ," +
                    "  academic VARCHAR[2],"+
                    "  aadhar VARCHAR[12]";


    public static StudentDetails get(){
        return app;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Invoking Dialog Box for studentID input
        studentidDialog();

        app=this;


        studentID= (TextView)findViewById(R.id.studentID);
        studentID.setText(sid);
        dob=(DatePicker) findViewById(R.id.age);
        dob.updateDate(2000, 0, 1);



        schoolID= (TextView) findViewById(R.id.schoolID);
        //schoolIdValidation();
        name= (EditText) findViewById(R.id.name);
        father_name = (EditText) findViewById(R.id.fatherName);
        mother_name = (EditText) findViewById(R.id.motherName);
        guardian_name = (EditText) findViewById(R.id.guardianName);
        attendance= (EditText) findViewById(R.id.attendance);
        aadhar = (EditText) findViewById(R.id.aadhar);

        gender = (Spinner) findViewById(R.id.gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);

        standard = (Spinner) findViewById(R.id.standard);
        adapter = ArrayAdapter.createFromResource(this, R.array.standard_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        standard.setAdapter(adapter);
        standard.setOnItemSelectedListener(this);

        academicPerformance = (Spinner) findViewById(R.id.academicPerformance);
        adapter = ArrayAdapter.createFromResource(this, R.array.academicPerf, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        academicPerformance.setAdapter(adapter);
        academicPerformance.setOnItemSelectedListener(this);

        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS child(" + table_query + ")");
    }


    public boolean childIdValidation(String child_id){

        boolean valid=false, mandal_check=false,panchayat_check=false,village_check=false,school_check=false;

        boolean len=child_id.length()==11;
        if(len) {
            mandal_check = Integer.parseInt(child_id.substring(0, 1)) <= 5 && Integer.parseInt(child_id.substring(0, 1)) > 0;
            panchayat_check = Integer.parseInt(child_id.substring(1, 3)) <= 99 && Integer.parseInt(child_id.substring(1, 3)) >= 0;
            village_check = Integer.parseInt(child_id.substring(3, 5)) <= 99 && Integer.parseInt(child_id.substring(3, 5)) >= 0;
            school_check = Integer.parseInt(child_id.substring(5, 8)) <= 999 && Integer.parseInt(child_id.substring(5, 8)) >= 0;
        }

        valid=len && mandal_check && panchayat_check && village_check && school_check;


        return valid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_details, menu);
        return true;
    }

    //Adding details collected to SQLite
    public void ADD() {
            if (name.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please Enter Student Name");
                return;
            } else if (gen.equals("Select..")) {
                showMessage("Error", "Please Select the Gender");
                return;
            } else if (std.equals("Select..")) {
                showMessage("Error", "Please Select the Standard");
                return;
            } else if (father_name.getText().toString().trim().length() == 0 && guardian_name.getText().toString().trim().length() == 0 && mother_name.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please Enter Father/Mother/Guardian name");
                return;
            } else if (attendance.getText().toString().length()<1 || (Float.parseFloat(attendance.getText().toString().trim()) > 100 || Float.parseFloat(attendance.getText().toString().trim()) < 0)) {
                showMessage("Error", "Please Enter a Valid Percentage for Attendance");
                return;
            }else if (acperf.equals("Select..")) {
                showMessage("Error", "Please Select the Standard");
                return;
            } else if (!(aadhar.getText().toString().trim().length() == 0 || aadhar.getText().toString().trim().length() == 12)) {
                showMessage("Error", "Please Enter a Valid Aadhar Number");
                return;
            }
            else {

                    int day = dob.getDayOfMonth();
                    int month = dob.getMonth() + 1;
                    int year = dob.getYear();

                    String date = year + "-" + month + "-" + day;


                    int g = 0, s = 0;
                    switch (gen) {
                        case "Male":
                            g = 1;
                            break;
                        case "Female":
                            g = 2;
                            break;
                        case "Other":
                            g = 3;
                            break;
                        default:
                            g = 0;
                    }
                    switch (std) {
                        case "I":
                            s = 1;
                            break;
                        case "II":
                            s = 2;
                            break;
                        case "III":
                            s = 3;
                            break;
                        case "IV":
                            s = 4;
                            break;
                        case "V":
                            s = 5;
                            break;
                        case "VI":
                            s = 6;
                            break;
                        case "VII":
                            s = 7;
                            break;
                        case "VIII":
                            s = 8;
                            break;
                        case "IX":
                            s = 9;
                            break;
                        case "X":
                            s = 10;
                            break;
                    }


                try {
                    //If the required fields are filled then the DB is updated
                    //creating insertion query
                    String insert_query = "'" + sid + "'," +
                            "'" + sid.substring(0,8) + "'," +
                            "'" + name.getText().toString().trim() + "'," +
                            "'" + date + "'," +
                            "'" + g + "'," +
                            "'" + s + "'," +
                            "'" + father_name.getText().toString().trim() + "'," +
                            "'" + mother_name.getText().toString().trim() + "'," +
                            "'" + guardian_name.getText().toString().trim() + "'," +
                            "'" + Float.parseFloat(attendance.getText().toString().trim()) + "'," +
                            "'" + acperf + "'," +
                            "'" + aadhar.getText().toString().trim() + "'";

                    //inserting into database
                    database.execSQL("INSERT INTO child VALUES (" + insert_query + ")");

                    insert_query = "'" + sid + "'," +
                            "'" + name.getText().toString().trim() + "'," +
                            "'" + gen + "',"+
                            "'" + father_name.getText().toString().trim() + "'";
                    database.execSQL("INSERT INTO child_references VALUES (" + insert_query + ")");


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
                }
                finally {
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
    //Method to create the dialog box
    //@params title and message
    public static void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentDetails.get());
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

    }

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
        final TextView errorMessage=(TextView) dialogView.findViewById(R.id.ErrorMessage);

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                String sid=studentID.getText().toString().toUpperCase();

                if(sid.length()==11)
                {

                    errorMessage.setVisibility(View.VISIBLE);
                    if(!childIdValidation(sid)) {
                        errorMessage.setText("Please Enter a Student ID which is valid");
                    }
                    else if(UpdateActivity.isStudentID(sid)){
                        errorMessage.setText("Student ID Already Exists! Please select a different ID.");
                    }
                    else if(!UpdateActivity.isSchoolID(sid.substring(0,8))){
                        errorMessage.setText("School Doesn't Exist! Please Add School First.");
                    }
                    else{
                        errorMessage.setText("Student ID is Valid!");
                    }
                }
                else if(sid.length()<11){
                    errorMessage.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };

        studentID.addTextChangedListener(inputTextWatcher);
        // Add action buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                sid = studentID.getText().toString().toUpperCase();
                if (!childIdValidation(sid) || UpdateActivity.isStudentID(sid) || !UpdateActivity.isSchoolID(sid.substring(0,8))) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //If canceled the intent returns to parent
                Intent();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showError() {

        Toast.makeText(this, "Enter a valid Student ID", Toast.LENGTH_LONG).show();
    }

    //Method to set studentID in the Activity
    public void setStudentID() {
        studentID.setText(StudentDetails.sid);
        schoolID.setText(sid.substring(0,8));
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

    //Method to get selected item in the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==standard)
        {
            std = parent.getItemAtPosition(position).toString();
        }else if(parent==gender){
            gen=parent.getItemAtPosition(position).toString();
        }
        else if(parent==academicPerformance){
            acperf=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
