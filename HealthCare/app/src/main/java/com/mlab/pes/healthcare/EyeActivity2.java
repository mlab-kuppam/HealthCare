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

public class EyeActivity2 extends ActionBarActivity {
    String treatment,Impression;

    static String eye_sid;

    EditText Cvis_left,Cvis_right,Bitot_left,Bitot_right,Allerconj_left,Allerconj_right,Night,Congp_left,Congp_right,Congd_left,Congd_right,Amb_left,Amb_right,
            Nys_left,Nys_right,Fund_left,Fund_right,Other,impression;
			
	TextView eye1StdId;

    int cvis_left=10,cvis_right=10,bitot_left=10,bitot_right=10,allconj_left=10,allconj_right=10,night=10,congp_left=10,congp_right=10,congd_left=10,congd_right=10,
            amb_left=10,amb_right=10,nys_left=10,nys_right=10,fund_left=10,fund_right=10,referal=10;

    RelativeLayout layout1;
    int index,valid=10;

    LinearLayout layout2,layout3,layout4,layout5,layout6,layout7,layout8;
    int i=-1,j=10,check=0;

    String treat_text;
    StringBuffer dos_text;

    EditText Text2,Text3,Text4;
    TextView UnitText,no1,no2,no3,no4,no5,no6,no7;
    AutoCompleteTextView Text1;
    Button BTN1,BTN2;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15,text16,text17,text18,text19,text20,text21,text23,text24,text25,text27,text28;

    MultiSpinner multiSpinner;

    SQLiteDatabase database;

    public String table_query=
            "  child_id VARCHAR[11]," +
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
            "  nb INTEGER[1]," +
            "  nb_com VARCHAR[140]," +
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
            "  fe_l INTEGER[1]," +
            "  others VARCHAR[140],"+
            "  impression VARCHAR[140],"+
            "  treatment VARCHAR[1000],"+
            "  referal INTEGER[1]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye2);

        studentidDialog();

        //Re-Initializing pid_count for every new student
        pic_count_eye = 0;
		
		eye1StdId = (TextView)findViewById(R.id.eye1_std_id);

        Cvis_left = (EditText) findViewById(R.id.cvis_left_text);
        Cvis_right = (EditText) findViewById(R.id.cvis_right_text);
        Bitot_left = (EditText) findViewById(R.id.bitot_left_text);
        Bitot_right = (EditText) findViewById(R.id.bitot_right_text);
        Allerconj_left = (EditText) findViewById(R.id.allconj_left_text);
        Allerconj_right = (EditText) findViewById(R.id.allconj_right_text);
        Night = (EditText) findViewById(R.id.night_right_text);
        Congp_left = (EditText) findViewById(R.id.congp_left_text);
        Congp_right = (EditText) findViewById(R.id.congp_right_text);
        Congd_left = (EditText) findViewById(R.id.congd_left_text);
        Congd_right = (EditText) findViewById(R.id.congd_right_text);
        Amb_left = (EditText) findViewById(R.id.amb_left_text);
        Amb_right = (EditText) findViewById(R.id.amb_right_text);
        Nys_left = (EditText) findViewById(R.id.nys_left_text);
        Nys_right = (EditText) findViewById(R.id.nys_right_text);
        Other = (EditText) findViewById(R.id.add_text);


