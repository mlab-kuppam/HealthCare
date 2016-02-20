package com.mlab.pes.healthcare;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class ENTActivity extends ActionBarActivity {

    //Declaring sid -> studentID(must)
    String sid;
	
	TextView entStdId;

    EditText Otisleft,Otisright,Asomleft,Asomright,Csomleft,Csomright,Impactleft,Impactright,Impairleft,Impairright,Epi,
            Adeno,Phary,Aller,Speech,Others;

    int oti_left=10,oti_right=10,asom_left=10,asom_right=10,csom_left=10,csom_right=10,impact_left=10,impact_right=10,impair_left=10,impair_right=10,
            epi=10,adeno=10,phary=10,aller=10,speech=10;

    SQLiteDatabase database;

    private static ENTActivity app;
    public static ENTActivity get(){
        return app;
    }

    public String table_query="  child_id VARCHAR[11]," +
            "  oe_r INTEGER[1], " +
            "  oe_r_com VARCHAR[140]," +
            "  oe_l INTEGER[1]," +
            "  oe_l_com VARCHAR[140]," +
            "  as_r INTEGER[1]," +
            "  as_r_com VARCHAR[140]," +
            "  as_l INTEGER[1]," +
            "  as_l_com VARCHAR[140]," +
            "  cs_r INTEGER[1]," +
            "  cs_r_com VARCHAR[140]," +
            "  cs_l INTEGER[1]," +
            "  cs_l_com VARCHAR[140]," +
            "  iw_r INTEGER[1]," +
            "  iw_r_com VARCHAR[140]," +
            "  iw_l INTEGER[1]," +
            "  iw_l_com VARCHAR[140]," +
            "  ih_r INTEGER[1]," +
            "  ih_r_com VARCHAR[140]," +
            "  ih_l INTEGER[1]," +
            "  ih_l_com VARCHAR[140]," +
            "  ep INTEGER[1]," +
            "  ep_com VARCHAR[140]," +
            "  ad INTEGER[1]," +
            "  ad_com VARCHAR[140]," +
            "  ph INTEGER[1]," +
            "  ph_com VARCHAR[140]," +
            "  ar INTEGER[1]," +
            "  ar_com VARCHAR[140]," +
            "  sd INTEGER[1]," +
            "  sd_com VARCHAR[140]," +
            "  others VARCHAR[140]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ent);

        app=this;

        //Re-Initializing pid_count for every new student
        pic_count_ent = 0;
        //Invoking StudentID Dialog box
        studentidDialog();
		
		entStdId = (TextView)findViewById(R.id.ent_std_id);
        entStdId.setText(sid);

        Otisleft = (EditText)findViewById(R.id.oti_left_text);
        Otisright = (EditText)findViewById(R.id.oti_right_text);

        Asomleft = (EditText)findViewById(R.id.asom_left_text);
        Asomright = (EditText)findViewById(R.id.asom_right_text);


        Csomleft = (EditText)findViewById(R.id.csom_left_text);
        Csomright = (EditText)findViewById(R.id.csom_right_text);

        Impactleft = (EditText)findViewById(R.id.impact_left_text);
        Impactright = (EditText)findViewById(R.id.impact_right_text);

        Impairleft = (EditText)findViewById(R.id.impair_left_text);
        Impairright = (EditText)findViewById(R.id.impair_right_text);

        Epi = (EditText)findViewById(R.id.epi_text);
        Adeno = (EditText)findViewById(R.id.adeno_text);
        Phary = (EditText)findViewById(R.id.phary_text);
        Aller = (EditText)findViewById(R.id.aller_text);
        Speech = (EditText)findViewById(R.id.speech_text);
        Others = (EditText)findViewById(R.id.add_text);

        //opening db
        database = openOrCreateDatabase("healthcare",Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS ent(" + table_query + ")");

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
        getMenuInflater().inflate(R.menu.menu_ent, menu);
        return true;
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
            case R.id.action_camera:
                capture();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void earClick(View view) {
        switch (view.getId()) {
            case R.id.oti_left_yes:
                Otisleft.setVisibility(view.VISIBLE);
                oti_left=1;
                break;
            case R.id.oti_right_yes:
                Otisright.setVisibility(view.VISIBLE);
                oti_right=1;
                break;
            case R.id.oti_left_no:
                Otisleft.setVisibility(view.GONE);
                oti_left=0;
                break;
            case R.id.oti_right_no:
                Otisright.setVisibility(view.GONE);
                oti_right=0;
                break;

            case R.id.asom_left_yes:
                Asomleft.setVisibility(view.VISIBLE);
                asom_left=1;
                break;
            case R.id.asom_right_yes:
                Asomright.setVisibility(view.VISIBLE);
                asom_right=1;
                break;
            case R.id.asom_left_no:
                Asomleft.setVisibility(view.GONE);
                asom_left=0;
                break;
            case R.id.asom_right_no:
                Asomright.setVisibility(view.GONE);
                asom_right=0;
                break;

            case R.id.csom_left_yes:
                Csomleft.setVisibility(view.VISIBLE);
                csom_left=1;
                break;
            case R.id.csom_right_yes:
                Csomright.setVisibility(view.VISIBLE);
                csom_right=1;
                break;
            case R.id.csom_left_no:
                Csomleft.setVisibility(view.GONE);
                csom_left=0;
                break;
            case R.id.csom_right_no:
                Csomright.setVisibility(view.GONE);
                csom_right=0;
                break;

            case R.id.impact_left_yes:
                Impactleft.setVisibility(view.VISIBLE);
                impact_left=1;
                break;
            case R.id.impact_right_yes:
                Impactright.setVisibility(view.VISIBLE);
                impact_right=1;
                break;
            case R.id.impact_left_no:
                Impactleft.setVisibility(view.GONE);
                impact_left=0;
                break;
            case R.id.impact_right_no:
                Impactright.setVisibility(view.GONE);
                impact_right=0;
                break;

            case R.id.impair_left_yes:
                Impairleft.setVisibility(view.VISIBLE);
                impair_left=1;
                break;
            case R.id.impair_right_yes:
                Impairright.setVisibility(view.VISIBLE);
                impair_right=1;
                break;
            case R.id.impair_left_no:
                Impairleft.setVisibility(view.GONE);
                impair_left=0;
                break;
            case R.id.impair_right_no:
                Impairright.setVisibility(view.GONE);
                impair_right=0;
                break;

            case R.id.epi_yes:
                Epi.setVisibility(view.VISIBLE);
                epi=1;
                break;
            case R.id.epi_no:
                Epi.setVisibility(view.GONE);
                epi=0;
                break;

            case R.id.adeno_yes:
                Adeno.setVisibility(view.VISIBLE);
                adeno=1;
                break;
            case R.id.adeno_no:
                Adeno.setVisibility(view.GONE);
                adeno=0;
                break;

            case R.id.phary_yes:
                Phary.setVisibility(view.VISIBLE);
                phary=1;
                break;
            case R.id.phary_no:
                Phary.setVisibility(view.GONE);
                phary=0;
                break;

            case R.id.aller_yes:
                Aller.setVisibility(view.VISIBLE);
                aller=1;
                break;
            case R.id.aller_no:
                Aller.setVisibility(view.GONE);
                aller=0;
                break;

            case R.id.speech_yes:
                Speech.setVisibility(view.VISIBLE);
                speech=1;
                break;
            case R.id.speech_no:
                Speech.setVisibility(view.GONE);
                speech=0;
                break;

        }
    }

    public void Next() {

            if (oti_left == 10 ) {
                showMessage("Warning", "Please select an option for Otitis Externa - Left Ear");
                return;
            }
            else if (oti_right == 10 ) {
                showMessage("Warning", "Please select an option for Otitis Externa - Right Ear");
                return;
            }
            else if (asom_left == 10 ) {
                showMessage("Warning", "Please select an option for ASOM - Left Ear");
                return;
            }
            else if (asom_right == 10 ) {
                showMessage("Warning", "Please select an option for ASOM - Right Ear");
                return;
            }
            else if (csom_left == 10 ) {
                showMessage("Warning", "Please select an option for CSOM - Left Ear");
                return;
            }
            else if (csom_right == 10 ) {
                showMessage("Warning", "Please select an option for CSOM - Right Ear");
                return;
            }
            else if ( impact_left == 10 ) {
                showMessage("Warning", "Please select an option for Imapacted Wax - Left Ear");
                return;
            }
            else if (impact_right == 10 ) {
                showMessage("Warning", "Please select an option for Imapacted Wax - Right Ear");
                return;
            }
            else if (impair_left == 10  ) {
                showMessage("Warning", "Please select an option for Impaired Hearing - Left Ear");
                return;
            }
            else if (impair_right == 10  ) {
                showMessage("Warning", "Please select an option for Impaired Hearing - Right Ear");
                return;
            }
            else if (epi == 10  ) {
                showMessage("Warning", "Please select an option for H/O Epistaxis");
                return;
            }
            else if (adeno == 10 ) {
                showMessage("Warning", "Please select an option for Adenotonsilitis");
                return;
            }
            else if (phary == 10 ) {
                showMessage("Warning", "Please select an option for Pharyngitis");
                return;
            }
            else if (aller == 10 ) {
                showMessage("Warning", "Please select an option for Allergic Rhinitis");
                return;
            }
            else if (speech == 10 ) {
                showMessage("Warning", "Please select an option for Speech Defects");
                return;
            }else
             {
                try {
                //creating insertion query
                String insert_query = "'" + sid + "'," +
                        "'" + oti_right + "','" + Otisright.getText().toString().trim() + "'," +
                        "'" + oti_left + "','" + Otisleft.getText().toString().trim() + "'," +
                        "'" + asom_right + "','" + Asomright.getText().toString().trim() + "'," +
                        "'" + asom_left + "','" + Asomleft.getText().toString().trim() + "'," +
                        "'" + csom_right + "','" + Csomright.getText().toString().trim() + "'," +
                        "'" + csom_left + "','" + Csomleft.getText().toString().trim() + "'," +
                        "'" + impact_right + "','" + Impactright.getText().toString().trim() + "'," +
                        "'" + impact_left + "','" + Impactleft.getText().toString().trim() + "'," +
                        "'" + impair_right + "','" + Impairright.getText().toString().trim() + "'," +
                        "'" + impair_left + "','" + Impairleft.getText().toString().trim() + "'," +
                        "'" + epi + "','" + Epi.getText().toString().trim() + "'," +
                        "'" + adeno + "','" + Adeno.getText().toString().trim() + "'," +
                        "'" + phary + "','" + Phary.getText().toString().trim() + "'," +
                        "'" + aller + "','" + Aller.getText().toString().trim() + "'," +
                        "'" + speech + "','" + Speech.getText().toString().trim() + "'," +
                        "'" + Others.getText().toString().trim() + "'";

                //inserting into database
                database.execSQL("INSERT INTO ent VALUES (" + insert_query + ")");
                for (int x = 0; x < pic_count_ent; x++) {

                    String insert_image_query = "'" + sid + "'," +
                            "'" + sid+"_ent_"+x + "',"+
                            "'" + Environment.getExternalStorageDirectory() + "/Images/" + sid + "_ent_" + x + ".jpg" + "'";

                    database.execSQL("INSERT INTO images VALUES(" + insert_image_query+")");
                }
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
        builder.setMessage(message);
        builder.setCancelable(false);
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

                sid = studentID.getText().toString().toUpperCase();
                //System.out.println(skin_sid);
                if (!UpdateActivity.isStudentID(sid)) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    entStdId.setText(sid);
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

    //Declaration of variables only for Camera function
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    //Declaration of photoId counter for ent
    static int pic_count_ent = 0;
    //Method -> Camera Functions
    public void capture() {
        //PhotoId declaration
        String pid = sid+"_ent_"+pic_count_ent;
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, pid+ ".jpg");
        //System.out.println("****File_name***"+image.toString());
        Uri uriSavedImage = Uri.fromFile(image);
        //PhotoId counter being incremented for new photo
        pic_count_ent++;
        //System.out.println("****pic_count***"+pic_count);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Result of Capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent

            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Images/" + sid+ ".jpg");
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

    public void deletePicture(){
        //If the entry is cancelled, this code will delete the images of the session
        File file = new File(Environment.getExternalStorageDirectory(), "Images");
        if(file.exists())
        {
            int x;
            //loop will delete all photos taken during the cancelled session
            for(x = 0; x < pic_count_ent; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/"+sid+"_ent_"+x+".jpg");
                //System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }

}
