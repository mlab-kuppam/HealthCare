package com.mlab.pes.healthcare;


import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class GetXMLTask extends AsyncTask<LinkedHashMap, Void, String> {
    String message;

    public GetXMLTask(String message) {
        super();
        this.message=message;
        // do stuff
    }

    private ProgressDialog dialog;
    protected void onPreExecute() {
        dialog = new ProgressDialog(MainActivity.get());
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.setMessage("Please Wait... Do not Close the App");
        dialog.show();

    }
    @Override
    protected String doInBackground(LinkedHashMap... params) {


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost;
        // In a POST request, we don't pass the values in the UL.
        //Therefore we use only the web page URL as the parameter of the HttpPost argument
        httpPost = new HttpPost(MainActivity.url+params[0].get("URL").toString());


        // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
        //uniquely separate by the other end.
        //To achieve that we use BasicNameValuePair
        //Things we need to pass with the POST request
        BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("", "");
        //System.out.println(trial);

        String j = null;
        if (params.length > 1) {
            Gson gson = new Gson();
            j = gson.toJson(params[1], LinkedHashMap.class);
            System.out.println("JSON STRING: " + j);
            usernameBasicNameValuePair = new BasicNameValuePair("j", j);
        }

        // We add the content that we want to pass with the POST request to as name-value pairs
        //Now we put those sending details to an ArrayList with type safe of NameValuePair
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(usernameBasicNameValuePair);

        try {
            // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
            //This is typically useful while sending an HTTP POST request.
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
            httpPost.setEntity(urlEncodedFormEntity);

            try {
                // HttpResponse is an interface just like HttpPost.
                //Therefore we can't initialize them
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // According to the JAVA API, InputStream constructor do nothing.
                //So we can't initialize InputStream although it is not an interface
                InputStream inputStream = httpResponse.getEntity().getContent();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }


                return stringBuilder.toString();

            } catch (ClientProtocolException cpe) {
                System.out.println("First Exception because of HttpResponese :" + cpe);
                cpe.printStackTrace();
            } catch (IOException ioe) {
                System.out.println("Second Exception because of HttpResponse :" + ioe);
                ioe.printStackTrace();
            }

        } catch (UnsupportedEncodingException uee) {
            System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
            uee.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String output) {
        dialog.dismiss();
/*
        this.output = output;

        if (url.equals("http://192.168.1.7/check_child_ids.php")) {
            syncing.child_table=output;

        } else {
            if (output.startsWith("Sync Status")) {
                output.replace("Sync Status", " ");
                output.replace("Sync Successful", " ");
                MainActivity.syncStatus.setText(output);
                SharedPreferences.Editor mEditor = MainActivity.mPrefs.edit();
                mEditor.putString("status", output).commit();
            }
            if (output.contains("Sync Successful")) {
                this.output = "Sync Successful";
            } else if (output.contains("Sync Unsuccessful")) {
                this.output = "Sync Unsuccessful";
            }
        }
*/
    }
}