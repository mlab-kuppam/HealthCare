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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class SkinActivity extends ActionBarActivity {

    //Declaring sid -> studentID(must)
    static String skin_sid;

    EditText Scabtext, Pityaltext, Phrynotext, Pedicultext, Pityvertext, Imptext, Paputext, Tintext, Miltext, Viraltext, Other;

	TextView skinStdId;
	
    int scab=10,pital=10,phry=10,pedi=10,pitver=10,imp=10,papu=10,tin=10,mil=10,vir=10;

    SQLiteDatabase database;

    //creating table query


    private static SkinActivity app;
    public static SkinActivity get(){
        return app;
    }

    public String table_query=
            "  child_id VARCHAR[11]," +
            "  sc INTEGER[1]," +
            "  sc_com VARCHAR[140]," +
            "  pi INTEGER[1]," +
            "  pi_com VARCHAR[140]," +
            "  ph INTEGER[1]," +
            "  ph_com VARCHAR[140]," +
            "  pe INTEGER[1]," +
            "  pe_com VARCHAR[140]," +
            "  im INTEGER[1]," +
            "  im_com VARCHAR[140]," +
            "  pap INTEGER[1]," +
            "  pap_com VARCHAR[140]," +
            "  tc INTEGER[1]," +
            "  tc_com VARCHAR[140]," +
            "  mi INTEGER[1]," +
            "  mi_com VARCHAR[140]," +
            "  vi INTEGER[1]," +
            "  vi_com VARCHAR[140]," +
            "  others VARCHAR[140]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        app=this;

        //Re-Initializing pid_count for every new student
        pic_count_skin = 0;
        //Invoking StudentID Dialog box
        studentidDialog();

		
		skinStdId = (TextView)findViewById(R.id.skin_std_id);
        skinStdId.setText(skin_sid);

        Scabtext = (EditText) findViewById(R.id.scab_text);
        Pityaltext = (EditText) findViewById(R.id.pityalb_text);
        Phrynotext = (EditText) findViewById(R.id.phry_text);
        Pedicultext = (EditText) findViewById(R.id.pedi_text);
        Pityvertext = (EditText) findViewById(R.id.pityver_text);
        Imptext = (EditText) findViewById(R.id.imp_text);
        Paputext = (EditText) findViewById(R.id.papu_text);
        Tintext = (EditText) findViewById(R.id.tin_text);
        Miltext = (EditText) findViewById(R.id.mil_text);
        Viraltext = (EditText) findViewById(R.id.viral_text);
        Other = (EditText) findViewById(R.id.add_text);


        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS skin(" + table_query + ")");

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
        getMenuInflater().inflate(R.menu.menu_skin, menu);
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

    public void onRadioselect(View v) {
        switch (v.getId()) {
            case R.id.scab_yes:
                Scabtext.setVisibility(v.VISIBLE);
                scab=1;
                break;
            case R.id.scab_no:
                Scabtext.setVisibility(v.GONE);
                scab=0;
                break;

            case R.id.pityalb_yes:
                Pityaltext.setVisibility(v.VISIBLE);
                pital=1;
                break;
            case R.id.pityalb_no:
                Pityaltext.setVisibility(v.GONE);
                pital=0;
                break;

            case R.id.phry_yes:
                Phrynotext.setVisibility(v.VISIBLE);
                phry=1;
                break;
            case R.id.phry_no:
                Phrynotext.setVisibility(v.GONE);
                phry=0;
                break;

            case R.id.pedicule_yes:
                Pedicultext.setVisibility(v.VISIBLE);
                pedi=1;
                break;
            case R.id.pedicule_no:
                Pedicultext.setVisibility(v.GONE);
                pedi=0;
                break;

            case R.id.pityver_yes:
                Pityvertext.setVisibility(v.VISIBLE);
                pitver=1;
                break;
            case R.id.pityver_no:
                Pityvertext.setVisibility(v.GONE);
                pitver=0;
                break;

            case R.id.imp_yes:
                Imptext.setVisibility(v.VISIBLE);
                imp=1;
                break;
            case R.id.imp_no:
                Imptext.setVisibility(v.GONE);
                imp=0;
                break;

            case R.id.papu_yes:
                Paputext.setVisibility(v.VISIBLE);
                papu=1;
                break;
            case R.id.papu_no:
                Paputext.setVisibility(v.GONE);
                papu=0;
                break;

            case R.id.tin_yes:
                Tintext.setVisibility(v.VISIBLE);
                tin=1;
                break;
            case R.id.tin_no:
                Tintext.setVisibility(v.GONE);
                tin=0;
                break;


            case R.id.mil_yes:
                Miltext.setVisibility(v.VISIBLE);
                mil=1;
                break;
            case R.id.mil_no:
                Miltext.setVisibility(v.GONE);
                mil=0;
                break;


            case R.id.viral_yes:
                Viraltext.setVisibility(v.VISIBLE);
                vir=1;
                break;
            case R.id.viral_no:
                Viraltext.setVisibility(v.GONE);
                vir=0;
                break;

        }
    }

    public void Next() {
            if (scab == 10) {
                showMessage("Warning", "Please select an option for Scabies");
                return;
            }
            else if ( pital == 10) {
                showMessage("Warning", "Please select an option for PityriasisAlba");
                return;
            }
            else if ( phry == 10) {
                showMessage("Warning", "Please select an option for Phrynoderma");
                return;
            }
            else if ( pedi == 10 ) {
                showMessage("Warning", "Please select an option for Pediculosis");
                return;
            }
            else if ( pitver == 10 ) {
                showMessage("Warning", "Please select an option for Pityriasis Vrsicolor");
                return;
            }
            else if (imp == 10) {
                showMessage("Warning", "Please select an option for Impetigo");
                return;
            }
            else if ( papu == 10 ) {
                showMessage("Warning", "Please select an option for Papular urticaria");
                return;
            }
            else if (tin == 10 ) {
                showMessage("Warning", "Please select an option for Tinea Cruis/Corporis");
                return;
            }
            else if (mil == 10 ) {
                showMessage("Warning", "Please select an option for Miliaria");
                return;
            }
            else if (vir == 10 ) {
                showMessage("Warning", "Please select an option for Viral Warts");
                return;
            }else {

                try {
                String insert_query = "'" + skin_sid + "'," +
                        "'" + scab + "'," +
                        "'" + Scabtext.getText().toString().trim() + "'," +
                        "'" + pital + "'," +
                        "'" + Pityaltext.getText().toString().trim() + "'," +
                        "'" + phry + "'," +
                        "'" + Phrynotext.getText().toString().trim() + "'," +
                        "'" + pedi + "'," +
                        "'" + Pedicultext.getText().toString().trim() + "'," +
                        "'" + imp + "'," +
                        "'" + Imptext.getText().toString().trim() + "'," +
                        "'" + papu + "'," +
                        "'" + Paputext.getText().toString().trim() + "'," +
                        "'" + tin + "'," +
                        "'" + Tintext.getText().toString().trim() + "'," +
                        "'" + mil + "'," +
                        "'" + Miltext.getText().toString().trim() + "'," +
                        "'" + vir + "'," +
                        "'" + Viraltext.getText().toString().trim() + "'," +
                        "'" + Other.getText().toString().trim() + "'";

                //inserting into database
                database.execSQL("INSERT INTO skin VALUES (" + insert_query + ")");

                for(int x = 0; x < pic_count_skin; x++)
                {
                    String insert_image_query = "'" + skin_sid + "'," +
                            "'" + skin_sid+"_skin_"+x+ "',"+
                            "'" + Environment.getExternalStorageDirectory()+"/Images/"+skin_sid+"_skin_"+x+".jpg" + "'" ;

                    database.execSQL("INSERT INTO images VALUES (" + insert_image_query+")");
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

                    skin_sid="";
            }catch (Exception e){
                    System.out.println("ERROR IN DB");
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

                skin_sid = studentID.getText().toString().toUpperCase();
                //System.out.println(skin_sid);
                if (!UpdateActivity.isStudentID(skin_sid)) {
                    showError();
                    studentidDialog();
                } else {
                    setStudentID();
                    dialog.dismiss();
                    skinStdId.setText(skin_sid);
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
    static int pic_count_skin = 0;
    //Method -> Camera Functions
    public void capture() {
        //PhotoId declaration
        String pid = skin_sid+"_skin_"+pic_count_skin;
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, pid+ ".jpg");
        //System.out.println("****File_name***"+image.toString());
        Uri uriSavedImage = Uri.fromFile(image);
        //PhotoId counter being incremented for new photo
        pic_count_skin++;
        //System.out.println("****pic_count***"+pic_count_skin);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Result of Capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent

            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Images/" + skin_sid+"_skin_"+(pic_count_skin-1)+ ".jpg");
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
            for(x = 0; x < pic_count_skin; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/"+skin_sid+"_skin_"+x+".jpg");
                System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }
}
