package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class EyeActivity2 extends ActionBarActivity {

    EditText Cvis_left,Cvis_right,Bitot_left,Bitot_right,Allerconj_left,Allerconj_right,Night_left,Night_right,Congp_left,Congp_right,Congd_left,Congd_right,Amb_left,Amb_right,
            Nys_left,Nys_right,Fund_left,Fund_right,Other;
			
	TextView eye1StdId;

    int cvis_left=10,cvis_right=10,bitot_left=10,bitot_right=10,allconj_left=10,allconj_right=10,night_left=1,night_right=10,congp_left=10,congp_right=10,congd_left=10,congd_right=10,
            amb_left=10,amb_right=10,nys_left=10,nys_right=10,fund_left=10,fund_right=10;

    SQLiteDatabase database;

    public String table_query=
            "  child_id VARCHAR[11]," +
            "  vt_r_d VARCHAR[5]," +
            "  vt_r_n VARCHAR[5]," +
            "  vt_l_d VARCHAR[5]," +
            "  vt_l_n VARCHAR[5]," +
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
            "  rc_com VARCHAR[140]," +
            "  cv_r INTEGER[1]," +
            "  cv_r_com VARCHAR[140]," +
            "  cv_l INTEGER[1]," +
            "  cv_l_com VARCHAR[140]," +
            "  bs_r INTEGER[1]," +
            "  bs_r_com VARCHAR[140]," +
            "  bs_l INTEGER[1]," +
            "  bs_l_com VARCHAR[140]," +
            "  ac_r INTEGER[1]," +
            "  ac_r_com VARCHAR[140]," +
            "  ac_l INTEGER[1]," +
            "  ac_l_com VARCHAR[140]," +
            "  nb_r INTEGER[1]," +
            "  nb_r_com VARCHAR[140]," +
            "  nb_l INTEGER[1]," +
            "  nb_l_com VARCHAR[140]," +
            "  cp_r INTEGER[1]," +
            "  cp_r_com VARCHAR[140]," +
            "  cp_l INTEGER[1]," +
            "  cp_l_com VARCHAR[140]," +
            "  cdc_r INTEGER[1]," +
            "  cdc_r_com VARCHAR[140]," +
            "  cdc_l INTEGER[1]," +
            "  cdc_l_com VARCHAR[140]," +
            "  am_r INTEGER[1]," +
            "  am_r_com VARCHAR[140]," +
            "  am_l INTEGER[1]," +
            "  am_l_com VARCHAR[140]," +
            "  ny_r INTEGER[1]," +
            "  ny_r_com VARCHAR[140]," +
            "  ny_l INTEGER[1]," +
            "  ny_l_com VARCHAR[140]," +
            "  fe_r INTEGER[1]," +
            "  fe_r_com VARCHAR[140]," +
            "  fe_l INTEGER[1]," +
            "  fe_l_com VARCHAR[140]," +
            "  others VARCHAR[140]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye2);

        //Re-Initializing pid_count for every new student
        pic_count_eye = 0;
		
		eye1StdId = (TextView)findViewById(R.id.eye1_std_id);
        eye1StdId.setText(EyeActivity1.eye_sid);

        Cvis_left = (EditText) findViewById(R.id.cvis_left_text);
        Cvis_right = (EditText) findViewById(R.id.cvis_right_text);
        Bitot_left = (EditText) findViewById(R.id.bitot_left_text);
        Bitot_right = (EditText) findViewById(R.id.bitot_right_text);
        Allerconj_left = (EditText) findViewById(R.id.allconj_left_text);
        Allerconj_right = (EditText) findViewById(R.id.allconj_right_text);
        Night_left = (EditText) findViewById(R.id.night_left_text);
        Night_right = (EditText) findViewById(R.id.night_right_text);
        Congp_left = (EditText) findViewById(R.id.congp_left_text);
        Congp_right = (EditText) findViewById(R.id.congp_right_text);
        Congd_left = (EditText) findViewById(R.id.congd_left_text);
        Congd_right = (EditText) findViewById(R.id.congd_right_text);
        Amb_left = (EditText) findViewById(R.id.amb_left_text);
        Amb_right = (EditText) findViewById(R.id.amb_right_text);
        Nys_left = (EditText) findViewById(R.id.nys_left_text);
        Nys_right = (EditText) findViewById(R.id.nys_right_text);
        Fund_left = (EditText) findViewById(R.id.fund_left_text);
        Fund_right = (EditText) findViewById(R.id.fund_right_text);
        Other = (EditText) findViewById(R.id.add_text);

        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS eye(" + table_query + ")");

        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[20] ," +
                        "  image TEXT" ;
        //creating image table
        database.execSQL("CREATE TABLE IF NOT EXISTS images( " + image_table_query + " )");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eye2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_next:
                Next();
                return super.onOptionsItemSelected(item);
            case R.id.action_camera:
                capture();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void eyeClick(View view) {
        switch (view.getId()) {
            case R.id.cvis_left_yes:
                Cvis_left.setVisibility(view.GONE);
                cvis_left = 1;
                break;
            case R.id.cvis_right_yes:
                Cvis_right.setVisibility(view.GONE);
                cvis_right = 1;
                break;
            case R.id.cvis_left_no:
                Cvis_left.setVisibility(view.VISIBLE);
                cvis_left = 0;
                break;
            case R.id.cvis_right_no:
                Cvis_right.setVisibility(view.VISIBLE);
                cvis_right = 0;
                break;

            case R.id.bitot_left_yes:
                Bitot_left.setVisibility(view.VISIBLE);
                bitot_left = 1;
                break;
            case R.id.bitot_right_yes:
                Bitot_right.setVisibility(view.VISIBLE);
                bitot_right = 1;
                break;
            case R.id.bitot_left_no:
                Bitot_left.setVisibility(view.GONE);
                bitot_left = 0;
                break;
            case R.id.bitot_right_no:
                Bitot_right.setVisibility(view.GONE);
                bitot_right = 0;
                break;

            case R.id.allconj_left_yes:
                Allerconj_left.setVisibility(view.VISIBLE);
                allconj_left = 1;
                break;
            case R.id.allconj_right_yes:
                Allerconj_right.setVisibility(view.VISIBLE);
                allconj_right = 1;
                break;
            case R.id.allconj_left_no:
                Allerconj_left.setVisibility(view.GONE);
                allconj_left = 0;
                break;
            case R.id.allconj_right_no:
                Allerconj_right.setVisibility(view.GONE);
                allconj_right = 0;
                break;

            case R.id.night_left_yes:
                Night_left.setVisibility(view.VISIBLE);
                night_left = 1;
                break;
            case R.id.night_right_yes:
                Night_right.setVisibility(view.VISIBLE);
                night_right = 1;
                break;
            case R.id.night_left_no:
                Night_left.setVisibility(view.GONE);
                night_left = 0;
                break;
            case R.id.night_right_no:
                Night_right.setVisibility(view.GONE);
                night_right = 0;
                break;

            case R.id.congp_left_yes:
                Congp_left.setVisibility(view.VISIBLE);
                congp_left = 1;
                break;
            case R.id.congp_right_yes:
                Congp_right.setVisibility(view.VISIBLE);
                congp_right = 1;
                break;
            case R.id.congp_left_no:
                Congp_left.setVisibility(view.GONE);
                congp_left = 0;
                break;
            case R.id.congp_right_no:
                Congp_right.setVisibility(view.GONE);
                congp_right = 0;
                break;

            case R.id.congd_left_yes:
                Congd_left.setVisibility(view.VISIBLE);
                congd_left=1;
                break;
            case R.id.congd_right_yes:
                Congd_right.setVisibility(view.VISIBLE);
                congd_right=1;
                break;
            case R.id.congd_left_no:
                Congd_left.setVisibility(view.GONE);
                congd_left=0;
                break;
            case R.id.congd_right_no:
                Congd_right.setVisibility(view.GONE);
                congd_right=0;
                break;

            case R.id.amb_left_yes:
                Amb_left.setVisibility(view.VISIBLE);
                amb_left=1;
                break;
            case R.id.amb_right_yes:
                Amb_right.setVisibility(view.VISIBLE);
                amb_right=1;
                break;
            case R.id.amb_left_no:
                Amb_left.setVisibility(view.GONE);
                amb_left=0;
                break;
            case R.id.amb_right_no:
                Amb_right.setVisibility(view.GONE);
                amb_right=0;
                break;

            case R.id.nys_left_yes:
                Nys_left.setVisibility(view.VISIBLE);
                nys_left=1;
                break;
            case R.id.nys_right_yes:
                Nys_right.setVisibility(view.VISIBLE);
                nys_right=1;
                break;
            case R.id.nys_left_no:
                Nys_left.setVisibility(view.GONE);
                nys_left=0;
                break;
            case R.id.nys_right_no:
                Nys_right.setVisibility(view.GONE);
                nys_right=0;
                break;

            case R.id.fund_left_yes:
                Fund_left.setVisibility(view.VISIBLE);
                fund_left=1;
                break;
            case R.id.fund_right_yes:
                Fund_right.setVisibility(view.VISIBLE);
                fund_right=1;
                break;
            case R.id.fund_left_no:
                Fund_left.setVisibility(view.GONE);
                fund_left=0;
                break;
            case R.id.fund_right_no:
                Fund_right.setVisibility(view.GONE);
                fund_right=0;
                break;

        }
    }

    public void Next() {

        if (cvis_left == 10 ) {
            showMessage("Warning", "Please select an option for Colour Vision - Left Eye");
            return;
        }
        else if (cvis_right == 10) {
            showMessage("Warning", "Please select an option for Colour Vision - Right Eye");
            return;
        }
        else if ( bitot_left == 10) {
            showMessage("Warning", "Please select an option for Bitot\'s Spots - Left Eye");
            return;
        }
        else if (bitot_right == 10) {
            showMessage("Warning", "Please select an option for Bitot\'s Spots - Right Eye");
            return;
        }
        else if (allconj_left == 10 ) {
            showMessage("Warning", "Please select an option for Allergy Conjunctivitis - Left Eye");
            return;
        }
        else if (allconj_right == 10) {
            showMessage("Warning", "Please select an option for Allergy Conjunctivitis - Right Eye");
            return;
        }
        else if (night_left == 10) {
            showMessage("Warning", "Please select an option for Night Blindness - Left Eye");
            return;
        }
        else if (night_right == 10) {
            showMessage("Warning", "Please select an option for Night Blindness - Right Eye");
            return;
        }
        else if (congp_left == 10) {
            showMessage("Warning", "Please select an option for Congenital Ptosis - Left Eye");
            return;
        }
        else if (congp_right == 10) {
            showMessage("Warning", "Please select an option for Congenital Ptosis - Right Eye");
            return;
        }
        else if (congd_left == 10) {
            showMessage("Warning", "Please select an option for Congenital Developmental Cataract - Left Eye");
            return;
        }
        else if (congd_right == 10) {
            showMessage("Warning", "Please select an option for Congenital Developmental Cataract - Right Eye");
            return;
        }
        else if (amb_left == 10) {
            showMessage("Warning", "Please select an option for Amblyopia - Left Eye");
            return;
        }
        else if (amb_right == 10) {
            showMessage("Warning", "Please select an option for Amblyopia - Right Eye");
            return;
        }
        else if (nys_left == 10) {
            showMessage("Warning", "Please select an option for Nystagmus - Left Eye");
            return;
        }
        else if (nys_right == 10) {
            showMessage("Warning", "Please select an option for Nystagmus - Right Eye");
            return;
        }
        else if (fund_left == 10) {
            showMessage("Warning", "Please select an option for Fundus Examination - Left Eye");
            return;
        }
        else if (fund_right == 10) {
            showMessage("Warning", "Please select an option for Fundus Examination - Right Eye");
            return;
        }
        else {


            try{
            //creating insertion query
            String insert_query = "'" + EyeActivity1.eye_sid + "'," +
                    "'" + EyeActivity1.vt_d_r + "'," +
                    "'" + EyeActivity1.vt_n_r + "'," +
                    "'" + EyeActivity1.vt_d_l + "'," +
                    "'" + EyeActivity1.vt_n_l + "'," +
                    "'" + EyeActivity1.upper.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.spherical_dist_right+EyeActivity1.Spherical_rt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.spherical_near_right+EyeActivity1.Spherical_rt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Cylinder_rt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Cylinder_rt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Axis_rt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Axis_rt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.spherical_dist_left+EyeActivity1.Spherical_lt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.spherical_near_left+EyeActivity1.Spherical_lt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Cylinder_lt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Cylinder_lt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Axis_lt_dist.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.Axis_lt_near.getText().toString().trim() + "'," +
                    "'" + EyeActivity1.lower.getText().toString().trim()+ "'," +
                    "'" + cvis_right + "'," +
                    "'" + Cvis_right.getText().toString().trim() + "'," +
                    "'" + cvis_left + "'," +
                    "'" + Cvis_left.getText().toString().trim() + "'," +
                    "'" + bitot_right + "'," +
                    "'" + Bitot_right.getText().toString().trim() + "'," +
                    "'" + bitot_left + "'," +
                    "'" + Bitot_left.getText().toString().trim() + "'," +
                    "'" + allconj_right + "'," +
                    "'" + Allerconj_right.getText().toString().trim() + "'," +
                    "'" + allconj_left + "'," +
                    "'" + Allerconj_left.getText().toString().trim() + "'," +
                    "'" + night_right + "'," +
                    "'" + Night_right.getText().toString().trim() + "'," +
                    "'" + night_left + "'," +
                    "'" + Night_left.getText().toString().trim() + "'," +
                    "'" + congp_right + "'," +
                    "'" + Congp_right.getText().toString().trim() + "'," +
                    "'" + congp_left + "'," +
                    "'" + Congp_left.getText().toString().trim() + "'," +
                    "'" + congd_right + "'," +
                    "'" + Congd_right.getText().toString().trim() + "'," +
                    "'" + congd_left + "'," +
                    "'" + Congd_left.getText().toString().trim() + "'," +
                    "'" + amb_right + "'," +
                    "'" + Amb_right.getText().toString().trim() + "'," +
                    "'" + amb_left + "'," +
                    "'" + Amb_left.getText().toString().trim() + "'," +
                    "'" + nys_right + "'," +
                    "'" + Nys_right.getText().toString().trim() + "'," +
                    "'" + nys_left + "'," +
                    "'" + Nys_left.getText().toString().trim() + "'," +
                    "'" + fund_right + "'," +
                    "'" + Fund_right.getText().toString().trim() + "'," +
                    "'" + fund_left + "'," +
                    "'" + Fund_left.getText().toString().trim() + "'," +
                    "'" + Other.getText().toString() + "'";
                System.out.println("EYE " + insert_query);

            //inserting into database
            database.execSQL("INSERT INTO eye VALUES (" + insert_query + ")");

            for(int x = 0; x < pic_count_eye; x++)
            {
                String insert_image_query = "'" + EyeActivity1.eye_sid + "'," +
                        "'" + EyeActivity1.eye_sid+"_eye_"+x + "',"+
                        "'" + Environment.getExternalStorageDirectory()+"/Images/"+EyeActivity1.eye_sid+"_eye_"+x+".jpg" + "'";

                database.execSQL("INSERT INTO images VALUES(" + insert_image_query + ")");
            }

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

                EyeActivity1.eye_sid="";

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
        builder.setPositiveButton("OK",null);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.show();

    }

    public void getBack(){

        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);

    }
    public void Getback() {

        Intent i = new Intent(this, EyeActivity1.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Getback();
    }

    //Declaration of variables only for Camera function
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    //Declaration of photoId counter for ent
    static int pic_count_eye = 0;
    //Method -> Camera Functions
    public void capture() {
        //PhotoId declaration
        String pid = EyeActivity1.eye_sid+"_eye_"+pic_count_eye;
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, pid+ ".jpg");
        //System.out.println("****File_name***"+image.toString());
        Uri uriSavedImage = Uri.fromFile(image);
        //PhotoId counter being incremented for new photo
        pic_count_eye++;
        //System.out.println("****pic_count***"+pic_count);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Result of Capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent

            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Images/" + EyeActivity1.eye_sid+ ".jpg");
            if (imgFile.exists()) {
                //image = encodeTobase64(myBitmap);
            }
        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture
            Toast.makeText(this, "Photo not saved", Toast.LENGTH_SHORT).show();
        } else {
            // Image capture failed, advise user
            Toast.makeText(this, "Photo Capture Failed", Toast.LENGTH_SHORT).show();
        }
    }


}
