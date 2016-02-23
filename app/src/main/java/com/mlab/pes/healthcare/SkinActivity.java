package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class SkinActivity extends ActionBarActivity {

    //Declaring sid -> studentID(must)
    static String skin_sid;
    String treatment="";

    EditText impression,Scabtext, Pityaltext, Phrynotext, Pedicultext, Pityvertext, Imptext, Paputext, Tintext, Miltext, Viraltext, Other, Xerosis;

	TextView skinStdId;
	
    int scab=10,pital=10,phry=10,pedi=10,pitver=10,imp=10,papu=10,tin=10,mil=10,vir=10,referal=10,xerosis=10;

    SQLiteDatabase database;

    //creating table query
    RelativeLayout layout1;
    int index;

    LinearLayout layout2,layout3,layout4,layout5,layout6;
    int i=-1,check=0;

    String treat_text;

    EditText Text2,Text3,Text4;
    TextView UnitText,no1,no2,no3,no4,no5;
    AutoCompleteTextView Text1;
    String Dialogarr[]= new String[4];
    Button BTN1,BTN2;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15,text16,text17,text18,text19,text20;


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
            "  pity INTEGER[1]," +
            "  pity_com VARCHAR[140]," +
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
            "  xerosis INTEGER[1]," +
            "  xerosis_com VARCHAR[140]," +
            "  others VARCHAR[140],"+
            "  impression VARCHAR[140],"+
            "  treatment VARCHAR[1000],"+
            "  referal INTEGER[1]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        app=this;

        //Re-Initializing pid_count for every new student
        pic_count_skin = 0;
        //Invoking StudentID Dialog box
        studentidDialog();


        impression=(EditText)findViewById(R.id.impression);
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
        Xerosis=(EditText) findViewById(R.id.xerosis_text);
        Other = (EditText) findViewById(R.id.add_text);


        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout6 = (LinearLayout) findViewById(R.id.layout6);

        text1 = (TextView)findViewById(R.id.text11);
        text3 = (TextView)findViewById(R.id.text31);
        text4 = (TextView)findViewById(R.id.text41);
        text5 = (TextView)findViewById(R.id.text12);
        text7 = (TextView)findViewById(R.id.text32);
        text8 = (TextView)findViewById(R.id.text42);
        text9 = (TextView)findViewById(R.id.text13);
        text11 = (TextView)findViewById(R.id.text33);
        text12 = (TextView)findViewById(R.id.text43);
        text13 = (TextView)findViewById(R.id.text14);
        text15 = (TextView)findViewById(R.id.text34);
        text16 = (TextView)findViewById(R.id.text44);
        text17 = (TextView)findViewById(R.id.text15);
        text19 = (TextView)findViewById(R.id.text35);
        text20 = (TextView)findViewById(R.id.text45);

        no1 = (TextView)findViewById(R.id.text01);
        no2 = (TextView)findViewById(R.id.text02);
        no3 = (TextView)findViewById(R.id.text03);
        no4 = (TextView)findViewById(R.id.text04);
        no5 = (TextView)findViewById(R.id.text05);


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


    /*private void AddTextLayout(TextView textView1,TextView textView2,TextView textView3,TextView textView4, int marginLeft, int marginTop, int marginRight, int marginBottom,int size, String dialogarr) {
        check++;
        System.out.println("checkinnnnnnng" + check);
        LinearLayout.LayoutParams textLayoutParameters = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        textLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        textView1.setTextSize(size);
        textView1.setText(dialogarr);
        textView1.setLayoutParams(textLayoutParameters);
        textView2.setTextSize(size);
        textView2.setText(dialogarr);
        textView2.setLayoutParams(textLayoutParameters);
        textView3.setTextSize(size);
        textView3.setText(dialogarr);
        textView3.setLayoutParams(textLayoutParameters);
        textView4.setTextSize(size);
        textView4.setText(dialogarr);
        textView4.setLayoutParams(textLayoutParameters);


    }*/



    public void PLUS(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Treatment");
        dialog.setContentView(R.layout.treat_dialog);
        dialog.setCancelable(false);

        final String[] auto= getResources().getStringArray(R.array.Treatment);
        Text1 = (AutoCompleteTextView) dialog.findViewById(R.id.text1);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, auto  );
        Text1.setAdapter(adapter);

        TextWatcher textWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int p=0;p<11;p++)
                {
                    if(Text1.getText().toString().equals(auto[p]))
                    {
                        check=1;
                        break;
                    }
                }
                for(int p=12;p<21;p++)
                {
                    if(Text1.getText().toString().equals(auto[p]))
                    {
                        check=2;
                        UnitText.setText("*");
                        break;
                    }

                }
                for(int p=22;p<29;p++)
                {
                    if(Text1.getText().toString().equals(auto[p]))
                    {
                        check=3;

                        break;
                    }
                }
                if(Text1.getText().toString().equals(auto[11]))
                {
                    check=4;
                    UnitText.setText("ml");
                }
                for(int l=0;l<auto.length;l++)
                    if(Text1.getText().toString().equals(auto[l]))
                    {
                        index =l;
                        break;
                    }

                System.out.println("cooool" + index);
            }
        };

        Text1.addTextChangedListener(textWatcher);




        Text2 = (EditText) dialog.findViewById(R.id.text2);
        Text3 = (EditText) dialog.findViewById(R.id.text3);

        BTN1 = (Button) dialog.findViewById(R.id.addButton1);
        BTN2 = (Button) dialog.findViewById(R.id.addButton2);

        UnitText = (TextView)dialog.findViewById(R.id.unit);
        dialog.show();
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Text1, Text2, Text3);
                dialog.dismiss();
            }
        });

        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                dialog.dismiss();
            }
        });
       /*SETVALUES(dialog);*/
    }

    public void validate(AutoCompleteTextView Text1, EditText Text2,EditText Text3)
    {
        if(Text1.length()==0)
        {

        }
        else if(Text2.length()==0)
        {

        }
        else if (Text3.length()==0)
        {

        }

        switch(i)
        {
            case 0: layout2.setVisibility(View.VISIBLE);text1.setText(Text1.getText().toString());text3.setText(Text2.getText().toString()+UnitText.getText());text4.setText(Text3.getText().toString());
                treat_text=""+no1.getText().toString()+"@"+text1.getText()+"@"+text3.getText().toString()+"@"+text4.getText().toString()+"days$";break;
            case 1:layout3.setVisibility(View.VISIBLE);text5.setText(Text1.getText().toString());text7.setText(Text2.getText().toString()+UnitText.getText());text8.setText(Text3.getText().toString());
                treat_text=""+no2.getText().toString()+"@"+text5.getText()+"@"+text7.getText().toString()+"@"+text8.getText().toString()+"days$";break;
            case 2:layout4.setVisibility(View.VISIBLE);text9.setText(Text1.getText().toString());text11.setText(Text2.getText().toString()+UnitText.getText());text12.setText(Text3.getText().toString());
                treat_text=""+no3.getText().toString()+"@"+text9.getText()+"@"+text11.getText().toString()+"@"+text12.getText().toString()+"days$";break;
            case 3:layout5.setVisibility(View.VISIBLE);text13.setText(Text1.getText().toString());text15.setText(Text2.getText().toString()+UnitText.getText());text16.setText(Text3.getText().toString());
                treat_text=""+no4.getText().toString()+"@"+text13.getText()+"@"+text15.getText().toString()+"@"+text16.getText().toString()+"days$";break;
            case 4:layout6.setVisibility(View.VISIBLE);text17.setText(Text1.getText().toString());text19.setText(Text2.getText().toString()+ UnitText.getText());text20.setText(Text3.getText().toString());
                treat_text=""+no5.getText().toString()+"@"+text17.getText()+"@"+text19.getText().toString()+"@"+text20.getText().toString()+"days$";break;

        }
        treatment=treatment+treat_text;
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

            case R.id.xerosis_yes:
                Xerosis.setVisibility(v.VISIBLE);
                xerosis=1;
                break;
            case R.id.xerosis_no:
                Xerosis.setVisibility(v.GONE);
                xerosis=0;
                break;

            case R.id.required:
                referal=1;
                break;
            case R.id.notRequired:
                referal=0;
                break;

        }
    }

    public void Next() {
            if (pic_count_skin ==0) {
                showMessage("Warning", "Please Take a picture of the Student");
                return;
            }
            else if (scab == 10) {
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
            }
            else if (xerosis == 10 ) {
                showMessage("Warning", "Please select an option for Xerosis");
                return;
            }
            else if (referal == 10 ) {
                showMessage("Warning", "Please select an option for Referral");
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
                        "'" + pitver + "'," +
                        "'" + Pityvertext.getText().toString().trim() + "'," +
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
                        "'" + xerosis + "'," +
                        "'" + Xerosis.getText().toString().trim() + "'," +
                        "'" + Other.getText().toString().trim() + "',"+
                        "'" + impression.getText().toString().trim() + "',"+
                        "'" + treatment + "',"+
                        "'" + referal + "'";

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
        studentID.setText(UpdateActivity.schoolId);
        studentID.setSelection(8);
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
