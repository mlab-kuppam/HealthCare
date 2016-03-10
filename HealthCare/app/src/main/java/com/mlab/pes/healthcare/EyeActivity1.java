package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class EyeActivity1 extends ActionBarActivity {

    //Declaring sid -> studentID(must)
    static String eye_sid;
	
	 int distrt=10,nearrt=10,distlt=10,nearlt=10;

    NumberPicker dist_right,dist_left;

    int refractCheck=0;

    int dist=10,near=10;
    //strings to store the complete values of vision testing
    public static String vt_d_r,vt_d_l;
    //string to store the compltete values of spherical refraction and correction
    public static String spherical_dist_right,spherical_dist_left,spherical_near_right,spherical_near_left;

    LinearLayout refract;

    TextView eyeStdId,txt_Rt_Dis,txt_Lt_Dis;

    String dist_refract,near_refract;


    SQLiteDatabase database;

    public String table_query=
            "  child_id VARCHAR[11]," +
                    "  vt_r_d VARCHAR[5]," +
                    "  vt_l_d VARCHAR[5]," +
                    "  vt_com VARCHAR[140]," +
                    "  rc_r_s_d VARCHAR[5]," +
                    "  rc_r_s_n VARCHAR[5]," +
                    "  rc_r_c_d VARCHAR[5]," +
                    "  rc_r_c_n VARCHAR[5]," +
                    "  rc_r_a_d VARCHAR[5]," +
                    "  rc_r_a_n VARCHAR[5]," +
                    "  rc_l_s_d VARCHAR[5]," +
                    "  rc_l_s_n VARCHAR[5]," +
                    "  rc_l_c_d VARCHAR[5]," +
                    "  rc_l_c_n VARCHAR[5]," +
                    "  rc_l_a_d VARCHAR[5]," +
                    "  rc_l_a_n VARCHAR[5]," +
                    "  rc_com VARCHAR[140]";

    public static EditText Spherical_rt_dist,Spherical_lt_dist,Spherical_lt_near,Spherical_rt_near,Cylinder_rt_dist,Cylinder_lt_dist,Cylinder_rt_near,Cylinder_lt_near,Axis_rt_dist,Axis_lt_dist,
            Axis_rt_near,Axis_lt_near,upper,lower;

    private static EyeActivity1 app;
    public static EyeActivity1 get(){
        return app;
    }

    RelativeLayout numb_dist_right,numb_dist_left,six_dist_right,six_dist_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye1);

        app=this;

        refract = (LinearLayout)findViewById(R.id.refract_correct);

        //Invoking StudentID Dialog box
        studentidDialog();

		eyeStdId = (TextView)findViewById(R.id.eye_std_id);
        eyeStdId.setText(eye_sid);
		
        dist_right = (NumberPicker) findViewById(R.id.numberPicker1);
        dist_left = (NumberPicker) findViewById(R.id.numberPicker2);
       numb_dist_right = (RelativeLayout)findViewById(R.id.numb_dist_right);
        numb_dist_left = (RelativeLayout)findViewById(R.id.numb_dist_left);
        six_dist_right = (RelativeLayout)findViewById(R.id.six_dist_right);
        six_dist_left = (RelativeLayout)findViewById(R.id.six_dist_left);

        Spherical_rt_dist = (EditText)findViewById(R.id.t1);
        Spherical_lt_dist = (EditText)findViewById(R.id.t4);
        Spherical_rt_near = (EditText)findViewById(R.id.t7);
        Spherical_lt_near = (EditText)findViewById(R.id.t10);
        Cylinder_rt_dist = (EditText)findViewById(R.id.t2);
        Cylinder_lt_dist = (EditText)findViewById(R.id.t5);
        Cylinder_rt_near = (EditText)findViewById(R.id.t8);
        Cylinder_lt_near = (EditText)findViewById(R.id.t11);
        Axis_rt_dist = (EditText)findViewById(R.id.t3);
        Axis_lt_dist = (EditText)findViewById(R.id.t6);
        Axis_rt_near = (EditText)findViewById(R.id.t9);
        Axis_lt_near = (EditText)findViewById(R.id.t12);
        upper = (EditText)findViewById(R.id.uppertext);
        lower = (EditText)findViewById(R.id.lowertext);


        txt_Rt_Dis = (TextView)findViewById(R.id.text_dist_right);
        txt_Lt_Dis = (TextView)findViewById(R.id.text_dist_left);


        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS eye1(" + table_query + ")");


        try {
            String[] nums1 = new String[8];
            int[] dist_left_fill = new int[]{0,6, 9, 12, 18, 24, 36, 60};
            for (int i = 0; i < nums1.length; i++) {
                nums1[i] = Integer.toString(dist_left_fill[i]);
            }

            dist_right.setMaxValue(7);
            dist_right.setMinValue(0);
            dist_right.setWrapSelectorWheel(true);
            dist_right.setDisplayedValues(nums1);

            dist_left.setMaxValue(7);
            dist_left.setMinValue(0);
            dist_left.setWrapSelectorWheel(true);
            dist_left.setDisplayedValues(nums1);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eye1, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     public void onRadioselect(View view)
    {
        switch (view.getId())
        {

            case R.id.dist_right_plus: spherical_dist_right="+";distrt=1;
                break;
            case R.id.dist_right_minus: spherical_dist_right="-";distrt=0;
                break;
            case R.id.near_right_plus: spherical_near_right="+";nearrt=1;
                break;
            case R.id.near_right_minus: spherical_near_right="-";nearrt=0;
                break;
            case R.id.dist_left_plus: spherical_dist_left="+";distlt=1;
                break;
            case R.id.dist_left_minus: spherical_dist_left="-";distlt=0;
                break;
            case R.id.near_left_plus: spherical_near_left="+";nearlt=1;
                break;
            case R.id.near_left_minus: spherical_near_left="-";nearlt=0;
                break;
        }
    }


    public void Switch_layout(View view)
    {
        switch (view.getId())
        {
            case R.id.text_dist_right:numb_dist_right.setVisibility(View.VISIBLE);
                six_dist_right.setVisibility(View.GONE);
                break;
            case R.id.text_dist_left:numb_dist_left.setVisibility(View.VISIBLE);
                six_dist_left.setVisibility(View.GONE);
                break;
        }
    }

    public void Num_Click(View view)
    {
        switch(view.getId()) {
            case R.id.numberPicker1:
                numb_dist_right.setVisibility(View.GONE);
                layout_val(dist_right, txt_Rt_Dis);
                six_dist_right.setVisibility(View.VISIBLE);
                break;
            case R.id.numberPicker2:
                six_dist_left.setVisibility(View.VISIBLE);
                layout_val(dist_left, txt_Lt_Dis);
                numb_dist_left.setVisibility(View.GONE);
                break;
        }
    }

    public void layout_val(NumberPicker numcheck, TextView textcheck)
    {
        try {
            textcheck.setText(numcheck.getDisplayedValues()[numcheck.getValue()]);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
        }
    }

    public void Next()
    {
        if(refractCheck==1){
        if(Spherical_rt_dist.getText().toString().length()==0)
        {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Spherical - Distance - Right Eye");
            return;
        }
        else if (Spherical_lt_dist.getText().toString().length()==0) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Spherical - Distance - Left Eye");
            return;
        }
        else if (Spherical_rt_near.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Spherical - Near - Right Eye");
            return;
        }
        else if (Spherical_lt_near.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Spherical - Near - Left Eye");
            return;
        }
        else if (Cylinder_rt_dist.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Cylindrical - Distance - Right Eye");
            return;
        }
        else if (Cylinder_lt_dist.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Cylindrical - Distance - Left Eye");
            return;
        }
        else if (Cylinder_rt_near.getText().toString().length()==0) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Cylindrical - Near - Right Eye");
            return;
        }
        else if (Cylinder_lt_near.getText().toString().length()==0) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Cylindrical - Near - Left Eye");
            return;
        }
        else if (Axis_rt_dist.getText().toString().length()==0  ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Axis - Distance - Right Eye");
            return;
        }
        else if (Axis_lt_dist.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Axis - Distance - Left Eye");
            return;
        }
        else if (Axis_rt_near.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Axis - Near - Right Eye");
            return;
        }
        else if (Axis_lt_near.getText().toString().length()==0 ) {
            showMessage("Warning", "Please enter a value for Refraction & Correction: Axis - Near - Left Eye");
            return;
        }
        else if(distrt==10)
        {
            showMessage("Warning", "Please mention if Refraction & Correction: Spherical - Distance - Right Eye is a positive / negative power");
            return;
        }
        else if(nearrt==10 )
        {
            showMessage("Warning", "Please mention if Refraction & Correction: Spherical - Near - Right Eye is a positive / negative power");
            return;
        }
        else if(distlt==10 )
        {
            showMessage("Warning", "Please mention if Refraction & Correction: Spherical - Distance - Left Eye is a positive / negative power");
            return;
        }
        else if(nearlt==10 )
        {
            showMessage("Warning", "Please mention if Refraction & Correction: Spherical - Near - Left Eye is a positive / negative power");
            return;
        }
        else if(Integer.parseInt(Axis_rt_dist.getText().toString().trim())>180 || Integer.parseInt(Axis_rt_dist.getText().toString().trim())<0 )
        {
            showMessage("Warning", "Please enter a valid value for Refraction & Correction: Axis - Distance - Right Eye");
            return;
        }
        else if(Integer.parseInt(Axis_lt_dist.getText().toString().trim())>180 || Integer.parseInt(Axis_lt_dist.getText().toString().trim())<0 )
        {
            showMessage("Warning", "Please enter a valid value for Refraction & Correction: Axis - Distance - Left Eye");
            return;
        }
        else if(Integer.parseInt(Axis_rt_near.getText().toString().trim())>180 || Integer.parseInt(Axis_rt_near.getText().toString().trim())<0)
        {
            showMessage("Warning", "Please enter a valid value for Refraction & Correction: Axis - Near - Right Eye");
            return;
        }
        else if(Integer.parseInt(Axis_lt_near.getText().toString().trim())>180 || Integer.parseInt(Axis_lt_near.getText().toString().trim())<0)
        {
            showMessage("Warning", "Please enter a valid value for Refraction & Correction: Axis - Near - Left Eye");
            return;
        }
        }
        else {

            vt_d_r = "6/" + txt_Rt_Dis.getText().toString();
            vt_d_l = "6/" + txt_Lt_Dis.getText().toString();


            try{
                //creating insertion query
                String insert_query = "'" +  eye_sid + "'," +
                        "'" +  vt_d_r + "'," +
                        "'" +  vt_d_l + "'," +
                        "'" +  upper.getText().toString().trim() + "'," +
                        "'" +  spherical_dist_right+ Spherical_rt_dist.getText().toString().trim() + "'," +
                        "'" +  spherical_near_right+ Spherical_rt_near.getText().toString().trim() + "'," +
                        "'" +  Cylinder_rt_dist.getText().toString().trim() + "'," +
                        "'" +  Cylinder_rt_near.getText().toString().trim() + "'," +
                        "'" +  Axis_rt_dist.getText().toString().trim() + "'," +
                        "'" +  Axis_rt_near.getText().toString().trim() + "'," +
                        "'" +  spherical_dist_left+ Spherical_lt_dist.getText().toString().trim() + "'," +
                        "'" +  spherical_near_left+ Spherical_lt_near.getText().toString().trim() + "'," +
                        "'" +  Cylinder_lt_dist.getText().toString().trim() + "'," +
                        "'" +  Cylinder_lt_near.getText().toString().trim() + "'," +
                        "'" +  Axis_lt_dist.getText().toString().trim() + "'," +
                        "'" +  Axis_lt_near.getText().toString().trim() + "'," +
                        "'" +  lower.getText().toString().trim()+ "'";
                System.out.println("EYE " + insert_query);

                //inserting into database
                database.execSQL("INSERT INTO eye1 VALUES (" + insert_query + ")");

                //sending confirmation that data is inserted
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Success");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getBack();
                    }
                });
                builder.setCancelable(false);
                builder.setMessage("Entry Successfully Added!");
                builder.show();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                database.close();

            }
        }
    }



    public void getBack(){

        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);

    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

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

                eye_sid = studentID.getText().toString().toUpperCase();
                //System.out.println(skin_sid);
                if (!UpdateActivity.isStudentID(eye_sid)) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    eyeStdId.setText(eye_sid);
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
            for(x = 0; x < EyeActivity2.pic_count_eye; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/"+eye_sid+"_eye_"+x+".jpg");
                //System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }

    public void Refract(View v) {

        if(refractCheck==0)
        {
            refract.setVisibility(v.VISIBLE);
            refractCheck++;
        }
        else{
            refract.setVisibility(v.GONE);
            refractCheck--;
        }
    }
}
