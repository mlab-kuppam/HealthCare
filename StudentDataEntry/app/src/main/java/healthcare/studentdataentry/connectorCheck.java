package healthcare.studentdataentry;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Aamir on 20-02-2016.
 */
class connectorCheck extends AsyncTask<Void, Void, Void> {

    private ProgressDialog dialog;
    protected void onPostExecute(Void dResult) {
        dialog.dismiss();
    }

    protected void onPreExecute() {
        progress();

    }
    void progress(){
        dialog = new ProgressDialog(MainActivity.get());
        dialog.setCancelable(false);
        dialog.setTitle("Connecting to Server");
        dialog.setMessage("Please Wait... Do not Close the App");
        dialog.show();
    }

    protected Void doInBackground(Void... params) {

        String IPI="http://192.168.3.35/";
        String IPE="http://122.252.230.46/";
        if(isConnectedToServer(IPI)){
            MainActivity.connected=true;
            MainActivity.url=IPI;
        }else if(isConnectedToServer(IPE)){
            MainActivity.connected=true;
            MainActivity.url=IPE;
        }
        return null;
    }

    public boolean isConnectedToServer(String url) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            // e.printStackTrace();
            return false;
        }
    }

}

