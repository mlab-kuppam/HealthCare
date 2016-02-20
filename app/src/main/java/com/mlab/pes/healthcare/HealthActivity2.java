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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class HealthActivity2 extends ActionBarActivity {

    EditText vit_b_com,vit_c_com,vit_others,gs_others,gsdeform,uti_com,siez_com,muc_others,worm_infest,Acute,gastro_other,nerve_other,behav_other,Other,MSDeform,ADHD,Bedwetting,Other_disease,injury;

    int ac=10,wo=10,msdef=10,se=10,ad=10,uti=10,bed=10,gsdef=10,vitc=10,vitb=10,oth=10,ho=10;

    RelativeLayout Worm,Seizure,UTI,GSDeform,VitC,VitB;

    CheckBox Pass,Pan,Pab,Skles,Pasthis,Drug,Drib,Freq,Burn,Phim,Undestes,Hypo,Cong_her,Bleed,Pete,Angular,Geo;

    TextView hlt2StdId;

    //String worm_pass,pruritis_ani,pain_abd,skin_les,past_history,antiepi_drug,dribling,inc_freq,burn_mict,phim,undestes,hypo,congher,bleed,pete,angular,geotong;
    //Amogh! Wouldn't it be better if we stored the value as 1 when the check box is checked rather than storing the string
    int worm_pass=0,pruritis_ani=0,pain_abd=0,skin_les=0,past_history=0,antiepi_drug=0,dribling=0,inc_freq=0,burn_mict=0,phim=0,undestes=0,hypo=0,congher=0,bleed=0,pete=0,angular=0,geotong=0;

    String[] checkboxnames = new String[]{"Passing worms","Pruritis ani","Pain abdomen","Skin lesions","Past history","On antiepileptic drug","Dribbling","Increased frequency","H/O burning micturation","Phimosis","Undescended testes","Hypospadiasis","Congenital hernias","Bleeding gums","Petechial haemorrhages","Angular chelitis","Geographical tongue"};

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
            "  ms_d_com VARCHAR[140]," +
            "  ms_others VARCHAR[140]," +
            "  ns_s INTEGER[1]," +
            "  ns_s_ph INTEGER[1] ," +
            "  ns_s_ad INTEGER[1] ," +
            "  ns_s_com VARCHAR[140]," +
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
            "  oid INTEGER[1]," +
            "  oid_com VARCHAR[140]," +
            "  hoin INTEGER[1]," +
            "  hoin_com VARCHAR[140]," +
            "  others VARCHAR[140]";


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
        siez_com=(EditText)findViewById(R.id.seiz_text);
        muc_others=(EditText)findViewById(R.id.musculo_text);
        worm_infest=(EditText)findViewById(R.id.worm_text);
        Acute = (EditText)findViewById(R.id.acute_text);
        gastro_other = (EditText)findViewById(R.id.gastro_text);
        MSDeform = (EditText)findViewById(R.id.deform_text);
        nerve_other = (EditText)findViewById(R.id.nerve_text);
        behav_other = (EditText)findViewById(R.id.behave_text);
        ADHD = (EditText)findViewById(R.id.adhd_text);
        Bedwetting = (EditText)findViewById(R.id.bed_text);
        Other_disease = (EditText)findViewById(R.id.other_text);
        injury = (EditText)findViewById(R.id.injury_text);
        Other = (EditText)findViewById(R.id.end_text);



        Worm = (RelativeLayout)findViewById(R.id.worm);
        Seizure = (RelativeLayout)findViewById(R.id.seizure);
        UTI = (RelativeLayout)findViewById(R.id.uti);
        GSDeform = (RelativeLayout)findViewById(R.id.GSdeform);
        VitC = (RelativeLayout)findViewById(R.id.vitc);
        VitB = (RelativeLayout)findViewById(R.id.vitb);

        Pass = (CheckBox)findViewById(R.id.pass);
        Pan = (CheckBox)findViewById(R.id.pruri);
        Pab = (CheckBox)findViewById(R.id.abd);
        Skles = (CheckBox)findViewById(R.id.skinles);
        Pasthis = (CheckBox)findViewById(R.id.past_hist);
        Drug = (CheckBox)findViewById(R.id.anti_drug);
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
                        "  photo_id VARCHAR[20] ," +
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

            case R.id.deform_yes: MSDeform.setVisibility(v.VISIBLE);msdef=1;
                break;
            case R.id.deform_no: MSDeform.setVisibility(v.GONE);msdef=0;
                break;

            case R.id.seiz_yes: Seizure.setVisibility(v.VISIBLE);se=1;
                break;
            case R.id.seiz_no: Seizure.setVisibility(v.GONE);se=0;
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

            case R.id.other_yes: Other_disease.setVisibility(v.VISIBLE);oth=1;
                break;
            case R.id.other_no: Other_disease.setVisibility(v.GONE);oth=0;
                break;

            case R.id.injury_yes: injury.setVisibility(v.VISIBLE);ho=1;
               break;
            case R.id.injury_no: injury.setVisibility(v.GONE);ho=0;
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

            case R.id.past_hist: //past_history=checkboxnames[4];
                past_history=1;
                break;
            case R.id.anti_drug:// antiepi_drug=checkboxnames[5];
                antiepi_drug=1;
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

        }
    }

    public void Next() {

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
        else if (oth == 10 ) {
            showMessage("Warning", "Please select an option for Other Infectious Diseases");
            return;
        }
        else if (ho == 10 ) {
            showMessage("Warning", "Please select an option for H/O Injuries");
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
                    "'" + MSDeform.getText().toString().trim() + "'," +
                    "'" + muc_others.getText().toString().trim() + "'," +
                    "'" + se + "'," +
                    "'" + past_history + "'," +
                    "'" + antiepi_drug + "'," +
                    "'" + siez_com.getText().toString().trim() + "'," +
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
                    "'" + oth + "'," +
                    "'" + Other_disease.getText().toString().trim() + "'," +
                    "'" + ho + "'," +
                    "'" + injury.getText().toString().trim() + "'," +
                    "'" + Other.getText().toString().trim() + "'";

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
        Getback();
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
