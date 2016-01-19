package com.mlab.pes.healthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class HealthActivity2 extends Activity {

    EditText Acute,gastro_other,nerve_other,behav_other,Other,MSDeform,ADHD,Bedwetting,Other_disease,injury;

    int ac=10,wo=10,msdef=10,se=10,ad=10,uti=10,bed=10,gsdef=10,vitc=10,vitb=10,oth=10,ho=10,end=10;

    RelativeLayout Worm,Seizure,UTI,GSDeform,VitC,VitB;

    CheckBox Pass,Pan,Pab,Skles,Pasthis,Drug,Drib,Freq,Burn,Phim,Undestes,Hypo,Cong_her,Bleed,Pete,Angular,Geo;

    TextView Std_id;

    String worm_pass,pruritis_ani,pain_abd,skin_les,past_history,antiepi_drug,dribling,inc_freq,burn_mict,phim,undestes,hypo,congher,bleed,pete,angular,geotong;

    String[] checkboxnames = new String[]{"Passing worms","Pruritis ani","Pain abdomen","Skin lesions","Past history","On antiepileptic drug","Dribbling","Increased frequency","H/O burning micturation","Phimosis","Undescended testes","Hypospadiasis","Congenital hernias","Bleeding gums","Petechial haemorrhages","Angular chelitis","Geographical tongue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health1);

        //Re-Initializing pid_count for every new student
        pic_count_health = 0;

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

        Std_id = (TextView)findViewById(R.id.hlt_std_id);


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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health1, menu);
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

            case R.id.end_yes: Other.setVisibility(v.VISIBLE);end=1;
                break;
            case R.id.end_no: Other.setVisibility(v.GONE);end=0;
                break;
        }
    }

    public void onCheckBoxclicked(View v)
    {
        switch(v.getId())
        {
            case R.id.pass: worm_pass=checkboxnames[0];
                break;
            case R.id.pruri: pruritis_ani=checkboxnames[1];
                break;
            case R.id.abd: pain_abd=checkboxnames[2];
                break;
            case R.id.skinles: skin_les=checkboxnames[3];
                break;

            case R.id.past_hist: past_history=checkboxnames[4];
                break;
            case R.id.anti_drug: antiepi_drug=checkboxnames[5];
                break;

            case R.id.drib: dribling=checkboxnames[6];
                break;
            case R.id.freq: inc_freq=checkboxnames[7];
                break;
            case R.id.burn: burn_mict=checkboxnames[8];
                break;

            case R.id.phimo: phim=checkboxnames[9];
                break;
            case R.id.undes_testes: undestes=checkboxnames[10];
                break;
            case R.id.hypo: hypo=checkboxnames[11];
                break;
            case R.id.cong_hernia: congher=checkboxnames[12];
                break;

            case R.id.bleed_gums: bleed=checkboxnames[13];
                break;
            case R.id.haemorrhage: pete=checkboxnames[14];
                break;

            case R.id.angchel:angular=checkboxnames[15];
                break;
            case R.id.geo_tong: geotong=checkboxnames[16];
                break;

        }
    }

    public void Next()
    {

        if(ac==10 || wo==10 || msdef==10 || se==10 || ad==10 || uti==10 || bed==10 || gsdef==10 || vitc==10 || vitb==10 || oth==10 ||
                ho==10 || end==10 )
        {
            showMessage("Error","Please enter all values");
            return;
        }

        Intent intent = new Intent(this,HealthActivity1.class);
        startActivity(intent);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void Getback()
    {
        Intent intent = new Intent(this,HealthActivity1.class);
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

            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Images/" + HealthActivity1.health_sid+ ".jpg");
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



    //Method to encode to image to base 64 string for syncing
    public String encodeTobase64(Bitmap image) {
        System.gc();
        if (image == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

}
