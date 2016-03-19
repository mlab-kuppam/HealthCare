package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkinActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    //Declaring sid -> studentID(must)
    static String skin_sid;
    String treatment="",impression;

    EditText Advice,Scabtext, Pityaltext, Phrynotext, Pedicultext, Pityvertext, Imptext, Paputext, Tintext, Miltext, Viraltext, Other, Xerosis;

	TextView skinStdId;
	
    int scab=10,pital=10,phry=10,pedi=10,pitver=10,imp=10,papu=10,tin=10,mil=10,vir=10,referal=10,xerosis=10,adviceOthers=10;

    SQLiteDatabase database;

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

    Spinner advice;

    private static SkinActivity app;
    public static SkinActivity get(){
        return app;
    }

    //creating table query

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
        Advice = (EditText) findViewById(R.id.advice_others);


        impression="";
        multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(UpdateActivity.list, "Select..", new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for(int i=0;i<selected.length;i++){
                    if(selected[i])
                    {
                        if(impression.length()==0)
                            impression=""+i;
                        else
                            impression=impression+","+i;

                        if(i==selected.length-1){
                            Advice.setVisibility(View.VISIBLE);
                            adviceOthers=1;
                        }
                    }
                    else{
                        if(i==selected.length-1){
                            Advice.setVisibility(View.GONE);
                            adviceOthers=0;
                        }
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
        database.execSQL("CREATE TABLE IF NOT EXISTS skin(" + table_query + ")");

        String image_table_query=
                "  child_id VARCHAR[10] ," +
                        "  photo_id VARCHAR[30] ," +
                        "  image TEXT" ;
        //creating image table
        database.execSQL("CREATE TABLE IF NOT EXISTS images( " + image_table_query + " )");

    }


    //Method to get selected item in the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==advice)
        {
            impression=parent.getItemAtPosition(position).toString();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                for (int p = 0; p < 22; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("");
                        break;
                    }

                }
                for (int p = 23; p < 26; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("ml");
                        break;
                    }

                }
                for (int p = 27; p < 41; p++) {
                    if (Text1.getText().toString().equals(auto[p])) {
                        UnitText.setText("\u00b0");
                        break;
                    }

                }

                for (int p = 42; p < 49; p++) {
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

        if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0 && text13.getText().length() != 0 && text17.getText().length() != 0) {
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
        else if (text1.getText().length() != 0 && text5.getText().length() != 0 && text9.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$"
                    + "" + no3.getText().toString() + "@" + text9.getText() + "@" + text11.getText().toString() + "@" + text12.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0 && text5.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$" + ""
                    + no2.getText().toString() + "@" + text5.getText() + "@" + text7.getText().toString() + "@" + text8.getText().toString() + "days$";
        }
        else if (text1.getText().length() != 0) {
            treat_text ="" + no1.getText().toString() + "@" + text1.getText() + "@" + text3.getText().toString() + "@" + text4.getText().toString() + "days$";
        }
        treatment=treat_text;
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
            else if(impression.equals("")){
                showMessage("Warning", "Please select an option for Advice");
                return;
            }
            else if(adviceOthers==1 && Advice.getText().toString().trim().length()==0){
                showMessage("Warning", "Please enter Advice");
                return;
            }
            else if (referal == 10 ) {
                showMessage("Warning", "Please select an option for Referral");
                return;
            }
            else {
                if(adviceOthers==1)
                    impression=impression+":"+Advice.getText().toString().trim();

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
                        "'" + impression+ "',"+
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
