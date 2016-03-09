package com.mlab.pes.healthcare;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aamir on 07-02-2016.
 */
public class syncing {
    //To Sync the details to the Cloud(Pushing Data to the Cloud)
    public void SYNC() {
//10.3.32.56



        LinkedHashMap linkedHashMap=new LinkedHashMap();
        linkedHashMap.put("URL", "sync_2.php");
        String t = "";
        ArrayList<String> tablenames = new ArrayList<String>();
        LinkedHashMap json_fin = new LinkedHashMap();
        try {

            Cursor r = MainActivity.db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

            if (r.moveToFirst()) {
                while (!r.isAfterLast()) {
                    tablenames.add(r.getString(r.getColumnIndex("name")));
                    r.moveToNext();
                }
            }
            r.close();

            for (int i = 1; i < tablenames.size(); i++) {
                String q = "SELECT * FROM " + tablenames.get(i);
                Cursor c = MainActivity.db.rawQuery(q, null);
                c.moveToFirst();
                if (c != null && c.getCount() > 0 && !tablenames.get(i).equals("images") && !tablenames.get(i).equals("child_references")&&!tablenames.get(i).equals("school_references")&& !tablenames.get(i).equals("android_metadata")) {
                    ArrayList array = new ArrayList();
                    do {
                        LinkedHashMap rows = new LinkedHashMap();

                        for (int l = 0; l < c.getColumnCount(); l++) {
                            String name = c.getColumnName(l);
                            switch (c.getType(l)) {
                                case Cursor.FIELD_TYPE_STRING:
                                    rows.put(name, c.getString(l));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    rows.put(name, c.getInt(l));
                                    break;
                                case Cursor.FIELD_TYPE_FLOAT:
                                    rows.put(name, c.getFloat(l));
                                    break;
                                case Cursor.FIELD_TYPE_BLOB:
                                    rows.put(name, c.getBlob(l));
                                    break;
                            }
                        }
                        array.add(rows);
                    } while (c.moveToNext());
                    c.close();
                    json_fin.put(tablenames.get(i), array);
                }
            }
            t = json_fin.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!t.equals("{}")) {
            try {

                GetXMLTask get = new GetXMLTask("Syncing Data");
                String output= get.execute(new LinkedHashMap[]{linkedHashMap,json_fin}).get();

                if(output!=null) {
                    if (output.contains("Sync Unsuccessful") || output.length()>60) {
                        MainActivity.showMessage("Failure", "Sync Unsuccessful", MainActivity.get());
                        return;
                    }
                    if (output.startsWith("Sync Status")) {
                        MainActivity.syncStatus.setText(output.substring(13,35).replaceFirst(":", "at"));
                        SharedPreferences.Editor mEditor = MainActivity.mPrefs.edit();
                        mEditor.putString("status", output.substring(13,35).replaceFirst(":", "at")).commit();
                    }

                }


                String image_output="";
                String q = "SELECT * FROM images";
                Cursor c = MainActivity.db.rawQuery(q, null);
                c.moveToFirst();
                if (c != null && c.getCount() > 0 && !output.contains("Sync Unsuccessful")) {
                    do {

                        connectorCheck connectorCheck=new connectorCheck();

                        connectorCheck.execute();
                        LinkedHashMap image = new LinkedHashMap();
                        ArrayList arrayList=new ArrayList();
                        LinkedHashMap rows = new LinkedHashMap();
                        rows.put("child_id", c.getString(c.getColumnIndex("child_id")));
                        rows.put("photo_id", c.getString(c.getColumnIndex("photo_id")));

                        Bitmap bitmap = BitmapFactory.decodeFile(c.getString(c.getColumnIndex("image")));
                        String image_text = encodeTobase64(bitmap);
                        rows.put("image", image_text);
                        arrayList.add(rows);
                        image.put("images", arrayList);


                        GetXMLTask get_image = new GetXMLTask("Syncing Images");
                        image_output=get_image.execute(new LinkedHashMap[]{linkedHashMap,image}).get();
                    } while (c.moveToNext() && image_output!=null && !image_output.contains("Sync Unsuccessful"));
                    c.close();
                }



            if (!(output.contains("Sync Unsuccessful") ||output.length()<=50) && !image_output.contains("Sync Unsuccessful")) {
/*
                for (int i = 0; i < tablenames.size(); i++) {
                    if(!tablenames.get(i).equals("child_references") || !tablenames.get(i).equals("school_references"))
                    MainActivity.db.execSQL("DROP TABLE IF EXISTS " + tablenames.get(i));
                }*/
                MainActivity.showMessage("Success!", output.substring(35),MainActivity.get());
            } else {
                MainActivity.showMessage("Failure!", "Sync Unsuccessful! Try again Later!",MainActivity.get());
            }
            } catch (InterruptedException e) {
                System.out.println("Interupted Exception");
            } catch (ExecutionException e) {
                System.out.println("Execution Exception");
            }

        } else {
           MainActivity.showMessage("Failure", "No Entries Found",MainActivity.get());
        }
    }
    static String child_table;
    public void retrieve_child_data(){


        LinkedHashMap linkedHashMap=new LinkedHashMap();
        linkedHashMap.put("URL", "check_child_ids.php");

        try {
            GetXMLTask retrieve = new GetXMLTask("Retrieving Child Table Data");
            child_table=retrieve.execute(new LinkedHashMap[]{linkedHashMap}).get();


            //System.out.println("Child Table "+child_table);
            if(!(child_table.contains("Connection failed") || child_table.contains("No Entries"))){
                JSONObject jsonObject = new JSONObject(child_table);

                //System.out.println("Child Table "+jsonObject.toString());
                MainActivity.db.execSQL("DROP TABLE IF EXISTS child_references" );
                String child_table_query=
                        "  child_id VARCHAR[10] ," +
                                "  name VARCHAR[140] ," +
                                "  gender VARCHAR[10] ,"+
                                "  father_name VARCHAR[140]";
                //creating image table
                MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS child_references( " + child_table_query + " )");

                Iterator<?> keys = jsonObject.keys();

                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    if ( jsonObject.get(key) instanceof JSONObject ) {
                        String gender="";
                        JSONObject obj=(JSONObject) jsonObject.get(key);
                        switch (Integer.parseInt(obj.get("gender").toString())){
                            case 1:gender="Male";
                                break;
                            case 2:gender="Female";
                                break;
                            case 3:gender="Other";
                                break;
                        }
                        String insert_query = "'" + obj.get("child_id").toString() + "'," +
                                "'" + obj.get("name").toString() + "'," +
                                "'" + gender + "',"+
                                "'"+ obj.get("father_name")+"'";
                        System.out.println(insert_query);
                        //inserting into database
                        MainActivity.db.execSQL("INSERT INTO child_references VALUES (" + insert_query + ")");
                    }
                }
                MainActivity.showMessage("Success","Entries Retrieved!",MainActivity.get());
            }
            else
                MainActivity.showMessage("Failure","Entries Could not be Retrieved!",MainActivity.get());
        }
        catch (InterruptedException e) {
            System.out.println("Interupted Exception");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("Execution Exception");
            e.printStackTrace();
        }catch (JSONException e) {
            System.out.println("JSON Exception");
            e.printStackTrace();
        }
    }
    static String school;
    public void retrieve_school_data(){
        LinkedHashMap linkedHashMap=new LinkedHashMap();
        linkedHashMap.put("URL", "check_school_ids.php");

        try {
            GetXMLTask retrieve = new GetXMLTask("Retrieving School Table Data");
            school=retrieve.execute(new LinkedHashMap[]{linkedHashMap}).get();


            //System.out.println("Child Table "+child_table);
            if(!(school.contains("Connection failed") || school.contains("No Entries"))){
                JSONObject jsonObject = new JSONObject(school);

                //System.out.println("Child Table "+jsonObject.toString());
                MainActivity.db.execSQL("DROP TABLE IF EXISTS school_references" );
                String school_table_query=
                        "  school_id VARCHAR[10] ," +
                                "  name VARCHAR[140] " ;
                //creating image table
                MainActivity.db.execSQL("CREATE TABLE IF NOT EXISTS school_references( " + school_table_query + " )");

                Iterator<?> keys = jsonObject.keys();

                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    if ( jsonObject.get(key) instanceof JSONObject ) {
                        JSONObject obj=(JSONObject) jsonObject.get(key);
                        String insert_query = "'" + obj.get("school_id").toString() + "'," +
                                "'" + obj.get("name").toString() + "'";
                        System.out.println(insert_query);
                        //inserting into database
                        MainActivity.db.execSQL("INSERT INTO school_references VALUES (" + insert_query + ")");
                    }
                }
                MainActivity.showMessage("Success","Entries Retrieved!",MainActivity.get());
            }
            else
                MainActivity.showMessage("Failure","Entries Could not be Retrieved!",MainActivity.get());
        }
        catch (InterruptedException e) {
            System.out.println("Interupted Exception");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("Execution Exception");
            e.printStackTrace();
        }catch (JSONException e) {
            System.out.println("JSON Exception");
            e.printStackTrace();
        }
    }
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