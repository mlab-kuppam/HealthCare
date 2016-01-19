package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class EyeActivity1 extends Activity {

    //Declaring sid -> studentID(must)
    static String eye_sid;

    NumberPicker dist_right,dist_left,near_right,near_left;

    int dist=10,near=10;

    TextView txt_Rt_Dis,txt_Lt_Dis,txt_Rt_Ne,txt_Lt_Ne;

    String dist_refract,near_refract;

    EditText Spherical_rt_dist,Spherical_lt_dist,Spherical_lt_near,Spherical_rt_near,Cylinder_rt_dist,Cylinder_lt_dist,Cylinder_rt_near,Cylinder_lt_near,Axis_rt_dist,Axis_lt_dist,
            Axis_rt_near,Axis_lt_near;

    RelativeLayout numb_dist_right,numb_dist_left,numb_near_right,numb_near_left,six_dist_right,six_dist_left,six_near_right,six_near_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye1);

        //Invoking StudentID Dialog box
        studentidDialog();

        dist_right = (NumberPicker) findViewById(R.id.numberPicker1);
        dist_left = (NumberPicker) findViewById(R.id.numberPicker2);
        near_right = (NumberPicker) findViewById(R.id.numberPicker3);
        near_left = (NumberPicker) findViewById(R.id.numberPicker4);
        numb_dist_right = (RelativeLayout)findViewById(R.id.numb_dist_right);
        numb_dist_left = (RelativeLayout)findViewById(R.id.numb_dist_left);
        numb_near_right = (RelativeLayout)findViewById(R.id.numb_near_right);
        numb_near_left = (RelativeLayout)findViewById(R.id.numb_near_left);
        six_dist_right = (RelativeLayout)findViewById(R.id.six_dist_right);
        six_dist_left = (RelativeLayout)findViewById(R.id.six_dist_left);
        six_near_right = (RelativeLayout)findViewById(R.id.six_near_right);
        six_near_left = (RelativeLayout)findViewById(R.id.six_near_left);

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


        txt_Rt_Dis = (TextView)findViewById(R.id.text_dist_right);
        txt_Lt_Dis = (TextView)findViewById(R.id.text_dist_left);
        txt_Rt_Ne = (TextView)findViewById(R.id.text_near_right);
        txt_Lt_Ne= (TextView)findViewById(R.id.text_near_left);

        try {
            String[] nums1 = new String[7];
            int[] dist_left_fill = new int[]{6, 9, 12, 18, 24, 26, 60};
            for (int i = 0; i < nums1.length; i++) {
                nums1[i] = Integer.toString(dist_left_fill[i]);
            }

            dist_right.setMaxValue(6);
            dist_right.setMinValue(0);
            dist_right.setWrapSelectorWheel(true);
            dist_right.setDisplayedValues(nums1);

            near_right.setMaxValue(6);
            near_right.setMinValue(0);
            near_right.setWrapSelectorWheel(true);
            near_right.setDisplayedValues(nums1);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
        }


        try {
            String[] nums2 = new String[7];
            int[] near_left_fill = new int[]{5,6,8,12,18,24,36};
            for (int i = 0; i < nums2.length; i++) {
                nums2[i] = Integer.toString(near_left_fill[i]);
            }

            dist_left.setMaxValue(6);
            dist_left.setMinValue(0);
            dist_left.setWrapSelectorWheel(true);
            dist_left.setDisplayedValues(nums2);

            near_left.setMaxValue(6);
            near_left.setMinValue(0);
            near_left.setWrapSelectorWheel(true);
            near_left.setDisplayedValues(nums2);
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
            case R.id.dist_plus: dist_refract="+";dist=1;
                break;
            case R.id.dist_minus: dist_refract="-";dist=0;
                break;
            case R.id.near_plus: near_refract="+";near=1;
                break;
            case R.id.near_minus: near_refract="-";near=0;
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
            case R.id.text_near_right:numb_near_right.setVisibility(View.VISIBLE);
                six_near_right.setVisibility(View.GONE);
                break;
            case R.id.text_near_left:numb_near_left.setVisibility(View.VISIBLE);
                six_near_left.setVisibility(View.GONE);
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
            case R.id.numberPicker3:
                numb_near_right.setVisibility(View.GONE);
                layout_val(near_right, txt_Rt_Ne);
                six_near_right.setVisibility(View.VISIBLE);
                break;
            case R.id.numberPicker4:
                numb_near_left.setVisibility(View.GONE);
                layout_val(near_left, txt_Lt_Ne);
                six_near_left.setVisibility(View.VISIBLE);
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
        if(Spherical_rt_dist.getText().toString().length()==0 || Spherical_lt_dist.getText().toString().length()==0 || Spherical_rt_near.getText().toString().length()==0 ||
                Spherical_lt_near.getText().toString().length()==0 || Cylinder_rt_dist.getText().toString().length()==0 || Cylinder_lt_dist.getText().toString().length()==0 ||
                Cylinder_rt_near.getText().toString().length()==0 || Cylinder_lt_near.getText().toString().length()==0 || Axis_rt_dist.getText().toString().length()==0  ||
                Axis_lt_dist.getText().toString().length()==0  || Axis_rt_near.getText().toString().length()==0  || Axis_lt_near.getText().toString().length()==0 )
        {
            showMessage("Warning", "Please enter all values");
            return;
        }

        else if(dist==10 || near==10 )
        {
            showMessage("Warning", "Please mention if it is a positive / negative power");
            return;
        }

        else if(Integer.parseInt(Axis_rt_dist.getText().toString().trim())>180 || Integer.parseInt(Axis_lt_dist.getText().toString().trim())>180 || Integer.parseInt(Axis_rt_near.getText().toString().trim())>180 ||
                Integer.parseInt(Axis_lt_near.getText().toString().trim())>180 || Axis_rt_dist.getText().toString().length()>3  ||
                Axis_lt_dist.getText().toString().length()>3  || Axis_rt_near.getText().toString().length()>3  || Axis_lt_near.getText().toString().length()>3 )
        {
            showMessage("Warning", "Please enter a valid axis");
            return;
        }

        Intent intent = new Intent(this,EyeActivity2.class);
        startActivity(intent);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
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
        // Add action buttons

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                eye_sid = studentID.getText().toString();
                //System.out.println(sid);
                if (eye_sid.length() <= 1) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Student ID: " + eye_sid, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Enter Student ID", Toast.LENGTH_LONG).show();
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
}