        Impression="";
        multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(UpdateActivity.list, "Select..", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for(int i=0;i<selected.length;i++){
                    if(selected[i])
                    {
                        if(Impression.length()==0)
                            Impression=""+i;
                        else
                            Impression=Impression+","+i;
                    }
                }
            }
        });
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout7 = (LinearLayout) findViewById(R.id.layout7);
        layout8 = (LinearLayout) findViewById(R.id.layout8);

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
        text21 = (TextView)findViewById(R.id.text16);
        text23 = (TextView)findViewById(R.id.text36);
        text24 = (TextView)findViewById(R.id.text46);
        text25 = (TextView)findViewById(R.id.text17);
        text27 = (TextView)findViewById(R.id.text37);
        text28 = (TextView)findViewById(R.id.text47);

        no1 = (TextView)findViewById(R.id.text01);
        no2 = (TextView)findViewById(R.id.text02);
        no3 = (TextView)findViewById(R.id.text03);
        no4 = (TextView)findViewById(R.id.text04);
        no5 = (TextView)findViewById(R.id.text05);
        no6 = (TextView)findViewById(R.id.text06);
        no7 = (TextView)findViewById(R.id.text07);

        dos_text= new StringBuffer();


        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS eye(" + table_query + ")");

        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[30] ," +
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

    //Methods for treatment page
    public void PLUS(View v) {
        i++;
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("PESIMSR Medical Store");
        dialog.setContentView(R.layout.treat_dialog);
        dialog.setCancelable(false);

        final String[] auto= getResources().getStringArray(R.array.Treatment);

        Text1 = (AutoCompleteTextView) dialog.findViewById(R.id.text1);
        Text2 = (EditText) dialog.findViewById(R.id.text2);

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
                for (int p = 0; p < 18; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("");
                        break;
                    }

                }
                for (int p = 19; p < 22; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("ml");
                        break;
                    }

                }
                for (int p = 23; p < 37; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("\u00b0");
                        break;
                    }

                }

                for (int p = 38; p < 45; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("");
                        break;
                    }

                }
            }
        };

        Text1.addTextChangedListener(textWatcher);

        Text3 = (EditText) dialog.findViewById(R.id.text3);

        BTN1 = (Button) dialog.findViewById(R.id.addButton1);
        BTN2 = (Button) dialog.findViewById(R.id.addButton2);

        UnitText = (TextView)dialog.findViewById(R.id.unit);
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Text1.length()==0)
                {
                    showMessage("Warning","Please enter the Drug name",dialog);
                    valid=0;
                    Text2.append("-");
                    return;
                }
                else if(Text2.length()==0)
                {
                    showMessage("Warning","Please enter the Dosage",dialog);
                    valid=0;
                    return;
                }
                else if (Text3.length()==0)
                {
                    showMessage("Warning","Please enter the Duration",dialog);
                    valid=0;
                    return;
                }


                validate(Text1, Text2, Text3, Text4, UnitText, dialog);

            }
        });

        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void validate(AutoCompleteTextView Text1, EditText Text2,EditText Text3,EditText Text4, TextView UnitText,Dialog dialog)
    {

        if(j!=10)
        {
            switch (j)
            {
                case 1:
                    layout2.setVisibility(View.VISIBLE);
                    text1.setText(Text1.getText().toString());
                    text3.setText(Text2.getText().toString() + UnitText.getText());
                    text4.setText(Text3.getText().toString());
                    j=10;
                    break;
                case 2:
                    layout3.setVisibility(View.VISIBLE);
                    text5.setText(Text1.getText().toString());
                    text7.setText(Text2.getText().toString() + UnitText.getText());
                    text8.setText(Text3.getText().toString());
                    j=10;
                    break;
                case 3:
                    layout4.setVisibility(View.VISIBLE);
                    text9.setText(Text1.getText().toString());
                    text11.setText(Text2.getText().toString() + UnitText.getText());
                    text12.setText(Text3.getText().toString());
                    j=10;
                    break;
                case 4:
                    layout5.setVisibility(View.VISIBLE);
                    text13.setText(Text1.getText().toString());
                    text15.setText(Text2.getText().toString() + UnitText.getText());
                    text16.setText(Text3.getText().toString());
                    j=10;
                    break;
                case 5:
                    layout6.setVisibility(View.VISIBLE);
                    text17.setText(Text1.getText().toString());
                    text19.setText(Text2.getText().toString() + UnitText.getText());
                    text20.setText(Text3.getText().toString());
                    j = 10;
                    break;
                case 6:
                    layout7.setVisibility(View.VISIBLE);
                    text21.setText(Text1.getText().toString());
                    text23.setText(Text2.getText().toString() + UnitText.getText());
                    text24.setText(Text3.getText().toString());
                    j = 10;
                    break;
                case 7:
                    layout8.setVisibility(View.VISIBLE);
                    text25.setText(Text1.getText().toString());
                    text27.setText(Text2.getText().toString() + UnitText.getText());
                    text28.setText(Text3.getText().toString());
                    j = 10;
                    break;
            }
        }
        else {
            switch (i) {
                case 0:
                    layout2.setVisibility(View.VISIBLE);
                    text1.setText(Text1.getText().toString());
                    text3.setText(Text2.getText().toString() + UnitText.getText());
                    text4.setText(Text3.getText().toString());
                    break;
                case 1:
                    layout3.setVisibility(View.VISIBLE);
                    text5.setText(Text1.getText().toString());
                    text7.setText(Text2.getText().toString() + UnitText.getText());
                    text8.setText(Text3.getText().toString());
                    break;
                case 2:
                    layout4.setVisibility(View.VISIBLE);
                    text9.setText(Text1.getText().toString());
                    text11.setText(Text2.getText().toString() + UnitText.getText());
                    text12.setText(Text3.getText().toString());
                    break;
                case 3:
                    layout5.setVisibility(View.VISIBLE);
                    text13.setText(Text1.getText().toString());
                    text15.setText(Text2.getText().toString() + UnitText.getText());
                    text16.setText(Text3.getText().toString());
                    break;
                case 4:
                    layout6.setVisibility(View.VISIBLE);
                    text17.setText(Text1.getText().toString());
                    text19.setText(Text2.getText().toString() + UnitText.getText());
                    text20.setText(Text3.getText().toString());
                    break;
                case 5:
                    layout7.setVisibility(View.VISIBLE);
                    text21.setText(Text1.getText().toString());
                    text23.setText(Text2.getText().toString() + UnitText.getText());
                    text24.setText(Text3.getText().toString());
                    break;
                case 6:
                    layout8.setVisibility(View.VISIBLE);
                    text25.setText(Text1.getText().toString());
                    text27.setText(Text2.getText().toString() + UnitText.getText());
                    text28.setText(Text3.getText().toString());
                    break;
            }
        }


        dialog.dismiss();

    }
    public void showMessage(String title,String message,Dialog dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();

    }

    public void CANCEL(View v) {
        switch (v.getId())
        {
            case R.id.del1:
                layout2.setVisibility(View.GONE);
                text1.setText("");
                text3.setText("");
                text4.setText("");j=1;break;
            case R.id.del2:
                layout3.setVisibility(View.GONE);
                text5.setText("");
                text7.setText("");
                text8.setText("");j=2;break;
            case R.id.del3:
                layout4.setVisibility(View.GONE);
                text9.setText("");
                text11.setText("");
                text12.setText("");j=3;break;
            case R.id.del4:
                layout5.setVisibility(View.GONE);
                text13.setText("");
                text15.setText("");
                text16.setText("");j=4;break;
            case R.id.del5:
                layout6.setVisibility(View.GONE);
                text17.setText("");
                text19.setText("");
                text20.setText("");j=5;break;
            case R.id.del6:
                layout7.setVisibility(View.GONE);
                text21.setText("");
                text23.setText("");
                text24.setText("");j=6;break;
            case R.id.del7:
                layout8.setVisibility(View.GONE);
                text25.setText("");
                text27.setText("");
                text28.setText("");j=7;break;
        }
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
                    dialog.dismiss();
                    eye1StdId.setText(eye_sid);
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

            case R.id.night_right_yes:
                Night.setVisibility(view.VISIBLE);
                night = 1;
                break;
            case R.id.night_right_no:
                Night.setVisibility(view.GONE);
                night = 0;
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
                fund_left=1;
                break;
            case R.id.fund_right_yes:
                fund_right=1;
                break;
            case R.id.fund_left_no:
                fund_left=0;
                break;
            case R.id.fund_right_no:
                fund_right=0;
                break;

        }
    }

    public void onRadioselect(View v) {
        switch (v.getId()) {
            case R.id.required:
                referal = 1;
                break;
            case R.id.notRequired:
                referal = 0;
                break;
        }
    }

    public void Next() {

        if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 && text13.getText().length() != 0 && text17.getText().length() != 0 && text21.getText().length() != 0 && text25.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$"
                    + "" + no4.getText().toString() + "@" + text13.getText() + "@" + text15.getText().toString() + "@" + text16.getText().toString() + "days$"
                    + "" + no5.getText().toString() + "@" + text17.getText() + "@" + text19.getText().toString() + "@" + text20.getText().toString() + "days$"
                    + "" + no6.getText().toString() + "@" + text21.getText() + "@" + text23.getText().toString() + "@" + text24.getText().toString() + "days$"
                    + "" + no7.getText().toString() + "@" + text25.getText() + "@" + text27.getText().toString() + "@" + text28.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 && text13.getText().length() != 0 && text17.getText().length() != 0 && text21.getText().length() != 0 ) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$"
                    + "" + no4.getText().toString() + "@" + text13.getText() + "@" + text15.getText().toString() + "@" + text16.getText().toString() + "days$"
                    + "" + no5.getText().toString() + "@" + text17.getText() + "@" + text19.getText().toString() + "@" + text20.getText().toString() + "days$"
                    + "" + no6.getText().toString() + "@" + text21.getText() + "@" + text23.getText().toString() + "@" + text24.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 && text13.getText().length() != 0 && text17.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$"
                    + "" + no4.getText().toString() + "@" + text13.getText() + "@" + text15.getText().toString() + "@" + text16.getText().toString() + "days$"
                    + "" + no5.getText().toString() + "@" + text17.getText() + "@" + text19.getText().toString() + "@" + text20.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 && text13.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$"
                    + "" + no4.getText().toString() + "@" + text13.getText() + "@" + text15.getText().toString() + "@" + text16.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 ) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$"
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$"
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$";
        }

        treatment=treat_text;


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
        else if (night == 10) {
            showMessage("Warning", "Please select an option for Night Blindness");
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
        else if (referal == 10 ) {
            showMessage("Warning", "Please select an option for Referal");
            return;
        }
        else if(Impression.equals("")){
            showMessage("Warning", "Please select an option for Advice");
            return;
        }
        else {


            try{
            //creating insertion query
            String insert_query = "'" + eye_sid + "'," +
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
                    "'" + night + "'," +
                    "'" + Night.getText().toString().trim() + "'," +
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
                    "'" + fund_left + "'," +
                    "'" + Other.getText().toString() + "',"+
                    "'" + Impression + "',"+
                    "'" + treatment + "',"+
                    "'" + referal + "'";
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

        Intent i = new Intent(this, UpdateActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {

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
    public void deletePicture(){
        //If the entry is cancelled, this code will delete the images of the session
        File file = new File(Environment.getExternalStorageDirectory(), "Images");
        if(file.exists())
        {
            int x;
            //loop will delete all photos taken during the cancelled session
            for(x = 0; x < pic_count_eye; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/" + EyeActivity1.eye_sid+ ".jpg");
                //System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }


}
