package healthcare.studentdataentry;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by Aamir on 20-02-2016.
 */
     class connectorCheck extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        protected void onPostExecute(Void dResult) {
            dialog.dismiss();
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.get());
            dialog.setCancelable(false);
            dialog.setTitle("Connecting to Server");
            dialog.setMessage("Please Wait... Do not Close the App");
            dialog.show();

        }

        protected Void doInBackground(Void... params) {

            MainActivity.connected=UpdateActivity.isConnectedToServer(MainActivity.url);
            return null;
        }

    }

