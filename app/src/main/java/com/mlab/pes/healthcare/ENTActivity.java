package com.mlab.pes.healthcare;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class ENTActivity extends ActionBarActivity {

    //Declaring sid -> studentID(must)
    String sid,treatment;
	
	TextView entStdId;

    RelativeLayout layout1;
    int index,valid=10;

    LinearLayout layout2,layout3,layout4,layout5,layout6;
    int i=-1,j=10,check=0;

    String treat_text,Impression;
    StringBuffer dos_text;

    EditText Text2,Text3,Text4;
    TextView UnitText,no1,no2,no3,no4,no5;
    AutoCompleteTextView Text1;
    Button BTN1,BTN2;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15,text16,text17,text18,text19,text20;


    MultiSpinner multiSpinner;

    RadioGroup wax_right,wax_left,tonsilitis,rhinitis,operated;

    EditText Otisleft,Otisright,Asomleft,Asomright,Csomleft,Csomright,Impairleft,Impairright,Epi,
            Adeno,Phary,Aller,Speech,Others,urti,impression;

    int oti_left=10,oti_right=10,asom_left=10,asom_right=10,csom_left=10,csom_right=10,impact_left=10,impact_right=10,impair_left=10,impair_right=10,
            epi=10,adeno=10,phary=10,aller=10,speech=10,referal=10,URTI=10,cleft=10,operatedOn=10,Impactleft=2,Impactright=2,Tonsilitis=2,Rhinitis=2;

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
            "  iw_r_com INTEGER[1]," +
            "  iw_l INTEGER[1]," +
            "  iw_l_com INTEGER[1]," +
            "  ih_r INTEGER[1]," +
            "  ih_r_com VARCHAR[140]," +
            "  ih_l INTEGER[1]," +
            "  ih_l_com VARCHAR[140]," +
            "  ep INTEGER[1]," +
            "  ep_com VARCHAR[140]," +
            "  ad INTEGER[1]," +
            "  ad_com INTEGER[1]," +
            "  ph INTEGER[1]," +
            "  ph_com VARCHAR[140]," +
            "  ar INTEGER[1]," +
            "  ar_com INTEGER[1]," +
            "  sd INTEGER[1]," +
            "  sd_com VARCHAR[140]," +
            "  urti INTEGER[1]," +
            "  urti_com VARCHAR[140]," +
            "  cleft INTEGER[1]," +
            "  cleft_operated INTEGER[1]," +
            "  others VARCHAR[140],"+
            "  impression VARCHAR[140],"+
            "  treatment VARCHAR[1000],"+
            "  referal INTEGER[1]";

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


        Impairleft = (EditText)findViewById(R.id.impair_left_text);
        Impairright = (EditText)findViewById(R.id.impair_right_text);

        Epi = (EditText)findViewById(R.id.epi_text);
        Phary = (EditText)findViewById(R.id.phary_text);
        Speech = (EditText)findViewById(R.id.speech_text);
        Others = (EditText)findViewById(R.id.add_text);
        urti = (EditText)findViewById(R.id.urti_text);


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

        operated = (RadioGroup) findViewById(R.id.operatedLayout);

        wax_left=(RadioGroup) findViewById(R.id.impact_left_sub);
        wax_right=(RadioGroup) findViewById(R.id.impact_right_sub);
        tonsilitis=(RadioGroup) findViewById(R.id.adeno_text);
        rhinitis=(RadioGroup) findViewById(R.id.aller_text);

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

        dos_text= new StringBuffer();


        //opening db
        database = openOrCreateDatabase("healthcare",Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS ent(" + table_query + ")");

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
        getMenuInflater().inflate(R.menu.menu_ent, menu);
        return true;
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
                    Toast.makeText(getApplicationContext(), "" + treat_text, Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    layout5.setVisibility(View.VISIBLE);
                    text13.setText(Text1.getText().toString());
                    text15.setText(Text2.getText().toString() + UnitText.getText());
                    text16.setText(Text3.getText().toString());
                    Toast.makeText(getApplicationContext(), "" + treat_text, Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    layout6.setVisibility(View.VISIBLE);
                    text17.setText(Text1.getText().toString());
                    text19.setText(Text2.getText().toString() + UnitText.getText());
                    text20.setText(Text3.getText().toString());
                    Toast.makeText(getApplicationContext(), "" + treat_text, Toast.LENGTH_LONG).show();
                    break;
            }
        }


        dialog.dismiss();

    }

    public void showMessage(String title,String message,Dialog dialog)
    {
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

    public void CANCEL(View v)
    {
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
        }
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
                wax_left.setVisibility(view.VISIBLE);
                impact_left=1;
                break;
            case R.id.impact_right_yes:
                wax_right.setVisibility(view.VISIBLE);
                impact_right=1;
                break;
            case R.id.impact_left_no:
                wax_left.setVisibility(view.GONE);
                impact_left=0;
                break;
            case R.id.impact_right_no:
                wax_right.setVisibility(view.GONE);
                impact_right=0;
                break;

            case R.id.impact_left_impacted:
                Impactleft=1;
                break;
            case R.id.impact_right_impacted:
                Impactright=1;
                break;
            case R.id.impact_left_unimpacted:
                Impactleft=0;
                break;
            case R.id.impact_right_unimpacted:
                Impactright=0;
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
                tonsilitis.setVisibility(view.VISIBLE);
                adeno=1;
                break;
            case R.id.adeno_no:
                tonsilitis.setVisibility(view.GONE);
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
                rhinitis.setVisibility(view.VISIBLE);
                aller=1;
                break;
            case R.id.aller_no:
                rhinitis.setVisibility(view.GONE);
                aller=0;
                break;

            case R.id.aller_allergic:
                Rhinitis=1;
                break;
            case R.id.aller_infective:
                Rhinitis=0;
                break;
            case R.id.adeno_acute:
                Tonsilitis=1;
                break;
            case R.id.adeno_chronic:
                Tonsilitis=0;
                break;
            case R.id.speech_yes:
                Speech.setVisibility(view.VISIBLE);
                speech=1;
                break;
            case R.id.speech_no:
                Speech.setVisibility(view.GONE);
                speech=0;
                break;

            case R.id.urti_yes:
                urti.setVisibility(view.VISIBLE);
                URTI=1;
                break;
            case R.id.urti_no:
                urti.setVisibility(view.GONE);
                URTI=0;
                break;


            case R.id.cleft_yes:
                cleft=1;
                operated.setVisibility(view.VISIBLE);
                //NotApplicable.setChecked(false);
                //Applicable.setChecked(true);
                break;
            case R.id.cleft_no:
                cleft=0;
                operated.setVisibility(view.GONE);
                //Applicable.setChecked(false);
                //NotApplicable.setChecked(true);
                break;


            case R.id.operated:
                operatedOn=1;
                break;
            case R.id.notOperated:
                operatedOn=0;
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
                showMessage("Warning", "Please select an option for Wax - Left Ear");
                return;
            }
            else if ( impact_left==1 && Impactleft == 2 ) {
                showMessage("Warning", "Please select an option for Wax - Impacted/Unimpacted - Left Ear");
                return;
            }
            else if (impact_right == 10 ) {
                showMessage("Warning", "Please select an option for Wax - Right Ear");
                return;
            }
            else if ( impact_right==1 && Impactright == 2 ) {
                showMessage("Warning", "Please select an option for Wax - Impacted/Unimpacted - Right Ear");
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
                showMessage("Warning", "Please select an option for Tonsilitis");
                return;
            }
            else if (adeno == 1 && Tonsilitis==2 ) {
                showMessage("Warning", "Please select an option for Tonsilitis - Acute/Chronic");
                return;
            }
            else if (phary == 10 ) {
                showMessage("Warning", "Please select an option for Pharyngitis");
                return;
            }
            else if (aller == 10 ) {
                showMessage("Warning", "Please select an option for Rhinitis");
                return;
            }
            else if (aller == 1 && Rhinitis==2 ) {
                showMessage("Warning", "Please select an option for Rhinitis - Allergic/Infective");
                return;
            }
            else if (speech == 10 ) {
                showMessage("Warning", "Please select an option for Speech Defects");
                return;
            }
            else if (URTI == 10 ) {
                showMessage("Warning", "Please select an option for URTI");
                return;
            }
            else if (cleft == 10 ) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Oral Examination - Cleft Lip/ Cleft Palate");
                return;
            }
            else if (cleft == 1 && operatedOn==10 ) {
                showMessage("Warning", "Please select an option for Personal Hygiene - Oral Examination - Cleft Lip/ Cleft Palate - Operated");
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
            else
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
                        "'" + impact_right + "','" + Impactright + "'," +
                        "'" + impact_left + "','" + Impactleft + "'," +
                        "'" + impair_right + "','" + Impairright.getText().toString().trim() + "'," +
                        "'" + impair_left + "','" + Impairleft.getText().toString().trim() + "'," +
                        "'" + epi + "','" + Epi.getText().toString().trim() + "'," +
                        "'" + adeno + "','" + Tonsilitis + "'," +
                        "'" + phary + "','" + Phary.getText().toString().trim() + "'," +
                        "'" + aller + "','" + Rhinitis + "'," +
                        "'" + speech + "','" + Speech.getText().toString().trim() + "'," +
                        "'" + URTI + "','" + urti.getText().toString().trim() + "'," +
                        "'" + cleft + "'," +
                        "'" + operatedOn + "'," +
                        "'" + Others.getText().toString().trim() +"',"+
                        "'" + Impression + "',"+
                        "'" + treatment + "',"+
                        "'" + referal + "'";

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
