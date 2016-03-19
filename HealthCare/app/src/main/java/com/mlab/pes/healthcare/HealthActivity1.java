package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class HealthActivity1 extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    //Declaring sid -> studentID(must)
    static String health_sid;

    EditText historyOfRS,cardio,anaemia_others,health_others,Height, Weight, Waist, Hip, Pulse_Rate, Bp, Nail, Bath, Groom, Oral, Sanitary, Dental, Fluorosis, Gingi, ulcer, Oral_Other, Lung, Resp_other, Heart
            ,traumaT,spacingT,crowdingT,cleftT,chronicT,soundsT,frequency,frequencyFood;

    RelativeLayout San,operated,bronchialL;

    String[] checkboxitem = new String[]{"Applicable", "Not Applicable"};

    String age;

    int anaemia, nails = 10, bath = 10, groom = 10, oral = 10, men = 10, san = 10, dent = 10, flu = 10, ging = 10, ulc = 10, lung = 10, heart = 10, check = 10,trauma=10,spacing=10,
    crowding=10,cleft=10,operatedOn=10,chronic=10,bronchial=10,sounds=10,bronchialAst,bothParents=2,OverNight,oral_referal=10;

    TextView hltStdId,bmi,whratio;

    RadioButton Applicable, NotApplicable;

    Spinner Anaemia_drop,dental_others,bronchialAsthma,overNight;

    SQLiteDatabase database;

    private static HealthActivity1 app;
    public static HealthActivity1 get(){
        return app;
    }

    //creating query for declaration of tables
    public String table_query=
            "  child_id VARCHAR[11]," +
            "  frequencyFood INTEGER[1]," +
            "  over_night_food INTEGER[1]," +
            "  both_parents INTEGER[1]," +
            "  height FLOAT[5]," +
            "  weight FLOAT[5]," +
            "  wc FLOAT[5]," +
            "  hc FLOAT[5]," +
            "  pr INTEGER[3]," +
            "  bp varchar(7)," +
            "  ph_n INTEGER[1]," +
            "  ph_n_com VARCHAR[140]," +
            "  ph_b INTEGER[1]," +
            "  ph_b_com VARCHAR[140]," +
            "  ph_g INTEGER[1]," +
            "  ph_g_com VARCHAR[140]," +
            "  ph_oc INTEGER[1]," +
            "  ph_oc_com VARCHAR[140]," +
            "  ph_am INTEGER[1]," +
            "  ph_am_sn INTEGER[1]," +
            "  ph_am_sn_com VARCHAR[140]," +
            "  health_others VARCHAR[140]," +
            "  ca INTEGER[1]," +
            "  ca_com VARCHAR[140]," +
            "  oe_dc INTEGER[1]," +
            "  oe_dc_com VARCHAR[140]," +
            "  oe_f INTEGER[1]," +
            "  oe_f_com VARCHAR[140]," +
            "  oe_g INTEGER[1]," +
            "  oe_g_com VARCHAR[140]," +
            "  oe_ou INTEGER[1]," +
            "  oe_ou_com VARCHAR[140]," +
            "  oe_trauma INTEGER[1]," +
            "  oe_trauma_com VARCHAR[140]," +
            "  oe_spacing INTEGER[1]," +
            "  oe_spacing_com VARCHAR[140]," +
            "  oe_crowding INTEGER[1]," +
            "  oe_crowding_com VARCHAR[140]," +
            "  oe_referal INTEGER[1]," +
            "  oe_others VARCHAR[140]," +
            "  rs_chronic INTEGER[1]," +
            "  rs_chronic_com VARCHAR[140]," +
            "  rs_bronchial INTEGER[1]," +
            "  oe_bronchial_medication VARCHAR[140]," +
            "  rs_sounds INTEGER[1]," +
            "  rs_sounds_com VARCHAR[140]," +
            "  rs_others VARCHAR[140]," +
            "  cvs_ahs INTEGER[1]," +
            "  cvs_ahs_com VARCHAR[140]," +
            "  cvs_others VARCHAR[140]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health1);

        app=this;

        //Invoking StudentID Dialog box
        studentidDialog();
		
		hltStdId = (TextView)findViewById(R.id.hlt_std_id);

        frequency=(EditText) findViewById(R.id.frequency);
        cardio = (EditText) findViewById(R.id.cardio_text);
        anaemia_others = (EditText) findViewById(R.id.anaemia_text);
        health_others = (EditText) findViewById(R.id.health_text);
        Height = (EditText) findViewById(R.id.height);
        Weight = (EditText) findViewById(R.id.weight);
        Waist = (EditText) findViewById(R.id.waist);
        Hip = (EditText) findViewById(R.id.hip);
        Pulse_Rate = (EditText) findViewById(R.id.pulse);
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
        Resp_other = (EditText) findViewById(R.id.resp_text);
        Heart = (EditText) findViewById(R.id.heart_text);
        traumaT=(EditText) findViewById(R.id.trauma_text);
        spacingT=(EditText) findViewById(R.id.spacing_text);
        crowdingT=(EditText) findViewById(R.id.crowding_text);
        chronicT = (EditText) findViewById(R.id.chronic_text);
        soundsT = (EditText) findViewById(R.id.sounds_text);

        bmi=(TextView) findViewById(R.id.bmi);
        whratio=(TextView) findViewById(R.id.whratio);


        Applicable = (RadioButton) findViewById(R.id.app);
        NotApplicable = (RadioButton) findViewById(R.id.notapp);

        Bp.setText("/");

        San = (RelativeLayout) findViewById(R.id.sanitary);
        bronchialL = (RelativeLayout) findViewById(R.id.bronchial_layout);



        Anaemia_drop = (Spinner) findViewById(R.id.anaemia_drop);
        overNight = (Spinner) findViewById(R.id.overNightFood);

        String[] symp = new String[]{"Select..","No Anaemia","Mild", "Moderate", "Severe"};
        String[] dental = {"Pre-Shedding mobility","Trauma of tooth","Spacing/Crowding","Developmental Abnormailty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, symp);
        Anaemia_drop.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.overNight, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        overNight.setAdapter(adapter1);
        overNight.setOnItemSelectedListener(this);

        bronchialAsthma = (Spinner) findViewById(R.id.bronchial_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.bronchinal_array, R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        bronchialAsthma.setAdapter(adapter2);
        bronchialAsthma.setOnItemSelectedListener(this);



        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS health1(" + table_query + ")");

        //Validating School ID
        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                final DecimalFormat df = new DecimalFormat("##.##");
                df.setRoundingMode(RoundingMode.CEILING);
                if(Weight.getText().toString().trim().length()>0 && Height.getText().toString().trim().length()>0)
                {
                    float weight=Float.parseFloat(Weight.getText().toString().trim());
                    float height=Float.parseFloat(Height.getText().toString().trim());
                    if(weight>0 & height>0)
                    {
                        double BMI=weight/Math.pow(height,2);
                        BMI=BMI*Math.pow(10,4);
                        bmi.setText(df.format(BMI));
                    }
                }
                else
                    bmi.setText("");
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        Weight.addTextChangedListener(inputTextWatcher);
        Height.addTextChangedListener(inputTextWatcher);
        inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                final DecimalFormat df = new DecimalFormat("##.##");
                df.setRoundingMode(RoundingMode.CEILING);
                if(Waist.getText().toString().trim().length()>0 && Hip.getText().toString().trim().length()>0)
                {
                    float waist=Float.parseFloat(Waist.getText().toString().trim());
                    float hip=Float.parseFloat(Hip.getText().toString().trim());
                    if(waist>0 & hip>0)
                    {
                        double wh=waist/hip;
                        whratio.setText(df.format(wh));
                    }
                }
                else
                    whratio.setText("");
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        Hip.addTextChangedListener(inputTextWatcher);
        Waist.addTextChangedListener(inputTextWatcher);
    }

    //Method to get selected item in the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == bronchialAsthma) {
            bronchialAst = position - 1;
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

            case R.id.bothParentsYes :
                bothParents=1;
                break;
            case R.id.bothParentsNo :
                bothParents=0;
                break;
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

            case R.id.heart_yes:
                heart = 1;
                Heart.setVisibility(v.VISIBLE);
                break;
            case R.id.heart_no:
                heart = 0;
                Heart.setVisibility(v.GONE);
                break;

            case R.id.trauma_yes:
                trauma = 1;
                traumaT.setVisibility(v.VISIBLE);
                break;
            case R.id.trauma_no:
                trauma = 0;
                traumaT.setVisibility(v.GONE);
                break;

            case R.id.spacing_yes:
                spacing = 1;
                spacingT.setVisibility(v.VISIBLE);
                break;
            case R.id.spacing_no:
                spacing = 0;
                spacingT.setVisibility(v.GONE);
                break;

            case R.id.crowding_yes:
                crowding = 1;
                crowdingT.setVisibility(v.VISIBLE);
                break;
            case R.id.crowding_no:
                crowding = 0;
                crowdingT.setVisibility(v.GONE);
                break;

            case R.id.chronic_yes:
                chronic=1;
                chronicT.setVisibility(View.VISIBLE);
                break;
            case R.id.chronic_no:
                chronicT.setVisibility(View.GONE);
                chronic=0;
                break;

            case R.id.bronchial_yes:
                bronchial=1;
                bronchialL.setVisibility(View.VISIBLE);
                break;
            case R.id.bronchial_no:
                bronchial=0;
                bronchialL.setVisibility(View.GONE);
                break;

            case R.id.sounds_yes:
                sounds=1;
                soundsT.setVisibility(View.VISIBLE);
                break;
            case R.id.sounds_no:
                sounds=0;
                soundsT.setVisibility(View.GONE);
                break;



            case R.id.app:
                check = 1;
                age = checkboxitem[0];
                San.setVisibility(v.VISIBLE);
                //NotApplicable.setChecked(false);
                //Applicable.setChecked(true);
                break;
            case R.id.notapp:
                check = 0;
                age = checkboxitem[1];
                San.setVisibility(v.GONE);
                //Applicable.setChecked(false);
                //NotApplicable.setChecked(true);
                break;


            case R.id.oral_required:
                oral_referal=1;
                break;
            case R.id.oral_notRequired:
                oral_referal=0;
                break;


        }
    }

    public void Next() {

            if (frequency.getText().toString().trim().length()==0) {
                showMessage("Error", "Please Enter Frequency of Skipping Breakfast/week in Dietary Pattern");
                return;
            }else if (OverNight == -1) {
                showMessage("Error", "Please Select an Option for Children Having Over-Night Food in the mornings in Dietary Pattern");
                return;
            }else if (bothParents == 2) {
                showMessage("Error", "Please Select an Option for Both Parents Working in Dietary Pattern");
                return;
            }
            else if (Height.getText().toString().length() == 0 ) {
                showMessage("Warning", "Please enter the Height");
                return;
            }
            else if ( Weight.getText().toString().length() == 0) {
                showMessage("Warning", "Please enter the Weight");
                return;
            }
            else if (Waist.getText().toString().length() == 0) {
                showMessage("Warning", "Please enter the Waist Circumference");
                return;
            }
            else if (Hip.getText().toString().length() == 0 ) {
                showMessage("Warning", "Please enter the Hip Circumference");
                return;
            }
            else if (Pulse_Rate.getText().toString().length() == 0 ) {
                showMessage("Warning", "Please enter the Pulse Rate");
                return;
            }
            else if (Bp.getText().toString().length() == 0) {
                showMessage("Warning", "Please enter the Blood Pressure");
                return;
            }
            else if (nails == 10) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Nails");
                return;
            }
            else if (bath == 10 ) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Bathing");
                return;
            }
            else if (groom == 10 ) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Gromming");
                return;
            }
            else if (oral == 10 ) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Oral Care");
                return;
            }
            else if (check == 10) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Age of Menarche");
                return;
            }
            else if ( check ==1 && san == 10) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Age of Menarche - Sanitary Napkin");
                return;
            }else if (Anaemia_drop.getSelectedItemPosition() == 0) {
                showMessage("Warning", "Please select an option for Clinical Anaemia");
                return;
            }
            else if (dent == 10) {
                showMessage("Warning", "Please select an option for Oral Examination - Dental Caries");
                return;
            }
            else if (flu == 10 ) {
                showMessage("Warning", "Please select an option for Oral Examination - Fluorosis");
                return;
            }
            else if (ging == 10) {
                showMessage("Warning", "Please select an option for Oral Examination - Gingivitis");
                return;
            }
            else if ( ulc == 10 ) {
                showMessage("Warning", "Please select an option for Oral Examination - Oral Ulcers");
                return;
            }
            else if (trauma == 10 ) {
                showMessage("Warning", "Please select an option for Oral Examination - Trauma of Tooth");
                return;
            }
            else if (spacing == 10 ) {
                showMessage("Warning", "Please select an option for Oral Examination - Spacing");
                return;
            }
            else if (crowding == 10 ) {
                showMessage("Warning","Please select an option for Oral Examination - Crowding");
                return;
            }
            else if (oral_referal == 10 ) {
                showMessage("Warning","Please select an option for Oral Examination - Referal");
                return;
            }
            else if (chronic == 10 ) {
                showMessage("Warning", "Please select an option for Respiratory System - Chronic Cough");
                return;
            }
            else if (bronchial == 10 ) {
                showMessage("Warning", "Please select an option for Respiratory System - Bronchial Asthma");
                return;
            }
            else if (bronchial == 1 && bronchialAst==-1 ) {
                showMessage("Warning", "Please select an option for Respiratory System - Bronchial Asthma - Medication");
                return;
            }
            else if (sounds == 10 ) {
                showMessage("Warning", "Please select an option for Respiratory System - Adventitious Sounds");
                return;
            }
            else if (heart == 10 ) {
                showMessage("Warning", "Please select an option for Cardio-Vascular System - Abnormal Heart Sounds");
                return;
            }
            else {
                anaemia=Anaemia_drop.getSelectedItemPosition();


                    try {
                    String insert_query = "'" + health_sid + "'," +
                            "'"+ frequency.getText().toString().trim()+"',"+
                            "'" + OverNight + "',"+
                            "'" + bothParents + "',"+
                            "'" + Float.parseFloat(Height.getText().toString().trim()) + "'," +
                            "'" + Float.parseFloat(Weight.getText().toString().trim()) + "'," +
                            "'" + Float.parseFloat(Waist.getText().toString().trim()) + "'," +
                            "'" + Float.parseFloat(Hip.getText().toString().trim()) + "'," +
                            "'" + Integer.parseInt(Pulse_Rate.getText().toString().trim()) + "'," +
                            "'" + Bp.getText().toString().trim() + "'," +
                            "'" + nails + "'," +
                            "'" + Nail.getText().toString().trim() + "'," +
                            "'" + bath + "'," +
                            "'" + Bath.getText().toString().trim() + "'," +
                            "'" + groom + "'," +
                            "'" + Groom.getText().toString().trim() + "'," +
                            "'" + oral + "'," +
                            "'" + Oral.getText().toString().trim() + "'," +
                            "'" + check + "'," +
                            "'" + san + "'," +
                            "'" + Sanitary.getText().toString().trim() + "'," +
                            "'" + health_others.getText().toString().trim() + "'," +
                            "'" + anaemia + "'," +
                            "'" + anaemia_others.getText().toString().trim() + "'," +
                            "'" + dent + "'," +
                            "'" + Dental.getText().toString().trim() + "'," +
                            "'" + flu + "'," +
                            "'" + Fluorosis.getText().toString().trim() + "'," +
                            "'" + ging + "'," +
                            "'" + Gingi.getText().toString().trim() + "'," +
                            "'" + ulc + "'," +
                            "'" + ulcer.getText().toString().trim() + "'," +
                            "'" + trauma + "'," +
                            "'" + traumaT.getText().toString().trim() + "'," +
                            "'" + spacing + "'," +
                            "'" + spacingT.getText().toString().trim() + "'," +
                            "'" + crowding + "'," +
                            "'" + crowdingT.getText().toString().trim() + "'," +
                            "'" + oral_referal + "'," +
                            "'" + Oral_Other.getText().toString().trim() + "'," +
                            "'" + chronic + "'," +
                            "'" + chronicT.getText().toString().trim() + "'," +
                            "'" + bronchial + "'," +
                            "'" + bronchialAst + "'," +
                            "'" + sounds + "'," +
                            "'" + soundsT.getText().toString().trim() + "'," +
                            "'" + Resp_other.getText().toString().trim() + "'," +
                            "'" + heart + "'," +
                            "'" + Heart.getText().toString().trim() + "'," +
                            "'" + cardio.getText().toString().trim() + "'";

                    //inserting into database
                    database.execSQL("INSERT INTO health1 VALUES (" + insert_query + ")");
                    //sending confirmation that data is inserted
                    showMessage("Success", "Entry Successfully Added!");


                        Intent b = new Intent(this, HealthActivity2.class);
                        startActivity(b);

                        hltStdId.setText("");

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    database.close();
                }
            }
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
        studentID.setText(UpdateActivity.schoolId);
        studentID.setSelection(8);
        //Validating Student ID
        UpdateActivity.StudentIDValidation(dialogView);

        //Add action buttons

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                health_sid = studentID.getText().toString().toUpperCase();
                //System.out.println(skin_sid);
                if (!UpdateActivity.isStudentID(health_sid)) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    hltStdId.setText(health_sid);
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

    public void showError() {
        Toast.makeText(this, "Enter a Valid Student ID", Toast.LENGTH_LONG).show();
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
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK",null);
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
