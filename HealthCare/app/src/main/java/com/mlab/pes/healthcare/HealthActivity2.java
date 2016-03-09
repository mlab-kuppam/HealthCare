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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class HealthActivity2 extends ActionBarActivity {

    EditText vit_b_com,vit_c_com,vit_others,gs_others,gsdeform,uti_com,siez_com,muc_others,worm_infest,Acute
            ,gastro_other,nerve_other,behav_other,Other,MSDeform,ADHD,Bedwetting,impression;

    int ac=10,wo=10,msdef=10,se=10,ad=10,uti=10,bed=10,gsdef=10,vitc=10,vitb=10,oth=10,ho=10,referal=10,treatment=10;

    String Treatment;

    RelativeLayout Worm,Seizure,UTI,GSDeform,VitC,VitB,deformities,seizureL;

    CheckBox Pass,Pan,Pab,Skles,Pasthis,Drug,Drib,Freq,Burn,Phim,Undestes,Hypo,Cong_her,Bleed,Pete,Angular,Geo;

    TextView hlt2StdId;

    //String worm_pass,pruritis_ani,pain_abd,skin_les,past_history,antiepi_drug,dribling,inc_freq,burn_mict,phim,undestes,hypo,congher,bleed,pete,angular,geotong;
    //Amogh! Wouldn't it be better if we stored the value as 1 when the check box is checked rather than storing the string
    int worm_pass=0,pruritis_ani=0,pain_abd=0,skin_les=0,past_history=0,antiepi_drug=0,dribling=0,inc_freq=0,burn_mict=0,phim=0,
            undestes=0,hypo=0,congher=0,bleed=0,pete=0,angular=0,geotong=0,bowlegs=0,knockedKnees=0,injuryMal=0;

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

    SQLiteDatabase database;

    public String table_query=
            "  child_id VARCHAR[11]," +
            "  gs_ag INTEGER[1]," +
            "  gs_ag_com VARCHAR[140]," +
            "  gs_wi INTEGER[1]," +
            "  gs_wi_pw INTEGER[1] ," +
            "  gs_wi_pa INTEGER[1] ," +
            "  gs_wi_pab INTEGER[1] ," +
            "  gs_wi_sl INTEGER[1] ," +
            "  gs_wi_com VARCHAR[140]," +
            "  gs_others VARCHAR[140]," +
            "  ms_d INTEGER[1]," +
            "  ms_d_bl INTEGER[1] ," +
            "  ms_d_kk INTEGER[1] ," +
            "  ms_d_irm INTEGER[1] ," +
            "  ms_d_com VARCHAR[140]," +
            "  ms_others VARCHAR[140]," +
            "  ns_s INTEGER[1]," +
            "  ns_s_tt INTEGER[1] ," +
            "  ns_others VARCHAR[140]," +
            "  bp_adhd INTEGER[1]," +
            "  bp_adhd_com VARCHAR[140]," +
            "  bp_others VARCHAR[140]," +
            "  gus_uti INTEGER[1]," +
            "  gus_uti_bm INTEGER[1] ," +
            "  gus_uti_if INTEGER[1] ," +
            "  gus_uti_dr INTEGER[1] ," +
            "  gus_uti_com VARCHAR[140]," +
            "  gus_bed INTEGER[1]," +
            "  gus_bed_com VARCHAR[140]," +
            "  gus_def INTEGER[1]," +
            "  gus_def_ch INTEGER[1] ," +
            "  gus_def_ut INTEGER[1] ," +
            "  gus_def_hy INTEGER[1] ," +
            "  gus_def_ph INTEGER[1] ," +
            "  gus_def_com VARCHAR[140]," +
            "  gus_others VARCHAR[140]," +
            "  vt_c INTEGER[1]," +
            "  vt_c_bg INTEGER[1] ," +
            "  vt_c_ph INTEGER[1] ," +
            "  vt_c_com VARCHAR[140]," +
            "  vt_b INTEGER[1]," +
            "  vt_b_ac INTEGER[1] ," +
            "  vt_b_gt INTEGER[1] ," +
            "  vt_b_com VARCHAR[140]," +
            "  vt_others VARCHAR[140]," +
            "  others VARCHAR[140],"+
            "  impression VARCHAR[140],"+
            "  treatment VARCHAR[1000],"+
            "  referal INTEGER[1]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health2);

        //Re-Initializing pid_count for every new student
        pic_count_health = 0;

        hlt2StdId = (TextView)findViewById(R.id.hlt1_std_id);
		hlt2StdId.setText(HealthActivity1.health_sid);

        vit_others=(EditText)findViewById(R.id.vitamin_text);
        vit_c_com=(EditText)findViewById(R.id.vitc_text);
        vit_b_com=(EditText)findViewById(R.id.vitb_text);
        gs_others=(EditText)findViewById(R.id.gs_text);
        gsdeform=(EditText)findViewById(R.id.gsdeform_text);
        uti_com=(EditText)findViewById(R.id.uti_text);
        muc_others=(EditText)findViewById(R.id.musculo_text);
        worm_infest=(EditText)findViewById(R.id.worm_text);
        Acute = (EditText)findViewById(R.id.acute_text);
        gastro_other = (EditText)findViewById(R.id.gastro_text);
        MSDeform = (EditText)findViewById(R.id.deform_text);
        nerve_other = (EditText)findViewById(R.id.nerve_text);
        behav_other = (EditText)findViewById(R.id.behave_text);
        ADHD = (EditText)findViewById(R.id.adhd_text);
        Bedwetting = (EditText)findViewById(R.id.bed_text);
        Other = (EditText)findViewById(R.id.end_text);


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



        Worm = (RelativeLayout)findViewById(R.id.worm);
        UTI = (RelativeLayout)findViewById(R.id.uti);
        GSDeform = (RelativeLayout)findViewById(R.id.GSdeform);
        VitC = (RelativeLayout)findViewById(R.id.vitc);
        VitB = (RelativeLayout)findViewById(R.id.vitb);
        deformities=(RelativeLayout)findViewById(R.id.deformitiesMus);
        seizureL=(RelativeLayout)findViewById(R.id.seizureL);

        Pass = (CheckBox)findViewById(R.id.pass);
        Pan = (CheckBox)findViewById(R.id.pruri);
        Pab = (CheckBox)findViewById(R.id.abd);
        Skles = (CheckBox)findViewById(R.id.skinles);
        Drib = (CheckBox)findViewById(R.id.drib);
        Freq = (CheckBox)findViewById(R.id.freq);
        Burn = (CheckBox)findViewById(R.id.burn);
        Phim = (CheckBox)findViewById(R.id.phimo);
        Undestes = (CheckBox)findViewById(R.id.undes_testes);
        Hypo = (CheckBox)findViewById(R.id.hypo);
        Cong_her = (CheckBox)findViewById(R.id.cong_hernia);

        //opening db
        database = openOrCreateDatabase("healthcare", Context.MODE_PRIVATE,null);
        //creating table if doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS health2(" + table_query + ")");

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
        getMenuInflater().inflate(R.menu.menu_health2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

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


    public void onRadioselect(View v)
    {
        switch(v.getId())
        {
            case R.id.acute_yes: Acute.setVisibility(v.VISIBLE);ac=1;
                break;
            case R.id.acute_no: Acute.setVisibility(v.GONE);ac=0;
                break;

            case R.id.worm_yes: Worm.setVisibility(v.VISIBLE);wo=1;
                break;
            case R.id.worm_no: Worm.setVisibility(v.GONE);wo=0;
                break;

            case R.id.deform_yes: deformities.setVisibility(v.VISIBLE);msdef=1;
                break;
            case R.id.deform_no: deformities.setVisibility(v.GONE);msdef=0;
                break;

            case R.id.seiz_yes: seizureL.setVisibility(v.VISIBLE);se=1;
                break;
            case R.id.seiz_no: seizureL.setVisibility(v.GONE);se=0;
                break;

            case R.id.treatment_yes: treatment=1;
                break;
            case R.id.treatment_no: treatment=0;
                break;

            case R.id.adhd_yes: ADHD.setVisibility(v.VISIBLE);ad=1;
                break;
            case R.id.adhd_no: ADHD.setVisibility(v.GONE);ad=0;
                break;

            case R.id.uti_yes: UTI.setVisibility(v.VISIBLE);uti=1;
                break;
            case R.id.uti_no: UTI.setVisibility(v.GONE);uti=0;
                break;

            case R.id.bed_yes: Bedwetting.setVisibility(v.VISIBLE);bed=1;
                break;
            case R.id.bed_no: Bedwetting.setVisibility(v.GONE);bed=0;
                break;

            case R.id.gsdeform_yes: GSDeform.setVisibility(v.VISIBLE);gsdef=1;
                break;
            case R.id.gsdeform_no: GSDeform.setVisibility(v.GONE);gsdef=0;
                break;

            case R.id.vitc_yes: VitC.setVisibility(v.VISIBLE);vitc=1;
                break;
            case R.id.vitc_no: VitC.setVisibility(v.GONE);vitc=0;
                break;

            case R.id.vitb_yes: VitB.setVisibility(v.VISIBLE);vitb=1;
                break;
            case R.id.vitb_no: VitB.setVisibility(v.GONE);vitb=0;
                break;




            case R.id.required:
                referal=1;
                break;
            case R.id.notRequired:
                referal=0;
                break;

        }
    }

    public void onCheckBoxclicked(View v)
    {
        switch(v.getId())
        {
            case R.id.pass: //worm_pass=checkboxnames[0];
                worm_pass=1;
                break;
            case R.id.pruri: //pruritis_ani=checkboxnames[1];
                pruritis_ani=1;
                break;
            case R.id.abd: //pain_abd=checkboxnames[2];
                pain_abd=1;
                break;
            case R.id.skinles: //skin_les=checkboxnames[3];
                skin_les=1;
                break;


            case R.id.drib: //dribling=checkboxnames[6];
                dribling=1;
                break;
            case R.id.freq:// inc_freq=checkboxnames[7];
                inc_freq=1;
                break;
            case R.id.burn:// burn_mict=checkboxnames[8];
                burn_mict=1;
                break;

            case R.id.phimo:// phim=checkboxnames[9];
                phim=1;
                break;
            case R.id.undes_testes:// undestes=checkboxnames[10];
                undestes=1;
                break;
            case R.id.hypo: //hypo=checkboxnames[11];
                hypo=1;
                break;
            case R.id.cong_hernia: //congher=checkboxnames[12];
                congher=1;
                break;

            case R.id.bleed_gums: //bleed=checkboxnames[13];
                bleed=1;
                break;
            case R.id.haemorrhage: //pete=checkboxnames[14];
                pete=1;
                break;

            case R.id.angchel://angular=checkboxnames[15];
                angular=1;
                break;
            case R.id.geo_tong: //geotong=checkboxnames[16];
                geotong=1;
                break;
            case R.id.bowlegs:
                bowlegs=1;
                break;
            case R.id.knockedknees:
                knockedKnees=1;
                break;
            case R.id.injuryMal:
                injuryMal=1;
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
        Treatment=treat_text;

        if (ac == 10 ) {
            showMessage("Error", "Please select an option for Gastro-Intestinal System - Acute Gastro-Enteritis");
            return;
        }
        else if (wo == 10 ) {
            showMessage("Warning", "Please select an option for Gastro-Intestinal System - Worm Infestation");
            return;
        }
        else if (msdef == 10 ) {
            showMessage("Warning", "Please select an option for Musculoskeletal System - Deformities");
            return;
        }
        else if (se == 10 ) {
            showMessage("Warning", "Please select an option for Nervous System - Seizures");
            return;
        }
        else if (se == 1 && treatment==10) {
            showMessage("Warning", "Please select an option for Nervous System - Seizures - Treatment Taken");
            return;
        }
        else if (ad == 10 ) {
            showMessage("Warning", "Please select an option for Behavioural Problems - ADHD");
            return;
        }
        else if (uti == 10 ) {
            showMessage("Warning", "Please select an option for Genitourinary System - UTI");
            return;
        }
        else if (bed == 10 ) {
            showMessage("Warning", "Please select an option for Genitourinary System - Bedwetting");
            return;
        }
        else if (gsdef == 10 ) {
            showMessage("Warning", "Please select an option for Genitourinary System - Deformities");
            return;
        }
        else if (vitc == 10 ) {
            showMessage("Warning", "Please select an option for Vitamin Deficiency - Vitamin C Deficiency");
            return;
        }
        else if (vitb == 10 ) {
            showMessage("Warning", "Please select an option for Vitamin Deficiency - Vitamin B Complex Deficiency");
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
            String insert_query = "'" + HealthActivity1.health_sid + "'," +
                    "'" + ac + "'," +
                    "'" + Acute.getText().toString().trim() + "'," +
                    "'" + wo + "'," +
                    "'" + worm_pass + "'," +
                    "'" + pruritis_ani + "'," +
                    "'" + pain_abd + "'," +
                    "'" + skin_les + "'," +
                    "'" + worm_infest.getText().toString().trim() + "'," +
                    "'" + gastro_other.getText().toString().trim() + "'," +
                    "'" + msdef + "'," +
                    "'" + bowlegs + "'," +
                    "'" + knockedKnees + "'," +
                    "'" + injuryMal + "'," +
                    "'" + MSDeform.getText().toString().trim() + "'," +
                    "'" + muc_others.getText().toString().trim() + "'," +
                    "'" + se + "'," +
                    "'" + treatment + "'," +
                    "'" + nerve_other.getText().toString().trim() + "'," +
                    "'" + ad + "'," +
                    "'" + ADHD.getText().toString().trim() + "'," +
                    "'" + behav_other.getText().toString().trim() + "'," +
                    "'" + uti + "'," +
                    "'" + burn_mict + "'," +
                    "'" + inc_freq + "'," +
                    "'" + dribling + "'," +
                    "'" + uti_com.getText().toString().trim() + "'," +
                    "'" + bed + "'," +
                    "'" + Bedwetting.getText().toString().trim() + "'," +
                    "'" + gsdef + "'," +
                    "'" + congher + "'," +
                    "'" + undestes + "'," +
                    "'" + hypo + "'," +
                    "'" + phim + "'," +
                    "'" + gsdeform.getText().toString().trim() + "'," +
                    "'" + gs_others.getText().toString().trim() + "'," +
                    "'" + vitc + "'," +
                    "'" + bleed + "'," +
                    "'" + pete + "'," +
                    "'" + vit_c_com.getText().toString().trim() + "'," +
                    "'" + vitb + "'," +
                    "'" + angular + "'," +
                    "'" + geotong + "'," +
                    "'" + vit_b_com.getText().toString().trim() + "'," +
                    "'" + vit_others.getText().toString().trim() + "'," +
                    "'" + Other.getText().toString().trim() + "',"+
                    "'" + Impression + "',"+
                    "'" + Treatment + "',"+
                    "'" + referal + "'";

            //inserting into database
            database.execSQL("INSERT INTO health2 VALUES (" + insert_query + ")");

            for (int x = 0; x < pic_count_health; x++) {

                String insert_image_query = "'" + HealthActivity1.health_sid + "'," +
                        "'" +  HealthActivity1.health_sid+"_health_"+x + "'," +
                        "'" +  Environment.getExternalStorageDirectory() + "/Images/" + HealthActivity1.health_sid + "_health_" + x + ".jpg" + "'";
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

        Intent intent = new Intent(this,UpdateActivity.class);
        startActivity(intent);
    }

    public void Getback()
    {
        try
        {
            database.delete("child", "child_id = ?", new String[] { HealthActivity1.health_sid });
        }
        catch(Exception e)
        {
            showMessage("Error","Child Id "+HealthActivity1.health_sid+" not found!");
        }
        finally
        {
            database.close();
        }
        Intent intent = new Intent(this,UpdateActivity.class);
        startActivity(intent);
    }


    public void onBackPressed()
    {
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
    public void deletePicture(){
        //If the entry is cancelled, this code will delete the images of the session
        File file = new File(Environment.getExternalStorageDirectory(), "Images");
        if(file.exists())
        {
            int x;
            //loop will delete all photos taken during the cancelled session
            for(x = 0; x < pic_count_health; x++)
            {
                File del_image = new File(Environment.getExternalStorageDirectory()+"/Images/"+HealthActivity1.health_sid+"_health_"+x+".jpg");
                //System.out.println("***Deleted***"+del_image.toString());
                del_image.delete();
            }
        }
    }

    //Declaration of variables only for Camera function
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    //Declaration of photoId counter for ent
    static int pic_count_health = 0;
    //Method -> Camera Functions
    public void capture() {
        //PhotoId declaration
        String pid = HealthActivity1.health_sid+"_health_"+pic_count_health;
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, pid+ ".jpg");
        //System.out.println("****File_name***"+image.toString());
        Uri uriSavedImage = Uri.fromFile(image);
        //PhotoId counter being incremented for new photo
        pic_count_health++;
        //System.out.println("****pic_count***"+pic_count);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Result of Capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent
            String image;

            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Images/" + HealthActivity1.health_sid+ ".jpg");
            if (imgFile.exists()) {
               // image = encodeTobase64(myBitmap);
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
